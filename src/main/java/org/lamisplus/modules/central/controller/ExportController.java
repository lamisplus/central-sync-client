package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.lamisplus.modules.central.domain.dto.SyncHistoryRequest;
import org.lamisplus.modules.central.domain.dto.SyncHistoryResponse;
import org.lamisplus.modules.central.service.SyncHistoryService;
import org.springframework.http.*;
import org.lamisplus.modules.central.service.ExportService;
import org.lamisplus.modules.central.utility.ConstantUtility;
import org.lamisplus.modules.central.utility.FileUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/export")
public class ExportController {
    private final FileUtility fileUtility;
    private final ExportService exportService;

    private String APIURL = "http://localhost:8485/api/v1/sync/receive-data/";
    private final SyncHistoryService syncHistoryService;

    @GetMapping("/all")
    public ResponseEntity<String> generate(@RequestParam Long facilityId, HttpServletResponse response) throws IllegalAccessException {
        if (facilityId == null)   {
            throw new IllegalAccessException("Invalid request parameters");
        }
        String zipFileName = exportService.bulkExport(facilityId);
        if (!zipFileName.equals("None") && !zipFileName.equals("NO_RECORD")) {
            return new ResponseEntity<>("Record generated successfully.", HttpStatus.OK);
        } else if (zipFileName.equals("NO_RECORD")) {
            return new ResponseEntity<>("No record found for the selected period.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Record generation failed.", HttpStatus.OK);
    }

    @GetMapping("/available-files")
    public ResponseEntity<Set<String>> availableFile() throws IOException {
        return new ResponseEntity<>(fileUtility.listFilesUsingDirectoryStream(ConstantUtility.TEMP_BATCH_DIR), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = fileUtility.downloadFile(ConstantUtility.TEMP_BATCH_DIR, fileName);
        response.setHeader(ConstantUtility.CONTENT_DISPOSITION,  ConstantUtility.ATTACHMENT_FILENAME + fileName);
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Length", Integer.toString(byteArrayOutputStream.size()));
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.close();
        response.flushBuffer();
    }

    @PostMapping("/send-data")
    public ResponseEntity<String> sendDataToAPI(@RequestParam("fileName") String fileName, @RequestParam("facilityId") Long facilityId) {

        byte[] byteRequest = fileUtility.convertZipToByteArray(ConstantUtility.TEMP_BATCH_DIR + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(byteRequest, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity;
        try {
            String apiUrl = APIURL + facilityId;
            responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                syncHistoryService.updateSyncHistory(fileName, 1);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending data: " + e.getMessage());
        }

        return ResponseEntity.ok("Data sent successfully. Response: " + responseEntity.getBody());
    }

    @GetMapping("/sync-histories")
    public ResponseEntity<List<SyncHistoryResponse>> getSyncHistoryResponses() {
        return new ResponseEntity<>(syncHistoryService.getSyncHistories(), HttpStatus.OK);
    }

    @PutMapping("/sync-histories/{syncId}")
    public ResponseEntity<SyncHistoryResponse> updateSyncHistoryResponse(@PathVariable Long syncId, @RequestBody SyncHistoryRequest request) {
        if (syncId == null) {
            throw new IllegalArgumentException("Sync Id cannot be null");
        }
        return new ResponseEntity<>(syncHistoryService.updateSyncHistory(request, syncId), HttpStatus.OK);
    }

    @GetMapping("/sync-histories/{syncId}")
    public ResponseEntity<SyncHistoryResponse> getSyncHistoryResponse(@PathVariable Long syncId) {
        return new ResponseEntity<>(syncHistoryService.getSyncHistoryById(syncId), HttpStatus.OK);
    }


    @SneakyThrows
    @PostConstruct
    public void initialize() {
        new File(ConstantUtility.TEMP_BATCH_DIR).mkdirs();
    }




}