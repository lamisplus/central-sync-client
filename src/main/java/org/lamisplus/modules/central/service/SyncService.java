package org.lamisplus.modules.central.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SyncService {

    //boolean bulkImport(MultipartFile file) throws IOException;

    //void importRadet(String jsonFilePath) throws IOException;

    void importRadet(String jsonFilePath,  String datimId, Long yr, String qt) throws IOException;

    boolean bulkImport(MultipartFile file, String datimId, Long yr, String qt) throws IOException;

    String getDatimId(Long facilityId);
//    void importPrep(String jsonFilePath) throws IOException;
//    void importHts(String jsonFilePath) throws IOException;
}
