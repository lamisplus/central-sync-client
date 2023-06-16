package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDateTime;

public interface LaboratoryTestDto {

	String getUuid();
	String getLabTestName();
	String getGroupName();
	String getViralLoadIndication();
	String getUnitMeasurement();
	String getDatimId();
	String getPersonUuid();
	Integer getArchived();
	LocalDateTime getDateModified();
	String getModifiedBy();
	
}
