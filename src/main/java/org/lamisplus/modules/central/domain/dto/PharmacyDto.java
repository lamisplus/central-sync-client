package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PharmacyDto {
	String getDatimId();
	String getPersonUuid();
	String getUuid();
	LocalDate getVisitDate();
	String getRegimenName();
	String getDuration();
	String getCodeDescription();
	String getVisitId();
	String getMmdType();
	Integer getArchived();
	LocalDateTime getLastModifiedDate();
	LocalDate getNextAppointment();
	String getLastModifiedBy();

	String getRegimenLine();

	String getDsdModelType();
	String getDsdModel();
}
