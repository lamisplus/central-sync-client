package org.lamisplus.modules.central.installers;

import com.foreach.across.core.annotations.Installer;
import com.foreach.across.core.installers.AcrossLiquibaseInstaller;
import org.springframework.core.annotation.Order;

@Order(1)
@Installer(name = "central-sync-schema-installer",
        description = "Installs the required database tables for central sync module",
        version = 7)
public class SchemaInstaller extends AcrossLiquibaseInstaller {
    public SchemaInstaller() {
        super("classpath:schema/schema.xml");
    }
}