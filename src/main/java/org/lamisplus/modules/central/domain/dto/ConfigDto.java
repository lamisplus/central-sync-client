package org.lamisplus.modules.central.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ConfigDto {
    private UUID id;
    private String name;
    private String version;
    private LocalDateTime releaseDate;
    private LocalDateTime uploadDate;
    private Boolean active;
    private List<ConfigModuleDto> configModules;
}
