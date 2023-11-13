package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.entity.ConfigModule;
import org.lamisplus.modules.central.repository.ConfigModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigModuleService {
    public final ConfigModuleRepository repository;

    public ConfigModule Save(ConfigModule configModule){
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
}
