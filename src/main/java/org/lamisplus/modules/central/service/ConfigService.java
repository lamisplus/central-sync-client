package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hpsf.GUID;
import org.lamisplus.modules.central.domain.entity.Config;
import org.lamisplus.modules.central.repository.ConfigRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigService {
    public final ConfigRepository repository;

    public Config Save(Config config){
        config.setId(java.util.UUID.randomUUID());
        config.setUploadDate(LocalDateTime.now());
        config.setActive(true);
        InactivatePreviousConfig();
        return repository.save(config);
    }

    public Optional<String> getActiveConfig(){
        return repository.getActiveConfigVersion();
    }

    public Config FindById(UUID Id){
        return repository.findById(Id).orElse(null);
    }

    public List<Config> FindAll(){
        return repository.findAll();
    }

    public void Delete(UUID id){
        repository.deleteById(id);
    }

    private void InactivatePreviousConfig(){
        List<Config> configs = FindAll();
        for(Config config:configs){
            config.setActive(false);
            repository.save(config);
        }
    }
}
