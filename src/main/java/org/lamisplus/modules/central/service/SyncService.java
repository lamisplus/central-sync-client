package org.lamisplus.modules.central.service;

import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.domain.dto.RemoteUrlDTO;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SyncService {

    String getDatimId(Long facilityId);


    String authorize(RemoteAccessToken remoteAccessToken);

    List<RemoteUrlDTO> getRemoteUrls();

    void deleteSyncHistory(Long id);

    void deleteRemoteAccessToken(Long id);

}
