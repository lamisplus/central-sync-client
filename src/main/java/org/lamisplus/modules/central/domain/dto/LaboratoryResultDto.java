package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDateTime;

public interface LaboratoryResultDto {

	String getUuid();
	LocalDateTime getDateAssayed();
	LocalDateTime getDateResultReported();
	String getResultReported();
	String getDatimId();
	String getPersonUuid();
	Integer getArchived();
	LocalDateTime getDateModified();
	String getModifiedBy();
	Integer getTestId();
	String getLabTestName();
	String getTestUuid();
	
}
