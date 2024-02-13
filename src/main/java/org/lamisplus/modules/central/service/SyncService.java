package org.lamisplus.modules.central.service;

import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.domain.dto.RemoteUrlDTO;
import org.lamisplus.modules.central.domain.entity.FacilityAppKey;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface SyncService {

    String getDatimId(Long facilityId);


    String authorize(RemoteAccessToken remoteAccessToken, Boolean update);

    List<RemoteUrlDTO> getRemoteUrls();

    void deleteSyncHistory(Long id);

    void deleteRemoteAccessToken(Long id);

    List<SyncHistoryTracker> getSyncHistoryTracker(Long syncHistoryId);

    void decrypt(String key, String fileLocation, String tableName);

    String authorizeBeforeSending(LoginVM loginVM, Long facilitId) throws RuntimeException;

    String checkUrl(FacilityAppKey appkey);

    ResponseEntity<Set<String>>  getFileStatus(LoginVM loginVM, Long syncId);
}

