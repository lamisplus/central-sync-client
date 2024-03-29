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

	//overriding equals() method
	/*@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		return this.getName() == ((ModuleStatus) obj).getName() && this.getVersion() == ((ModuleStatus) obj).getVersion();
	}*/
}
