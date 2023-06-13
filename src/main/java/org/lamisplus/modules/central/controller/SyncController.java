package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.lamisplus.modules.central.domain.dto.RemoteUrlDTO;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.lamisplus.modules.central.service.SyncService;
import org.lamisplus.modules.central.utility.ConstantUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
public class SyncController {
    private final SyncService syncService;
    private final static String BASE_URL1 = "/api/v1/sync";
    private final static String BASE_URL2 = "/api/sync";

    @PostMapping(value = BASE_URL1 +"/import")
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
    @PostMapping(BASE_URL1 + "/receive-data/{facilityId}")
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

    @RequestMapping(value = BASE_URL2 + "/remote-access-token",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendToRemoteAccessToServer(@Valid @RequestBody RemoteAccessToken remoteAccessToken) throws GeneralSecurityException, IOException {
        if(syncService.authorize(remoteAccessToken) == null) throw new RuntimeException("Error while signing in");
    }

    @RequestMapping(value = BASE_URL2 + "/remote-urls",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RemoteUrlDTO>> getRemoteUrls() {
        return ResponseEntity.ok(syncService.getRemoteUrls());
    }
}
