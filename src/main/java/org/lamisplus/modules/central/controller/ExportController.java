package org.lamisplus.modules.central.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.domain.dto.SyncHistoryRequest;
import org.lamisplus.modules.central.domain.dto.SyncHistoryResponse;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.lamisplus.modules.central.service.SyncHistoryService;
import org.lamisplus.modules.central.utility.HttpConnectionManager;
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
import java.util.List;
import java.util.Set;

import static org.lamisplus.modules.central.utility.ConstantUtility.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/export")
public class ExportController {
    private final FileUtility fileUtility;
    private final ExportService exportService;
    private final RemoteAccessTokenRepository accessTokenRepository;
    private String API_URL = "/api/v1/sync/receive-data/";
    private String LOGIN_API = "/api/v1/authenticate";
    private final SyncHistoryService syncHistoryService;
    private final SyncHistoryRepository historyRepository;

    @GetMapping("/all")
    public ResponseEntity<String> generate(@RequestParam Long facilityId,
                                           @RequestParam (required = false, defaultValue = "true") boolean current,
                                           HttpServletResponse response) throws IllegalAccessException {
        if (facilityId == null)   {
            throw new IllegalAccessException("Invalid request parameters");
        }
        String zipFileName = exportService.bulkExport(facilityId, current);
        if (!zipFileName.equals("None") && !zipFileName.equals("NO_RECORD")) {
            return new ResponseEntity<>("Record generated successfully.", HttpStatus.OK);
        } else if (zipFileName.equals("NO_RECORD")) {
            return new ResponseEntity<>("No record found for the selected period.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Record generation failed.", HttpStatus.OK);
    }

    @GetMapping("/available-files")
    public ResponseEntity<Set<String>> availableFile() throws IOException {
        return new ResponseEntity<>(fileUtility.listFilesUsingDirectoryStream(TEMP_BATCH_DIR), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {

        SyncHistory history = historyRepository.getFile(fileName).orElseThrow(()-> new EntityNotFoundException(SyncHistory.class, "file", String.valueOf(fileName)));
        ByteArrayOutputStream byteArrayOutputStream = fileUtility.downloadFile(history.getFilePath(), fileName);
        response.setHeader(CONTENT_DISPOSITION,  ATTACHMENT_FILENAME + fileName);
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Length", Integer.toString(byteArrayOutputStream.size()));
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.close();
        response.flushBuffer();
    }

    public String authorizeBeforeSending(LoginVM loginVM) throws RuntimeException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("loginVM is {}", loginVM);
        try {
            RemoteAccessToken remoteAccessToken = accessTokenRepository
                    .findOneAccess()
                    .orElseThrow(()-> new EntityNotFoundException(RemoteAccessToken.class, "Access", "not available"));

            String USE_LOGIN_API = checkUrl(remoteAccessToken).concat(LOGIN_API);
            String connect = new HttpConnectionManager().post(loginVM, null, USE_LOGIN_API);

            //For serializing the date on the sync queue
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            JWTToken jwtToken = objectMapper.readValue(connect, JWTToken.class);

            if (jwtToken.getIdToken() != null) {
                String token = "Bearer " + jwtToken.getIdToken().replace("'", "");
                //log.info("token is {}", token);
                return token;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }


    @PostMapping("/send-data")
    public ResponseEntity<String> sendDataToAPI(@RequestParam("fileName") String fileName, @RequestParam("facilityId") Long facilityId) {

        LoginVM loginVM = new LoginVM();
        RemoteAccessToken remoteAccessToken = accessTokenRepository
                .findOneAccess()
                .orElseThrow(()-> new EntityNotFoundException(RemoteAccessToken.class, "Access", "not available"));

        String USE_API_URL = checkUrl(remoteAccessToken).concat(API_URL);
        loginVM.setUsername(remoteAccessToken.getUsername());
        loginVM.setPassword(remoteAccessToken.getPassword());

        SyncHistory history = historyRepository.getFile(fileName).orElseThrow(()-> new EntityNotFoundException(SyncHistory.class, "file", String.valueOf(fileName)));

        byte[] byteRequest = fileUtility.convertZipToByteArray(history.getFilePath() + File.separator + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        //loginVM.setPassword("kitkit");
        //loginVM.setUsername("uskarim");
        headers.set("Authorization", authorizeBeforeSending(loginVM));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity;
        try {
            String apiUrl = USE_API_URL + facilityId;
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(byteRequest, headers);

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
        new File(TEMP_BATCH_DIR).mkdirs();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JWTToken {
        @JsonProperty("id_token")
        private String idToken;

    }

    private String checkUrl(RemoteAccessToken remoteAccessToken){
        if(remoteAccessToken.getUrl() == null || StringUtils.isAllBlank(remoteAccessToken.getUrl())){
            throw new EntityNotFoundException(RemoteAccessToken.class, "Access", "not available");
        }
        return remoteAccessToken.getUrl();
    }

}