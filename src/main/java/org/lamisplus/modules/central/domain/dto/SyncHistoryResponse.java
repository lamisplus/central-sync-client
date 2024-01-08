package org.lamisplus.modules.central.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private Object messageLog;
    private UUID uuid;
    private float percentageSynced;
    private String genKey;
    private boolean hasError;
}
