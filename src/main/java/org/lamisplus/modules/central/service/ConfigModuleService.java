package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.ConfigModuleDto;
import org.lamisplus.modules.central.domain.dto.MessageType;
import org.lamisplus.modules.central.domain.dto.ModuleProjection;
import org.lamisplus.modules.central.domain.dto.ModuleStatus;
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
    private final ConfigModuleRepository repository;
    private final SyncMapper mapper;

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
            if(syncModule.getModuleName().contains("Base")){
                moduleStatuses.add(new ModuleStatus(syncModule.getModuleName(),
                        MessageType.SUCCESS, syncModule.getMinVersion(),
                        syncModule.getMinVersion(), syncModule.getMaxVersion()));
                continue;
            }
            for (ModuleProjection appModule : appModules) {
                //where there is a match
                if(syncModule.getModuleName().equals(appModule.getName())) {
                    //2.0.0.0
                    String appV;
                   if(appModule.getVersion().length() > 7) {
                        appV = appModule.getVersion().substring(0, appModule.getVersion().length() - 1);
                    } else {
                        appV= appModule.getVersion();
                    }

                    int max = Integer.valueOf(syncModule.getMaxVersion().replace(".", ""));
                    int min = Integer.valueOf(syncModule.getMinVersion().replace(".", ""));
                    int appVersion = Integer.valueOf(appV.replace(".", ""));
                    if (appVersion >= min && appVersion <= max) {
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

    public List<ConfigModuleDto> getConfigModuleByModuleId(UUID configId) {
        return mapper.toConfigModuleDtoList(repository
                .findAllByConfigId(configId));
    }
}
