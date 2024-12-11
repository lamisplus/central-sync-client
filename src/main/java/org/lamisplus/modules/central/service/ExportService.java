package org.lamisplus.modules.central.service;

import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.domain.entity.ConfigTable;
import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;
import java.util.List;

public interface ExportService {
    String generateFilesForSyncing(Long facilityId, boolean current);

    List<SyncHistoryTracker> exportAnyTable(ConfigTable configTable, long facilityId, String startName,
                                            String startDate, String endDate, String fileLocation,
                                            String uuid, String excludeColumn);

    String getDatimId(Long facilityId);

    String encryptCredentials(LoginVM login, String appKey, String history, String tracker, String fileName);

    String encryptMessage(String message, String appKey);
}
