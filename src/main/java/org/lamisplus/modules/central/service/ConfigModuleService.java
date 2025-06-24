package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.ConfigModuleDto;
import org.lamisplus.modules.central.domain.dto.MessageType;
import org.lamisplus.modules.central.domain.dto.ModuleProjection;
import org.lamisplus.modules.central.domain.dto.ModuleStatus;
import org.lamisplus.modules.central.domain.dto.Version;
import org.lamisplus.modules.central.domain.entity.ConfigModule;
import org.lamisplus.modules.central.domain.mapper.SyncMapper;
import org.lamisplus.modules.central.repository.ConfigModuleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigModuleService {
    private static final String NOT_FOUND = "N/A";
    public static final String BASE_MODULE = "BaseModule";
    public static final int ZERO = 0;
    private final ConfigModuleRepository repository;
    private final SyncMapper mapper;

    public ConfigModule save(ConfigModule configModule){
        return repository.save(configModule);
    }

    public ConfigModule findById(UUID id){
        return repository.findById(id).orElse(null);
    }

    public List<ConfigModule> findAll(){
        return repository.findAll();
    }

    public List<ConfigModule> findAllByConfigId(UUID configId){
        return repository.findAllByConfigId(configId);
    }

    public void delete(UUID id){
        repository.deleteById(id);
    }

    /**
     * Module checking on the client sync.
     * @return ModuleStatus
     */
    public List<ModuleStatus> moduleCheck(){
        boolean found = false;
        List<ModuleStatus> moduleStatuses = new ArrayList<>();
        //sync modules
        List<ConfigModule> syncModules = repository.getActiveConfigModules();
        //app modules
        List<ModuleProjection> appModules = repository.getAppModules();

        for (ConfigModule syncModule : syncModules) {
            found = false;
            if(syncModule.getModuleName().contains(BASE_MODULE)){
                moduleStatuses.add(new ModuleStatus(syncModule.getModuleName(),
                        MessageType.SUCCESS, syncModule.getMinVersion(), syncModule.getMainVersion(),
                        syncModule.getMinVersion(), syncModule.getMaxVersion()));
                continue;
            }
            for (ModuleProjection appModule : appModules) {
                //where there is a match
                if(syncModule.getModuleName().equals(appModule.getName())) {
                    //2.0.0.0
                    Version max = Version.createVersion(syncModule.getMaxVersion());
                    Version min = Version.createVersion(syncModule.getMinVersion());
                    Version mainVersion = Version.createVersion(syncModule.getMainVersion());
                    Version appVersion = Version.createVersion(appModule.getVersion());

                    moduleStatuses.add(checkModuleSpecificVersion(syncModule, appModule, max, min, appVersion, mainVersion));

                    found = true;
                    break;
                }
            }
            //if not found
            if(!found){
                //add to list
                moduleStatuses.add(new ModuleStatus(syncModule.getModuleName(), MessageType.ERROR, NOT_FOUND,
                        syncModule.getMinVersion(), syncModule.getMainVersion(), syncModule.getMaxVersion()));
            }
        }
        return moduleStatuses;
    }

    /**
     * check Module Version.
     * @param syncModule
     * @param appModule
     * @param max
     * @param min
     * @param appVersion
     * @param mainVersion
     * @return ModuleStatus
     */
    private static ModuleStatus checkModuleSpecificVersion(ConfigModule syncModule, ModuleProjection appModule,
                                                           Version max, Version min, Version appVersion, Version mainVersion) {
        if(mainVersion.compareTo(appVersion) == ZERO){
            return new ModuleStatus(syncModule.getModuleName(),
                    MessageType.SUCCESS, appModule.getVersion(), syncModule.getMainVersion(),
                    syncModule.getMinVersion(), syncModule.getMaxVersion());
        } else if (appVersion.compareTo(min) > ZERO && appVersion.compareTo(max) < ZERO) {
            return new ModuleStatus(syncModule.getModuleName(),
                    MessageType.WARNING, appModule.getVersion(), syncModule.getMainVersion(),
                    syncModule.getMinVersion(), syncModule.getMaxVersion());
        } else {
            return new ModuleStatus(syncModule.getModuleName(),
                    MessageType.ERROR, appModule.getVersion(), syncModule.getMainVersion(),
                    syncModule.getMinVersion(), syncModule.getMaxVersion());
        }
    }

    public List<ConfigModuleDto> getConfigModuleByModuleId(UUID configId) {
        return mapper.toConfigModuleDtoList(repository
                .findAllByConfigId(configId));
    }
}
