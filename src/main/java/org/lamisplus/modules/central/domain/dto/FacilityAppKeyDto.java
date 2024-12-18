package org.lamisplus.modules.central.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class FacilityAppKeyDto {
    private UUID id;
    @NotNull(message = "facility id is required")
    private Integer facilityId;
    @NotNull
    @NotEmpty(message = "facility name is required")
    private String facilityName;
    @NotNull
    @NotEmpty(message = "app key is required")
    private String appKey;
    @NotNull
    @NotEmpty(message = "server url is required")
    private String serverUrl;
}
