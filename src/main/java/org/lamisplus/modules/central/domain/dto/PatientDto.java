package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PatientDto {

	String getPersonUuid();
	String getFacilityName();
	String getState	();
	String getLga	();
	String getDatimId();
	String getSurname();
	String getFirstName();
	String getOtherName();
	LocalDate getDateOfBirth();
	String getHospitalNumber();
	LocalDate getDateOfRegistration();
	String getMaritalStatus();
	String getEducation();
	String getOccupation();
	String getTown();
	int getArchived();
	String getSex();
	String getResidentialState();
	String getResidentialLga();
	String getAddress();
	String getPhone();
	LocalDateTime getLastModifiedDate();
	String getLastModifiedBy();
}
