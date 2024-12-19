package org.lamisplus.modules.central.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ConfigTableDto {
    private UUID id;
    private String tableName;
    private String updateColumn;
    private String excludeColumns;
    private Boolean hasFacilityId;
    private UUID configModuleId;
    private Boolean archived;
}
