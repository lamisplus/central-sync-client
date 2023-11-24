package org.lamisplus.modules.central.service;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.SyncHistoryRequest;
import org.lamisplus.modules.central.domain.dto.SyncHistoryResponse;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.lamisplus.modules.central.repository.SyncHistoryTrackerRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncHistoryService {
    public static final String SYNCED = "Synced";
    private final SyncHistoryRepository syncHistoryRepository;
    private final SyncHistoryTrackerRepository syncHistoryTrackerRepository;

    private String getFacilityNameById(Long facilityId) {
        Optional<String> optional = syncHistoryRepository.getFacilityNameById(facilityId);
        return optional.orElse("None");
    }
    public SyncHistoryResponse saveSyncHistory(SyncHistoryRequest request) {
        SyncHistory syncHistory = new SyncHistory();
        syncHistory.setOrganisationUnitId(request.getOrganisationUnitId());
        syncHistory.setTableName(request.getTableName());
        syncHistory.setUploadSize(request.getUploadSize());
        syncHistory.setProcessed(3); //default
        syncHistory.setDateLastSync(LocalDateTime.now());
        syncHistory.setErrorLog(request.getErrorLog());
        syncHistory.setFilePath(request.getFilePath());
        syncHistory.setGenKey(request.getGenKey());
        syncHistoryRepository.save(syncHistory);

        return entityToDto(syncHistory);
    }

    public SyncHistoryResponse updateSyncHistory(SyncHistoryRequest request, Long id) {
        SyncHistory syncHistory = new SyncHistory();
        syncHistory.setOrganisationUnitId(request.getOrganisationUnitId());
        syncHistory.setProcessedSize(request.getProcessedSize());
        syncHistory.setProcessed(request.getProcessed());
        syncHistory.setDateLastSync(LocalDateTime.now());
        syncHistoryRepository.save(syncHistory);

        return entityToDto(syncHistory);
    }

    public SyncHistoryResponse getSyncHistoryById(Long syncId) {
        SyncHistory syncHistory = getSyncHistory(syncId);
        return entityToDto(syncHistory);
    }

    public SyncHistory getSyncHistory(Long syncId) {
        return syncHistoryRepository.findById(syncId).orElseThrow(() ->
                new IllegalArgumentException("could not find sync history with id: " + syncId));
    }

    public List<SyncHistoryResponse> getSyncHistories() {
        List<SyncHistory> syncHistories = syncHistoryRepository.findAll().stream()
                .sorted(Comparator.comparing(SyncHistory::getDateLastSync).reversed())
                .collect(Collectors.toList());

        return entitiesToDtos(syncHistories);
    }

    public List<SyncHistoryResponse> entitiesToDtos(List<SyncHistory> syncHistoryList) {
        List<SyncHistoryResponse> syncHistoryResponseList = new ArrayList<>();
        for (SyncHistory syncHistory : syncHistoryList) {
            syncHistoryResponseList.add(entityToDto(syncHistory));
        }

        return syncHistoryResponseList;
    }

    public SyncHistoryResponse entityToDto(SyncHistory entity) {
        SyncHistoryResponse response = new SyncHistoryResponse();
        String facilityName = getFacilityNameById(entity.getOrganisationUnitId());
        response.setFacilityName(facilityName);
        response.setId(entity.getId());
        response.setOrganisationUnitId(entity.getOrganisationUnitId());
        response.setUploadSize(entity.getUploadSize());
        response.setProcessed(entity.getProcessed());
        response.setProcessedSize(entity.getProcessedSize());
        response.setDateLastSync(entity.getDateLastSync());
        response.setTableName(entity.getTableName());
        response.setErrorLog(entity.getErrorLog());
        response.setUuid(entity.getUuid());
        response.setGenKey(entity.getGenKey());
        response.setPercentageSynced(getPercentageSynced(entity.getUuid()));

        return response;
    }

    public void updateSyncHistory(String fileName, Integer status) {
        Optional<SyncHistory> existingRecord = syncHistoryRepository.findByTableName(fileName);
        if (existingRecord.isPresent()) {
            SyncHistory history = existingRecord.get();
            history.setDateLastSync(LocalDateTime.now());
            history.setProcessed(status);
            syncHistoryRepository.save(history);
        }
    }

    public Boolean updateSyncHistoryTracker(Long syncHistoryTrackerId) {
        Boolean updated = false;
        Optional<SyncHistoryTracker> existingRecord = syncHistoryTrackerRepository.findById(syncHistoryTrackerId);
        if (existingRecord.isPresent()) {
            SyncHistoryTracker historyTracker = existingRecord.get();
            historyTracker.setTimeCreated(LocalDateTime.now());
            historyTracker.setStatus(SYNCED);
            syncHistoryTrackerRepository.save(historyTracker);
            updated = true;
        }
        return updated;
    }

    private float getPercentageSynced(UUID syncHistoryUuid){
        int allFiles = syncHistoryTrackerRepository.countBySyncHistoryUuid(syncHistoryUuid);
        int syncedFile = syncHistoryTrackerRepository.countBySyncHistoryUuidAndStatus(syncHistoryUuid, SYNCED);
        return (Float.valueOf(syncedFile)/Float.valueOf(allFiles)) * Float.valueOf(100);
    }
}
