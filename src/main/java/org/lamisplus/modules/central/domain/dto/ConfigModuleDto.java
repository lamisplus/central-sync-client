package org.lamisplus.modules.central.domain.dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class ConfigModuleDto {
    private UUID id;
    private String moduleName;
    private String minVersion;
    private String maxVersion;
    private String mainVersion;
    private UUID configId;
    private List<ConfigTableDto> configTables;
}
