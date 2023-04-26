package org.lamisplus.modules.central.domain.dto;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class SyncQueueDto implements Serializable {

    private Long id;

    private String fileName;

    private String tableName;

    private Long organisationUnitId;

    private LocalDateTime dateCreated;

    private Integer processed;

    private Integer receivedSize;

    private Integer processedSize;

    private String createdBy;
}
