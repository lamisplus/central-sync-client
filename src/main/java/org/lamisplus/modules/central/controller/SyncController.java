package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.domain.dto.RemoteAccessTokenDto;
import org.lamisplus.modules.central.domain.dto.RemoteUrlDTO;
import org.lamisplus.modules.central.domain.dto.SyncDetailDto;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
import org.lamisplus.modules.central.service.SyncService;
import org.lamisplus.modules.central.utility.ConstantUtility;
import org.lamisplus.modules.central.utility.RSAUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@RestController
public class SyncController {
    private final SyncService syncService;
    private static final  String BASE_URL1 = "/api/v1/sync";
    private final RemoteAccessTokenRepository accessTokenRepository;

    private final RSAUtils rsaUtils;

    @DeleteMapping(value = BASE_URL1 + "/sync-history/{id}")
    public void deleteSyncHistory(@PathVariable Long id){
        syncService.deleteSyncHistory(id);
    }

    @DeleteMapping(value = BASE_URL1 + "/remote-access-token/{id}")
    public void deleteRemoteAccessToken(@PathVariable Long id){
        syncService.deleteRemoteAccessToken(id);
    }

    @GetMapping(value = BASE_URL1 + "/history/{id}/tracker")
    public Set<SyncHistoryTracker> getSyncHistoryTracker(@PathVariable Long id){
        return syncService.getSyncHistoryTracker(id);
    }

    @SneakyThrows
    @PostConstruct
    public void initialize() {
        new File(ConstantUtility.TEMP_SERVER_DIR).mkdirs();
        File directory = new File(ConstantUtility.TEMP_SERVER_DIR);

        if (directory.exists()) {
            FileUtils.cleanDirectory(directory);
        }
    }

    @PostMapping(value = BASE_URL1 + "/remote-access-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendToRemoteAccessToServer(@Valid @RequestBody RemoteAccessTokenDto remoteAccessTokenDto) {
        RemoteAccessToken remoteAccessToken = new RemoteAccessToken();
        BeanUtils.copyProperties(remoteAccessTokenDto, remoteAccessToken);
        if(syncService.authorize(remoteAccessToken, false) == null) {
            throw new EntityNotFoundException(RemoteAccessToken.class, "Error while signing in");
        }
    }

    @PutMapping(value = BASE_URL1 + "/remote-access-token/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateRemoteAccessOnServer(@PathVariable Long id, @Valid @RequestBody RemoteAccessTokenDto remoteAccessTokenDto) {
        if(!accessTokenRepository.findById(id).isPresent()){
            throw new EntityNotFoundException(RemoteAccessToken.class, "id", "not found");
        }
        RemoteAccessToken remoteAccessToken = new RemoteAccessToken();
        BeanUtils.copyProperties(remoteAccessTokenDto, remoteAccessToken);
        if(syncService.authorize(remoteAccessToken, true) == null) throw new EntityNotFoundException(RemoteAccessToken.class, "Error while signing in");
    }

    @GetMapping(value = BASE_URL1 + "/remote-urls", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RemoteUrlDTO>> getRemoteUrls() {
        return ResponseEntity.ok(syncService.getRemoteUrls());
    }

    @GetMapping(value = BASE_URL1 + "/key", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getKey() {
        return rsaUtils.keyGenerateAndReturnKey();
    }

    @GetMapping(BASE_URL1 + "/aes/decrypt")
    public void getDecrypt(@RequestParam String key, @RequestParam String location, @RequestParam String tableName) {
        syncService.decrypt(key, location, tableName);
    }

    @GetMapping(BASE_URL1 + "/rsa/decrypt")
    public String getDecrypt(@RequestParam String prKey, @RequestParam String encryptedMsg) {
        byte[] encryptedBytes = DatatypeConverter.parseBase64Binary(encryptedMsg);
        try {
            return rsaUtils.decryptWithPrivateKey(encryptedBytes, prKey);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = BASE_URL1 + "/sync-history/{id}")
    public ResponseEntity<Set<String>> getFileStatus(@PathVariable Long id, @RequestBody SyncDetailDto syncDetailDto){
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername(syncDetailDto.getUsername());
        loginVM.setPassword(syncDetailDto.getPassword());
        return syncService.getFileStatus(loginVM, id);
    }
}
