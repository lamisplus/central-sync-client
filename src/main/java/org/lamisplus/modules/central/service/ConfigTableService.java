package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.ConfigTableDto;
import org.lamisplus.modules.central.domain.entity.ConfigTable;
import org.lamisplus.modules.central.domain.mapper.SyncMapper;
import org.lamisplus.modules.central.repository.ConfigTableRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigTableService {
    public final ConfigTableRepository repository;
    private final SyncMapper mapper;

    public ConfigTable Save(ConfigTable configTable){
        configTable.setId(java.util.UUID.randomUUID());
        return repository.save(configTable);
    }

    public ConfigTable FindById(UUID Id){
        return repository.findById(Id).orElse(null);
    }

    public List<ConfigTable> FindAll(){
        return repository.findAll();
    }

    public List<ConfigTable> FindAllByModuleId(UUID moduleId){
        return repository.findAllByConfigModuleId(moduleId);
    }

    public void Delete(UUID id){
        repository.deleteById(id);
    }

    public List<ConfigTable> getTablesForSyncing(){
        return repository.getTablesForSyncing();
    }

    public List<ConfigTableDto> getConfigTableByModuleId(UUID moduleId) {
        return mapper.toConfigTableDtoList(repository
                .findAllByConfigModuleId(moduleId));
    }
}
