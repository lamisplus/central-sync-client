package org.lamisplus.modules.central.service;

import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;

import java.util.List;

public interface ExportService {
    String bulkExport(Long facilityId, Boolean current);

    List<SyncHistoryTracker> exportAnyTable(String tableName, Long facilityId, String startName, String startDate, String endName, String endDate, String fileLocation, String uuid, String excludeColumn);

    String getDatimId(Long facilityId);

    void decrypt(String key, String fileLocation, String tableName);
}
