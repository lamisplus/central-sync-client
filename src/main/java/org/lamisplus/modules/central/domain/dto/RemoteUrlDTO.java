package org.lamisplus.modules.central.domain.dto;

import lombok.Data;

@Data
public class RemoteUrlDTO {
    private Long id;
    private String url;
    private Long facilityId;
    private String username;

}
