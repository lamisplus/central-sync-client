package org.lamisplus.modules.central.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDetail implements Serializable {
    private String version;
    private Boolean init;
    private UUID history;
    private String key;
    private List<FileTrackerDTO> fileTracker;
}
