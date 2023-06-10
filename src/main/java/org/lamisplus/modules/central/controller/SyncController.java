package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.lamisplus.modules.central.service.SyncService;
import org.lamisplus.modules.central.utility.ConstantUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/sync")
@Slf4j
@RestController
public class SyncController {
    private final SyncService syncService;

    @PostMapping(value = "/import")
    public ResponseEntity<String> importData(@RequestParam("multipartFile") MultipartFile multipartFile,  @RequestParam("facilityId") Long facilityId ) throws IOException {
        String datimId = syncService.getDatimId(facilityId);
        syncService .bulkImport(multipartFile,  datimId);
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
    @PostMapping("/receive-data/{facilityId}")
    public ResponseEntity<String> receiveDataFromAPI(@RequestBody byte[] data, @PathVariable Long facilityId ) throws IOException {
        String datimId = syncService.getDatimId(facilityId);
        final String filePath = ConstantUtility.TEMP_SERVER_DIR + "import.zip";
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);){
            fileOutputStream.write(data);
            syncService.bulkImport(filePath, datimId);
        } catch (IOException e) {
            return new ResponseEntity("Error writing data to file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Data received and saved to file successfully.", HttpStatus.OK);
    }
}
