package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.CentralSyncModule;
import org.lamisplus.modules.central.domain.dto.MessageType;
import org.lamisplus.modules.central.domain.dto.ModuleProjection;
import org.lamisplus.modules.central.domain.dto.ModuleStatus;
import org.lamisplus.modules.central.domain.entity.ConfigModule;
import org.lamisplus.modules.central.repository.ConfigModuleRepository;
import org.springframework.stereotype.Service;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigModuleService {
    private static final String NOT_FOUND = "N/A";
    private static final String ERROR_MSG = "ERROR";
    private static final String SUCCESS_MSG = "SUCCESS";
    private final ConfigModuleRepository repository;

    public ConfigModule Save(ConfigModule configModule){
        configModule.setId(java.util.UUID.randomUUID());
        return repository.save(configModule);
    }

    public ConfigModule FindById(UUID Id){
        return repository.findById(Id).orElse(null);
    }

    public List<ConfigModule> FindAll(){
        return repository.findAll();
    }

    public List<ConfigModule> FindAllByConfigId(UUID configId){
        return repository.findAllByConfigId(configId);
    }

    public void Delete(UUID id){
        repository.deleteById(id);
    }

    /**
     * Module checking on the client sync.
     * @return ModuleStatus
     */
    public List<ModuleStatus> moduleCheck(){
        Boolean found = false;
        List<ModuleStatus> moduleStatuses = new ArrayList<>();
        //sync modules
        List<ConfigModule> syncModules = repository.getActiveConfigModules();
        //app modules
        List<ModuleProjection> appModules = repository.getAppModules();

        for (ConfigModule syncModule : syncModules) {
            found = false;
            for (ModuleProjection appModule : appModules) {
                //where there is a match
                if(syncModule.getModuleName().equals(appModule.getName())) {
                    if (ModuleCompatible(syncModule.getMinVersion(), syncModule.getMaxVersion(), appModule.getVersion())) {
                        moduleStatuses.add(new ModuleStatus(syncModule.getModuleName(),
                                MessageType.SUCCESS, appModule.getVersion(),
                                syncModule.getMinVersion(), syncModule.getMaxVersion()));
                    } else {
                        moduleStatuses.add(new ModuleStatus(syncModule.getModuleName(),
                                MessageType.WARNING, appModule.getVersion(),
                                syncModule.getMinVersion(), syncModule.getMaxVersion()));
                    }

                    found = true;
                    break;
                }
            }
            //if not found
            if(!found){
                //add to list
                moduleStatuses.add(new ModuleStatus(syncModule.getModuleName(), MessageType.ERROR, NOT_FOUND,
                        syncModule.getMinVersion(), syncModule.getMaxVersion()));
            }
        }
        return moduleStatuses;
    }

    private boolean ModuleCompatible(String sMinVersion, String sMaxVersion, String sVersion){
        DefaultArtifactVersion minVersion = new DefaultArtifactVersion(sMinVersion);
        DefaultArtifactVersion maxVersion = new DefaultArtifactVersion(sMaxVersion);
        DefaultArtifactVersion version = new DefaultArtifactVersion(sVersion);

        return version.compareTo(minVersion) >= 0 && version.compareTo(maxVersion) <= 0;
    }
}
