package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ObservationDto {
	LocalDate getDateOfObservation();
	String getDatimId();
	String getPersonUuid();
	String getUuid();
	Integer getArchived();
	LocalDateTime getLastModifiedDate();
	String getLastModifiedBy();
	String getData();
	String getType();
}
