package org.lamisplus.modules.central.service;

import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;

import java.util.HashMap;
import java.util.List;

public interface ExportService {
    String generateFilesForSyncing(Long facilityId, Boolean current);

    List<SyncHistoryTracker> exportAnyTable(String tableName, Long facilityId, String startName, String startDate, String endName, String endDate, String fileLocation, String uuid, String excludeColumn);

    String getDatimId(Long facilityId);

    String encryptCredentials(LoginVM login, String appKey, String history, String tracker, String fileName);
}
