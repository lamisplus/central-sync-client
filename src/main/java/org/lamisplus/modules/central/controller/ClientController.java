package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.central.domain.dto.FacilityDto;
import org.lamisplus.modules.central.domain.dto.RemoteUrlDTO;
import org.lamisplus.modules.central.domain.dto.UploadDTO;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.service.ClientRemoteAccessTokenService;
import org.lamisplus.modules.central.service.SyncHistoryService;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
import org.lamisplus.modules.central.service.SyncClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/sync")
public class ClientController {
    private final SyncHistoryService syncHistoryService;
    private final ClientRemoteAccessTokenService clientRemoteAccessTokenService;
    private final SyncClientService syncClientService;
    private final RemoteAccessTokenRepository remoteAccessTokenRepository;


    @RequestMapping(value = "/upload",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //@CircuitBreaker(name = "service2", fallbackMethod = "getDefaultMessage")
    //@Retry(name = "retryService2", fallbackMethod = "retryFallback")
    public @ResponseBody ResponseEntity sender(@RequestParam(required = false, defaultValue = "*") String tableName, @Valid @RequestBody UploadDTO uploadDTO) throws Exception  {
            syncClientService.sender(uploadDTO, tableName);
            return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/table/{facilityId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List> getTablesForSyncing(@PathVariable Long facilityId) {
        return ResponseEntity.ok(syncClientService.getTablesForSyncing(facilityId));
    }
    @RequestMapping(value = "/facilities",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FacilityDto>> getOrganisationUnitWithRecords() {
        return ResponseEntity.ok(remoteAccessTokenRepository.findFacilityWithRecords());
    }

    @RequestMapping(value = "/sync-history",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SyncHistory>> getSyncHistory() {
        return ResponseEntity.ok(syncHistoryService.getSyncHistories());
    }


    @RequestMapping(value = "/remote-urls",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RemoteUrlDTO>> getRemoteUrls() {
        return ResponseEntity.ok(clientRemoteAccessTokenService.getRemoteUrls());
    }

    @RequestMapping(value = "/remote-access-token",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendToRemoteAccessToServer(@Valid @RequestBody RemoteAccessToken remoteAccessToken) throws GeneralSecurityException, IOException {
        clientRemoteAccessTokenService.sendToRemoteAccessToServer(remoteAccessToken, false);
    }

    @RequestMapping(value = "/remote-access-token+ /{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateRemoteAccessToken(@PathVariable Long id, @Valid @RequestBody RemoteAccessToken remoteAccessToken) throws GeneralSecurityException, IOException {
        remoteAccessTokenRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException(RemoteAccessToken.class, "id", String.valueOf(id)));
        clientRemoteAccessTokenService.sendToRemoteAccessToServer(remoteAccessToken, true);
    }
}
