package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDateTime;

public interface LaboratorySampleDto {

	String getUuid();
	Integer getTestId();
	LocalDateTime getDateSampleCollected();
	String getDatimId();
	String getPersonUuid();
	Integer getArchived();
	LocalDateTime getDateModified();
	String getModifiedBy();
	String getSampleType();

	String getTestUuid();
	
}
