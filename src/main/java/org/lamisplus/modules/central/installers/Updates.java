package org.lamisplus.modules.central.installers;

import com.foreach.across.core.annotations.Installer;
import com.foreach.across.core.installers.AcrossLiquibaseInstaller;
import org.springframework.core.annotation.Order;

@Order(2)
@Installer(name = "schema-installer-update", description = "Updates the required database tables for sync data",
        version = 1)
public class Updates extends AcrossLiquibaseInstaller {
    public Updates() {
        super("classpath:schema/updates.xml");
    }
}
