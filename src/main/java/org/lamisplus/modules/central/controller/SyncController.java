package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.domain.dto.RemoteUrlDTO;
import org.lamisplus.modules.central.domain.dto.SyncDetailDto;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
import org.lamisplus.modules.central.service.SyncService;
import org.lamisplus.modules.central.utility.ConstantUtility;
import org.lamisplus.modules.central.utility.RSAUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@RestController
public class SyncController {
    private final SyncService syncService;
    private final static String BASE_URL1 = "/api/v1/sync";
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
    public List<SyncHistoryTracker> getSyncHistoryTracker(@PathVariable Long id){
        return syncService.getSyncHistoryTracker(id);
    }

    @SneakyThrows
    @PostConstruct
    public void initialize() {
        new File(ConstantUtility.TEMP_SERVER_DIR).mkdirs();
        File directory = new File(ConstantUtility.TEMP_SERVER_DIR);

        if (!directory.exists()) {
            return;
        } else {
            FileUtils.cleanDirectory(directory);
        }
    }

    @RequestMapping(value = BASE_URL1 + "/remote-access-token",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendToRemoteAccessToServer(@Valid @RequestBody RemoteAccessToken remoteAccessToken) {
        if(syncService.authorize(remoteAccessToken, false) == null) throw new RuntimeException("Error while signing in");
    }

    @RequestMapping(value = BASE_URL1 + "/remote-access-token/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateRemoteAccessOnServer(@PathVariable Long id, @Valid @RequestBody RemoteAccessToken remoteAccessToken) {
        accessTokenRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException(RemoteAccessToken.class, "id", "not found"));
        if(syncService.authorize(remoteAccessToken, true) == null) throw new RuntimeException("Error while signing in");
    }

    @RequestMapping(value = BASE_URL1 + "/remote-urls",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RemoteUrlDTO>> getRemoteUrls() {
        return ResponseEntity.ok(syncService.getRemoteUrls());
    }

    @RequestMapping(value = BASE_URL1 + "/key",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap<String, String> getKey() {
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
