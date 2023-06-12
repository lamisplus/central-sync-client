package org.lamisplus.modules.central.service;
import org.lamisplus.modules.central.domain.dto.SyncHistoryRequest;
import org.lamisplus.modules.central.domain.dto.SyncHistoryResponse;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SyncHistoryService {
    private final SyncHistoryRepository syncHistoryRepository;

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
}
