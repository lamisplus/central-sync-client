package org.lamisplus.modules.central.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.controller.ExportController;
import org.lamisplus.modules.central.domain.dto.RemoteUrlDTO;
import org.lamisplus.modules.central.domain.entity.*;
import org.lamisplus.modules.central.repository.*;
import org.lamisplus.modules.central.utility.AESUtil;
import org.lamisplus.modules.central.utility.HttpConnectionManager;
import org.lamisplus.modules.central.utility.RSAUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.stream.Collectors;

import static org.lamisplus.modules.central.utility.ConstantUtility.CREDENTIAL;
import static org.lamisplus.modules.central.utility.ConstantUtility.TEMP_BATCH_DIR;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncServiceImpl implements SyncService {
    public static final int UN_ARCHIVED = 0;
    public static final String ALGORITHM = "AES";
    private final SyncHistoryRepository syncHistoryRepository;
    private final FacilityAppKeyRepository facilityAppKeyRepository;
    private final SyncHistoryTrackerRepository syncHistoryTrackerRepository;
    private final RemoteAccessTokenRepository remoteAccessTokenRepository;
    private final RemoteAccessTokenRepository accessTokenRepository;
    private final RSAUtils rsaUtils;
    private String API_URL = "/api/v1/sync/receive-data/";
    private String LOGIN_API = "/api/v1/authenticate";
    private String FILE_STATUS_API = "/api/v1/sync";
    public static final String AUTHORIZATION = "Authorization";
    public static final String GEN_KEY = "genKey";
    public static final String APP_KEY = "appKey";


    @Override
    public String getDatimId(Long facilityId) {
        String datimId = syncHistoryRepository.getDatimCode(facilityId);
        return datimId;

    }

    public String authorize(RemoteAccessToken remoteAccessToken, Boolean update) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("remoteAccessToken is {}", remoteAccessToken);
        String url = remoteAccessToken.getUrl() + "/api/v1/authenticate";
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername(remoteAccessToken.getUsername());
        loginVM.setPassword(remoteAccessToken.getPassword());
        try {
            String connect = new HttpConnectionManager().post(loginVM, null, url);

            //For serializing the date on the sync queue
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            ExportController.JWTToken jwtToken = objectMapper.readValue(connect, ExportController.JWTToken.class);

            if (jwtToken.getIdToken() != null) {
                String token = "Bearer " + jwtToken.getIdToken().replace("'", "");
                log.info("token is {}", token);
                //saving the remote access token after authentication
                if(update)remoteAccessTokenRepository.deleteAll();
                saveRemoteAccessToken(remoteAccessToken);
                return token;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private RemoteAccessToken saveRemoteAccessToken(RemoteAccessToken remoteAccessToken){
        return accessTokenRepository.save(remoteAccessToken);
    }

    public List<RemoteUrlDTO> getRemoteUrls() {
        List<RemoteAccessToken> remoteAccessTokens = accessTokenRepository.findAll();

        List<RemoteUrlDTO> remoteUrlDTOS = new ArrayList<>();
        remoteAccessTokens.forEach(remoteAccessToken -> {
            RemoteUrlDTO remoteUrlDTO = new RemoteUrlDTO();
            remoteUrlDTO.setId(remoteAccessToken.getId());
            remoteUrlDTO.setUrl(remoteAccessToken.getUrl());
            remoteUrlDTO.setUsername(remoteAccessToken.getUsername());
            remoteUrlDTOS.add(remoteUrlDTO);
        });
        return remoteUrlDTOS;
    }

    public void deleteSyncHistory(Long id){
        SyncHistory syncHistory = syncHistoryRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException(SyncHistory.class, "id", String.valueOf(id)));
        syncHistoryRepository.delete(syncHistory);
    }

    public void deleteRemoteAccessToken(Long id){
        RemoteAccessToken remoteAccessToken = remoteAccessTokenRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException(RemoteAccessToken.class, "id", String.valueOf(id)));
        remoteAccessTokenRepository.delete(remoteAccessToken);
    }

    public List<SyncHistoryTracker> getSyncHistoryTracker(Long syncHistoryId){
        return syncHistoryTrackerRepository.findAllBySyncHistoryIdAndArchived(syncHistoryId, UN_ARCHIVED)
                .stream()
                .sorted(Comparator.comparing(SyncHistoryTracker::getTimeCreated).reversed())
                .collect(Collectors.toList());
    }

    public void decrypt(String key, String fileLocation, String tableName){
        String tempFile = TEMP_BATCH_DIR +  fileLocation + File.separator + tableName + ".json";
        String deTempFile = TEMP_BATCH_DIR +  fileLocation + File.separator + tableName + "_decrypted.json";
        try {
            SecretKey secretKey = AESUtil.getPrivateAESKeyFromDB(key);
            log.info("started decrypting...");
            AESUtil.decryptFile(ALGORITHM, secretKey, AESUtil.generateIv(),new File(tempFile), new File(deTempFile));
            log.info("done decrypting...");

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public String authorizeBeforeSending(LoginVM loginVM, Long facilitId) throws RuntimeException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("loginVM is {}", loginVM);
        try {
            FacilityAppKey facilityAppKey = facilityAppKeyRepository
                    .findByFacilityId(facilitId.intValue())
                    .orElseThrow(()-> new EntityNotFoundException(FacilityAppKey.class, "App Key", "not available"));

            String USE_LOGIN_API = checkUrl(facilityAppKey).concat(LOGIN_API);
            String connect = new HttpConnectionManager().post(loginVM, null, USE_LOGIN_API);

            //For serializing the date on the sync queue
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            ExportController.JWTToken jwtToken = objectMapper.readValue(connect, ExportController.JWTToken.class);

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

    public String checkUrl(FacilityAppKey appKey){
        if(appKey.getServerUrl() == null || StringUtils.isAllBlank(appKey.getServerUrl())){
            throw new EntityNotFoundException(FacilityAppKey.class, "App key", "not available");
        }
        return appKey.getServerUrl();
    }

    public ResponseEntity<Set<String>> getFileStatus(LoginVM loginVM, Long syncId){
        ResponseEntity<Set> responseEntity = null;
        Set<String> error = new HashSet<>();
        SyncHistory history = syncHistoryRepository
                .findById(syncId)
                .orElseThrow(()-> new EntityNotFoundException(SyncHistory.class, "syncId",String.valueOf(syncId)));

        FacilityAppKey facilityAppKey = facilityAppKeyRepository
                .findByFacilityId(history.getOrganisationUnitId().intValue())
                .orElseThrow(()-> new EntityNotFoundException(FacilityAppKey.class, "appKey","facility"));

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(AUTHORIZATION, authorizeBeforeSending(loginVM, history.getOrganisationUnitId()));
        headers.set(GEN_KEY, history.getGenKey());
        headers.set(APP_KEY, facilityAppKey.getAppKey());

        try {
            String apiUrl = facilityAppKey.getServerUrl() + FILE_STATUS_API;
            log.info("apiUrl {}", apiUrl);
            HttpEntity<Set<String>> requestEntity = new HttpEntity<>(headers);

            responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Set.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                log.info("files are {}", responseEntity.getBody());
                return ResponseEntity.ok(requestEntity.getBody());
            }else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body(requestEntity.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            error.add("Error sending data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
