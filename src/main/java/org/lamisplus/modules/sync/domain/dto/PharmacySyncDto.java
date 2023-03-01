package org.lamisplus.modules.sync.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PharmacySyncDto {

    public Long getId();

    public LocalDate getVisitDate();

    public Boolean getAdrScreened();

    public Boolean getPrescriptionError();

    public Boolean getAdherence();

    public String getMmdType();

    public LocalDate getNextAppointment();

    public Object getExtra();
    
    public Object getAdverseDrugReactions();
    
    public Boolean getIsDevolve();
    
    public Integer getRefillPeriod();
    
    public String getDeliveryPoint();
    
    public  String getDsdModel();
    
    public  String getDsdModelType();
    
    public  String getRefill();
    
    public  String getRefillType();
    
    public  String getIptType();
    
    public String getVisitType();
    
    public Object getIpt();

    public String getUuid();
    public Integer getArchived();
    public LocalDateTime getCreatedDate();

    public String getCreatedBy();

    public LocalDateTime getLastModifiedDate();

    public String getLastModifiedBy();
    public Long getFacilityId();
}
