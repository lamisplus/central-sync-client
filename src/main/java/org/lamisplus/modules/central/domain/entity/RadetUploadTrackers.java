package org.lamisplus.modules.central.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "central_radet_upload_tracker")
public class RadetUploadTrackers {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ipCode;
    private String ipName;
    private Long facilityId;
    private String facilityName;
    private String state;
    private String lga;
    private Long fiscalYear;
    private String reportingQuarter;
    private String createdDate;
}
