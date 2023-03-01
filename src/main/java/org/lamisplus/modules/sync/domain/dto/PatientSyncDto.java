package org.lamisplus.modules.sync.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PatientSyncDto {
    public Long getId();
    public Boolean getActive();
    public String getSurname();
    public String getFirstName();
    public String getOtherName();
    public LocalDate getDateOfBirth();
    public Boolean getDeceased();
    public LocalDateTime getDeceasedDateTime();
    public Long getMaritalStatusId();
    public String getNinNumber();
    public String getEmrId();
    public Long getGenderId();
    public Long getSexId();
    public Long getEmploymentStatusId();
    public Long getEducationId();
    public Long getOrganizationId();
    public Object getContactPoint();
    public Object getAddress();
    public Object getIdentifier();
    public Object getContact();
    public LocalDate getDateOfRegistration();
    public Boolean getIsDateOfBirthEstimated();
    public Long getFacilityId();
    public  String getUuid();
    public int getArchived();

    public LocalDateTime getCreatedDate();
    
    public String getCreatedBy();
    
    public LocalDateTime getLastModifiedDate();
    
    public String getLastModifiedBy();
}
