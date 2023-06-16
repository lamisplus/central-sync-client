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
	//LocalDate getDateOfBirth();
	//Integer getAge();
	
}
