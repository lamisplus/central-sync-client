package org.lamisplus.modules.central.domain.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FacilityAppKeyDto {
    private UUID id;
    private Integer facilityId;
    private String facilityName;
    private String appKey;
}
