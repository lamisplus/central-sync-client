package org.lamisplus.modules.central.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SyncHistoryResponse {
    private Long id;
    private LocalDateTime dateLastSync;
    private Long organisationUnitId;
    private String tableName;
    private Integer processed;
    private Long syncQueueId;
    private Long remoteAccessTokenId;
    private Integer uploadSize;
    private Integer processedSize;
    private String facilityName;
}
