package org.lamisplus.modules.central.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.UploadConfirmationDTO;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.service.ExportService;
import org.lamisplus.modules.central.service.ImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImportController {

    private final String IMPORT_URL_VERSION_ONE = "/api/v1/central-sync-import";
    private final SimpMessageSendingOperations messagingTemplate;
    private final ImportService importService;


    @PostMapping(IMPORT_URL_VERSION_ONE + "/person-data")
    public ResponseEntity<UploadConfirmationDTO> importPersonData(@RequestParam("facilityId") Long facilityId,
                                                                  @RequestParam("tableName") String tableName,
                                                                  @RequestParam("userName") String userName,
                                                                  @RequestParam("file") MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return  ResponseEntity.ok(importService.importPersonData(facilityId, tableName, userName, file));
    }
}
