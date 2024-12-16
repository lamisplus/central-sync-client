package org.lamisplus.modules.central.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleStatus {
	private String name;
	private MessageType message;
	private String availableVersion;
	private String minimumVersion;
	private String maximumVersion;
}
