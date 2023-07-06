package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EacDto {
	String getDatimId();
	String getPersonUuid();
	String getUuid();
	LocalDate getFollowUpDate();
	String getComment();
	String getBarriersOthers();
	String getInterventionOthers();
	String getIntervention();
	String getBarriers();
	LocalDate getEacSessionDate();
	Integer getArchived();
	LocalDateTime getLastModifiedDate();
	String getLastModifiedBy();
	String getEacUuid();
	String getReferral();
	String getAdherence();
	String getStatus();
}
