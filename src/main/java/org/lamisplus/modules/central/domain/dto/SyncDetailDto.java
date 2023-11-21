package org.lamisplus.modules.central.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SyncDetailDto {
    private String syncHistoryUuid;
    private String syncHistoryTrackerUuid;
    private Long facilityId;
    //private String fileName;
    private String username;
    private String password;
}
