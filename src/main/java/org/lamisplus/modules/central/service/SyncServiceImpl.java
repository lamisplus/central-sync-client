package org.lamisplus.modules.central.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.controller.ExportController;
import org.lamisplus.modules.central.domain.dto.RemoteUrlDTO;
import org.lamisplus.modules.central.domain.entity.*;
import org.lamisplus.modules.central.repository.*;
import org.lamisplus.modules.central.utility.HttpConnectionManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncServiceImpl implements SyncService {
    public static final int UN_ARCHIVED = 0;
    private final SyncHistoryRepository syncHistoryRepository;
    private final SyncHistoryTrackerRepository syncHistoryTrackerRepository;
    private final RemoteAccessTokenRepository remoteAccessTokenRepository;
    private final RemoteAccessTokenRepository accessTokenRepository;

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
        return syncHistoryTrackerRepository.findAllByIdSyncHistoryIdAndArchived(syncHistoryId, UN_ARCHIVED)
                .stream()
                .sorted(Comparator.comparing(SyncHistoryTracker::getTimeCreated).reversed())
                .collect(Collectors.toList());
    }

}
