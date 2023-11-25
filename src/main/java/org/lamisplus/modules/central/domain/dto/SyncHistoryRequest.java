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
    private List messageLog;
    private String filePath;
    private String genKey;

    public SyncHistoryRequest(Long organisationUnitId, String tableName, Integer uploadSize, List messageLog, String filePath, String genKey) {
        this.organisationUnitId = organisationUnitId;
        this.tableName = tableName;
        this.uploadSize = uploadSize;
        this.messageLog = messageLog;
        this.filePath = filePath;
        this.genKey = genKey;
    }
}
