package org.lamisplus.modules.central;

import com.foreach.across.config.AcrossApplication;
import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.context.configurer.ComponentScanConfigurer;

@AcrossApplication(
        modules = {
        })
public class CentralSyncModule extends AcrossModule {
    public static final String NAME = "CentralSyncModule";
    public CentralSyncModule() {
        super ();
        addApplicationContextConfigurer (new ComponentScanConfigurer(
                getClass ().getPackage ().getName () + ".repository",
                getClass ().getPackage ().getName () + ".service",
                getClass ().getPackage ().getName () + ".controller",
                getClass ().getPackage ().getName () + ".utility"
        ));
    }
    @Override
    public String getName() {
        return NAME;
    }
}
