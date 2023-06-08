package org.lamisplus.modules.central.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.domain.entity.Visit;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface HivEnrollmentSyncDto {
    public Long getId();
    
    public String getUniqueId();
    
    public Long getEntryPointId();
    
    public Long getTargetGroupId();
    
    public LocalDate getDateConfirmedHiv();
    
    public LocalDate getDateEnrolledPMTCT();
    
    public Long getSourceOfReferrerId();
    
    public ZonedDateTime getTimeHivDiagnosis();
    
    public Boolean getPregnant();
    
    public Boolean getBreastfeeding();
    
    public LocalDate getDateOfRegistration();
    
    public Long getStatusAtRegistrationId();
    
    public Long getEnrollmentSettingId();
    
    public LocalDate getDateStarted();
    
    public Boolean getSendMessage();
    
    public String getUuid();
    
    public int getArchived();
    
    public String getFacilityName();
    
    public String getOvcNumber();
    
    public String getHouseHoldNumber();
    
    public String getCareEntryPointOther();
    
    public String getReferredToOVCPartner();
    
    public String getReferredFromOVCPartner();
    
    public LocalDate getDateReferredToOVCPartner();
    
    public LocalDate getDateReferredFromOVCPartner();
    
    public LocalDate getDateOfLpm();
    
    public Long getPregnancyStatusId();
    
    public Long getTbStatusId();
    
    public String getLipName();
    
    public Object getOvcServiceProvided();

    public LocalDateTime getCreatedDate();

    public String getCreatedBy();

    public LocalDateTime getLastModifiedDate();

    public String getLastModifiedBy();
}
