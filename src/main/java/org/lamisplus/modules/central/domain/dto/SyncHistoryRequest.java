package org.lamisplus.modules.central.domain.dto;

import lombok.Data;

@Data
public class SyncHistoryRequest {
    private Long organisationUnitId;
    private String tableName;
    private Integer processed;
    private Long syncQueueId;
    private Long remoteAccessTokenId;
    private Integer uploadSize;
    private Integer processedSize;

    public SyncHistoryRequest(Long organisationUnitId, String tableName, Integer uploadSize) {
        this.organisationUnitId = organisationUnitId;
        this.tableName = tableName;
        this.uploadSize = uploadSize;
    }
}
