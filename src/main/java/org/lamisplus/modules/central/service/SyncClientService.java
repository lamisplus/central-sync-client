package org.lamisplus.modules.central.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.domain.entities.User;
import org.lamisplus.modules.base.service.UserService;
import org.lamisplus.modules.central.domain.dto.UploadDTO;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.domain.entity.SyncQueue;
import org.lamisplus.modules.central.domain.entity.Tables;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.lamisplus.modules.central.utility.AESUtil;
import org.lamisplus.modules.central.utility.HttpConnectionManager;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncClientService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final SyncHistoryService syncHistoryService;
    private final RemoteAccessTokenRepository remoteAccessTokenRepository;
    private final ObjectSerializer objectSerializer;
    private final UserService userService;
    private final SyncHistoryRepository syncHistoryRepository;

    private final SendWebsocketService sendSyncWebsocketService;

    private static String SYNC_ENDPOINT = "/central-topic/progress";
    private static String SYNC_PAYLOAD_ENDPOINT = "/central-topic/table-progress";
    private final ObjectMapper objectMapper;


    //@Async
    public String sender(UploadDTO uploadDTO, String tableName) throws Exception {
        log.info("path: {}", uploadDTO.getServerUrl());
        //Current login user
        User user = userService.getUserWithRoles().orElse(null);
        RemoteAccessToken remoteAccessToken = null;
        //Check the if user is not null
        remoteAccessToken = remoteAccessTokenRepository
                .findByUrl(uploadDTO.getServerUrl())
                .orElseThrow(() -> new EntityNotFoundException(RemoteAccessToken.class, "url", "" + uploadDTO.getServerUrl()));

        if (remoteAccessToken.getToken() == null)
            new EntityNotFoundException(RemoteAccessToken.class, "token", "" + remoteAccessToken.getToken());

        //Setting the token
        String token = " Bearer ".concat(remoteAccessToken.getToken());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


        int i = 0;
        for (Tables table : Tables.values()) {
            //process only one table sent
            if (tableName != null && !tableName.equals("*")) {
                System.out.println("table Name - " + table.name());
                if(!table.name().equalsIgnoreCase(tableName)){
                    continue;
                }
            }
            System.out.println("table to processed is - " + table.name());
            //Web socket for progress bar
            Map<String, Object> payload = new HashMap<>();
            i +=1;
            //Getting the percentage - (Value/Total value) × 100
            payload.put("percentageSynced", (i/Tables.values().length) * 100);
            payload.put("count", i);
            payload.put("currentTable", table.name());
            payload.put("total", Tables.values().length);
            sendSyncWebsocketService.broadcastProgressUpdate(SYNC_ENDPOINT, i);
            sendSyncWebsocketService.broadcastProgressUpdate(SYNC_PAYLOAD_ENDPOINT, objectMapper.writeValueAsString(payload));

            SyncHistory syncHistory = syncHistoryService.getSyncHistory(table.name(), uploadDTO.getFacilityId());
            LocalDateTime dateLastSync = syncHistory.getDateLastSync();
            List<?> serializeTableRecords = objectSerializer.serialize(table, uploadDTO.getFacilityId(), dateLastSync);

            //TODO: save attributes of data to be sent to server - name, size, uuid

            if (!serializeTableRecords.isEmpty()) {
                Object serializeObject = serializeTableRecords.get(0);
                Integer size = serializeTableRecords.size();
                log.info("object size:  {} ", serializeTableRecords.size());
                if (!serializeObject.toString().contains("No table records was retrieved for server sync")) {
                    String pathVariable = table.name().concat("/").concat(Long.toString(uploadDTO.getFacilityId()))
                            .concat("/").concat(remoteAccessToken.getUsername())
                            .concat("/")
                            .concat(Integer.toString(size));
                    //log.info("path: {}", pathVariable);
                    String url = uploadDTO.getServerUrl().concat("/api/sync/").concat(pathVariable);

                    log.info("url : {}", url);

                    byte[] bytes = mapper.writeValueAsBytes(serializeTableRecords);

                    SecretKey secretKey = AESUtil.getPrivateAESKeyFromDB(remoteAccessToken);
                    bytes = this.encrypt(bytes, secretKey);

                    String response = new HttpConnectionManager().post(bytes, token, url);
                    log.info("Done");

                    syncHistory.setTableName(table.name());
                    syncHistory.setOrganisationUnitId(uploadDTO.getFacilityId());
                    syncHistory.setDateLastSync(LocalDateTime.now());

                    try {
                        //For serializing the date on the sync queue
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.registerModule(new JavaTimeModule());
                        objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                        SyncQueue syncQueue = objectMapper.readValue(response, SyncQueue.class);

                        syncHistory.setProcessed(syncQueue.getProcessed());
                        syncHistory.setSyncQueueId(syncQueue.getId());

                        //get remote access token id
                        syncHistory.setRemoteAccessTokenId(remoteAccessToken.getId());
                        syncHistory.setUploadSize(serializeTableRecords.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    syncHistoryService.save(syncHistory);
                }
            }
        }
        return "Successful";//CompletableFuture.completedFuture("Successful");
    }

    private byte[] encrypt(byte[] bytes, SecretKey secretKey) throws GeneralSecurityException, Exception {
        try{
            Cipher encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedMessageBytes = encryptCipher.doFinal(bytes);
            return encryptedMessageBytes;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public List getTablesForSyncing(Long facilityId){
        return Arrays.stream(Tables.values()).collect(Collectors.toList());
        //return syncHistoryRepository.findTableToSync(facilityId);
    }
}

