package org.lamisplus.modules.sync.domain.dto;

import java.time.LocalDateTime;

public interface TriageVitalSignSyncDto {
    public Long getId();
    public Double getBodyWeight();
    public Double getDiastolic();
    public LocalDateTime getCaptureDate();
    public Double getHeight();
    public Double getTemperature();
    public Double getPulse();
    public Double getRespiratoryRate();
    public Double getSystolic();
    public Integer getArchived();
    public String getUuid();

    public LocalDateTime getCreatedDate();

    public String getCreatedBy();

    public LocalDateTime getLastModifiedDate();

    public String getLastModifiedBy();

    public int getFacilityId();
}
