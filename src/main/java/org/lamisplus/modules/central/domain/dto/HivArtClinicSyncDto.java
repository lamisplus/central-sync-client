package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface HivArtClinicSyncDto {

    public Long getId();

    public LocalDate getVisitDate();

    public Long getCd4();

    public Long getCd4Percentage();

    public Boolean getIsCommencement();

    public Long getFunctionalStatusId();

    public Long getClinicalStageId();

    public String getClinicalNote();

    public String getUuid();

    public long getRegimenId();
    public long getRegimenTypeId();

    public Long getArtStatusId();

    public Long getWhoStagingId();

    public String getOiScreened();

    public String getStiIds();

    public String getStiTreated();

    public Object getOpportunisticInfections();

    public String getAdrScreened();

    public Object getAdverseDrugReactions();

    public String getAdherenceLevel();

    public Object getAdheres();

    public LocalDate getNextAppointment();

    public LocalDate getLmpDate();

    public Object getTbScreen();

    public Boolean getIsViralLoadAtStartOfArt();

    public Double getViralLoadAtStartOfArt();

    public LocalDate getDateOfViralLoadAtStartOfArt();

    public String getCryptococcalScreeningStatus();
    public String getCervicalCancerScreeningStatus();
    public String getCervicalCancerTreatmentProvided();
    public String getHepatitisScreeningResult();
    public String getFamilyPlaning();
    public String getOnFamilyPlaning();
    public String getLevelOfAdherence();
    public String getTbStatus();
    public String getTbPrevention();

    public Object getARVDrugsRegimen();

    public Object getViralLoadOrder();

    public Object getextra();

    public String  getCd4Count();

    public String  getCd4SemiQuantitative();

    public Integer  getCd4FlowCytometry();

    public String  getPregnancyStatus();
    public Integer getArchived();
    public LocalDateTime getCreatedDate();

    public String getCreatedBy();

    public LocalDateTime getLastModifiedDate();

    public String getLastModifiedBy();
    public Long getFacilityId();
}
