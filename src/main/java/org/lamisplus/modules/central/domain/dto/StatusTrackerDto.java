package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface StatusTrackerDto {
	String getDatimId();
	String getPersonUuid();
	String getUuid();
	Integer getArchived();
	LocalDateTime getLastModifiedDate();
	String getLastModifiedBy();
	String getHivStatus();
	LocalDate getStatusDate();

	String getCauseOfDeath();
	String getVaCauseOfDeath();
}
