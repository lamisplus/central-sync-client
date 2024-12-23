package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Config save(Config config){
        config.setUploadDate(LocalDateTime.now());
        config.setActive(true);
        inactivatePreviousConfig();
        return repository.save(config);
    }

    public Optional<String> getActiveConfig(){
        return repository.getActiveConfigVersion();
    }

    public Config findById(UUID id){
        return repository.findById(id).orElse(null);
    }

    public List<Config> findAll(){
        return repository.findAll();
    }

    public void delete(UUID id){
        repository.deleteById(id);
    }

    private void inactivatePreviousConfig(){
        List<Config> configs = findAll();
        for(Config config:configs){
            config.setActive(false);
            repository.save(config);
        }
    }
}
