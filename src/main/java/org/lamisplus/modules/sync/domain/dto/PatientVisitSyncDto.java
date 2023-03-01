package org.lamisplus.modules.sync.domain.dto;

import java.time.LocalDateTime;

public interface PatientVisitSyncDto {
    public Long getId();
    public LocalDateTime getVisitStartDate();
    public LocalDateTime getVisitEndDate();
    public String getUuid();
    public Integer getArchived();
    public LocalDateTime getCreatedDate();

    public String getCreatedBy();

    public LocalDateTime getLastModifiedDate();

    public String getLastModifiedBy();
    public Long getFacilityId();
}
