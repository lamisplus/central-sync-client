package org.lamisplus.modules.central.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.domain.dto.SyncDetailDto;
import org.lamisplus.modules.central.domain.dto.SyncHistoryRequest;
import org.lamisplus.modules.central.domain.dto.SyncHistoryResponse;
import org.lamisplus.modules.central.domain.entity.FacilityAppKey;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;
import org.lamisplus.modules.central.repository.FacilityAppKeyRepository;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.lamisplus.modules.central.repository.SyncHistoryTrackerRepository;
import org.lamisplus.modules.central.service.FacilityAppKeyService;
import org.lamisplus.modules.central.service.SyncHistoryService;
import org.lamisplus.modules.central.service.SyncService;
import org.springframework.http.*;
import org.lamisplus.modules.central.service.ExportService;
import org.lamisplus.modules.central.utility.FileUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static org.lamisplus.modules.central.utility.ConstantUtility.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/export")
public class ExportController {
    public static final String GENERATED = "Generated";
    public static final int ARCHIVED = 0;
    public static final String AUTHORIZATION = "Authorization";
    public static final String VERSION = "version";
    public static final String GEN_KEY = "genKey";
    public static final String APP_KEY = "appKey";
    private final FileUtility fileUtility;
    private final ExportService exportService;
    private final FacilityAppKeyRepository facilityAppKeyRepository;
    private String API_URL = "/api/v1/sync/receive-data/";
    private String LOGIN_API = "/api/v1/authenticate";
    private final SyncHistoryService syncHistoryService;
    private final SyncHistoryRepository historyRepository;
    private final SyncHistoryTrackerRepository syncHistoryTrackerRepository;
    private final FacilityAppKeyService facilityAppKeyService;
    private final SyncService syncService;
    private final SyncHistoryRepository syncHistoryRepository;

    @GetMapping("/all")
    public ResponseEntity<String> generate(@RequestParam Long facilityId,
                                           @RequestParam (required = false, defaultValue = "true") boolean current,
                                           HttpServletResponse response) throws IllegalAccessException {
        if (facilityId == null)   {
            throw new IllegalAccessException("Invalid request parameters");
        }
        String zipFileName = exportService.generateFilesForSyncing(facilityId, current);
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


    @PostMapping("/file/data")
    public ResponseEntity<String> sendFileDataToAPI(@RequestBody SyncDetailDto syncDetailDto) {
        if(syncDetailDto.getUsername() == null || syncDetailDto.getPassword() == null){
            throw new EntityNotFoundException(LoginVM.class, "Username or Password", "not found");
        }
        FacilityAppKey facilityAppKey = facilityAppKeyRepository
                .findByFacilityId(syncDetailDto.getFacilityId().intValue())
                .orElseThrow(()-> new EntityNotFoundException(FacilityAppKey.class, "App Key", "not available"));

        List<SyncHistoryTracker> trackers = new ArrayList<>();
        String USE_API_URL = syncService.checkUrl(facilityAppKey).concat(API_URL);

        if(syncDetailDto.getSyncHistoryTrackerUuid() != null){
            SyncHistoryTracker tracker = syncHistoryTrackerRepository
                    .findByUuid(java.util.UUID.fromString(syncDetailDto.getSyncHistoryTrackerUuid()))
                    .orElseThrow(()-> new EntityNotFoundException(SyncHistoryTracker.class, "uuid", "uuid"));
            syncDetailDto.setSyncHistoryUuid(tracker.getSyncHistoryUuid().toString());
            trackers.add(tracker);

        } else {
            trackers = syncHistoryTrackerRepository
                    .findAllBySyncHistoryUuidAndStatusAndArchived(java.util.UUID.fromString(syncDetailDto.getSyncHistoryUuid()), GENERATED, ARCHIVED);
        }

        SyncHistory history = historyRepository
                .findByUuid(java.util.UUID.fromString(syncDetailDto.getSyncHistoryUuid()))
                .orElseThrow(()-> new EntityNotFoundException(SyncHistory.class, "file", "file"));
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername(syncDetailDto.getUsername());
        loginVM.setPassword(syncDetailDto.getPassword());

        return getStringResponseEntity(syncDetailDto.getFacilityId(), loginVM, trackers, USE_API_URL, history);
    }

    private ResponseEntity<String> getStringResponseEntity(Long facilityId, LoginVM loginVM, List<SyncHistoryTracker> trackers, String USE_API_URL, SyncHistory history) {
        ResponseEntity<String> responseEntity = null;
        String datimId = historyRepository.getDatimCode(facilityId);
        String appKey = facilityAppKeyService.FindByFacilityId(Integer.valueOf(String.valueOf(facilityId))).getAppKey();

        for (SyncHistoryTracker tracker : trackers) {
            byte[] byteRequest = fileUtility.convertFileToByteArray(history.getFilePath() + File.separator + tracker.getFileName());

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.set(AUTHORIZATION, syncService.authorizeBeforeSending(loginVM, facilityId));

            String version = syncHistoryRepository.getClientSyncModuleVersion().orElse("N/A");
            headers.set(VERSION, version);
            headers.set(GEN_KEY, history.getGenKey());
            headers.set(APP_KEY, appKey);
            //just the username
            String encryptedUsername = exportService.encryptMessage(loginVM.getUsername(), appKey);
            headers.set(CREDENTIAL, encryptedUsername);

            try {
                String apiUrl = USE_API_URL + datimId + "/" + history.getUuid() + "/" + tracker.getUuid() + "/" + tracker.getFileName();
                log.info("apiUrl {}", apiUrl);
                HttpEntity<byte[]> requestEntity = new HttpEntity<>(byteRequest, headers);

                responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    syncHistoryService.updateSyncHistoryTracker(tracker.getId());
                }else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending data: " + e.getMessage());
            }
        }
        return responseEntity;
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
}