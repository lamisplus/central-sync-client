package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface LaboratoryOrderDto {
	String getUuid();
	LocalDateTime getOrderDate();
	String getDatimId();
	String getPersonUuid();
	Integer getArchived();
	LocalDateTime getDateModified();
	String getModifiedBy();
	
}
