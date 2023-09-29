package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ClinicDataDto {
	String getFacilityName();
	String getState	();
	String getLga	();
	String getDatimId();
	String getPersonUuid();
	String getHospitalNumber();
	LocalDate getVisitDate();
	String getClinicalStage ();
	String getFunctionalStatus();
	String getTbStatus();
	Double getBodyWeight();
	Double getHeight();
	Double getSystolic();
	String getDiastolic();
	String getPregnancyStatus();
	Integer getArchived();
	LocalDateTime getLastModifiedDate();
	LocalDate getNextAppointment();
	String getLastModifiedBy();
	String getUuid();
	String getHepatitisScreeningResult();
	String getRegimenType();
	String getRegimen();
	String getCd4Count();
	String getCd4SemiQuantitative();
	Integer getC4FlowCytometry();
	String getCd4Type();
	Boolean getIsCommencement();
	Long getCd4();
	Long getCd4Percentage();
	
}
