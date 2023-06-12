package org.lamisplus.modules.central.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SyncService {

    //boolean bulkImport(MultipartFile file) throws IOException;

    //void importRadet(String jsonFilePath) throws IOException;

    void importRadet(String jsonFilePath,  String datimId) throws IOException;

    boolean bulkImport(MultipartFile file, String datimId) throws IOException;

    String getDatimId(Long facilityId);

    boolean bulkImport(String fileName, String datimId) throws IOException;
    void importHts(String jsonFilePath, String datimId) throws IOException;

    void importPrep(String jsonFilePath, String datimId) throws IOException;

}
