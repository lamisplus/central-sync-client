package org.lamisplus.modules.central.service;

import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.domain.dto.RemoteUrlDTO;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SyncService {

    //boolean bulkImport(MultipartFile file) throws IOException;

    //void importRadet(String jsonFilePath) throws IOException;

    void importRadet(String jsonFilePath,  String datimId) throws IOException;

    boolean bulkImport(MultipartFile file, String datimId) throws IOException;

    String getDatimId(Long facilityId);

    boolean bulkImport(String fileName, String datimId) throws IOException;
    void importHts(String jsonFilePath, String datimId) throws IOException;

    void importPrep(String jsonFilePath, String datimId) throws IOException;

    String authorize(RemoteAccessToken remoteAccessToken);

    List<RemoteUrlDTO> getRemoteUrls();

    void deleteSyncHistory(Long id);

    void deleteRemoteAccessToken(Long id);



}
