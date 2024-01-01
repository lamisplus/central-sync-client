package org.lamisplus.modules.central.installers;

import com.foreach.across.core.annotations.Installer;
import com.foreach.across.core.installers.AcrossLiquibaseInstaller;
import org.springframework.core.annotation.Order;

@Order(2)
@Installer(name = "client-sync-schema-installer",
        description = "Installs the required database tables for new client sync module",
        version = 3)
public class NewVersionSchemaInstaller extends AcrossLiquibaseInstaller {
    public NewVersionSchemaInstaller() {
        super("classpath:schema/schema-new-version.xml");
    }
}