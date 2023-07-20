package org.lamisplus.modules.central.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SyncHistoryRequest {
    private Long organisationUnitId;
    private String tableName;
    private Integer processed;
    private Long syncQueueId;
    private Long remoteAccessTokenId;
    private Integer uploadSize;
    private Integer processedSize;
    private List errorLog;
    private String filePath;

    public SyncHistoryRequest(Long organisationUnitId, String tableName, Integer uploadSize, List errorLog, String filePath) {
        this.organisationUnitId = organisationUnitId;
        this.tableName = tableName;
        this.uploadSize = uploadSize;
        this.errorLog = errorLog;
        this.filePath = filePath;
    }
}
