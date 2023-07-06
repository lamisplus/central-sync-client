package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EnrollmentDto {
	String getOvcNumber();
	String getHouseHoldNumber();
	LocalDate getDateConfirmedHiv();
	LocalDate getDateOfRegistration();
	LocalDate getDateStarted();
	String getEntryPoint();
	String getEnrollmentSetting();
	String getUniqueId();
	String getFacilityName();
	String getPregnancyStatus();
	String getTbStatus();
	String getStatusAtRegistration();
	String getTargetGroup();
	String getDatimId();
	String getPersonUuid();
	String getUuid();
	Integer getArchived();
	LocalDateTime getLastModifiedDate();
	String getLastModifiedBy();
}
