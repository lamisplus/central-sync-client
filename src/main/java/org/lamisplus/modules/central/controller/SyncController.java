package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.lamisplus.modules.central.service.SyncService;
import org.lamisplus.modules.central.utility.ConstantUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/sync")
@Slf4j
@RestController
public class SyncController {
    private final SyncService syncService;

    @PostMapping(value = "/import")
    public ResponseEntity<String> importData(MultipartFile multipartFile,  @RequestParam("facilityId") Long facilityId, @RequestParam("fy") Long fy, @RequestParam("qt") String qt ) throws IOException {
        String datimId = syncService.getDatimId(facilityId);
        syncService .bulkImport(multipartFile,  datimId, fy, qt);
        //syncService.bulkImport(multipartFile);
        return ResponseEntity.ok().body("Data imported successfully");
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
}
