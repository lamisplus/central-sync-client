package org.lamisplus.modules.central.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BiometricDto {
	String getDatimId();
	String getPersonUuid();
	String getUuid();
	String getTemplateType();
	LocalDate getEnrollmentDate();
	Boolean getIso();
	String getDeviceName();
	Boolean getVersionIso20();
	Integer getImageQuality();
	Integer getCount();
	Integer getRecapture();
	Integer getHashed();
	byte[] getTemplate();
	Integer getArchived();
	LocalDateTime getLastModifiedDate();
	String getLastModifiedBy();
}
