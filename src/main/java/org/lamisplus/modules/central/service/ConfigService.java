package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.entity.Config;
import org.lamisplus.modules.central.repository.ConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigService {
    public final ConfigRepository repository;

    public Config Save(Config config){
        return repository.save(config);
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
}
