package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.ConfigDto;
import org.lamisplus.modules.central.domain.dto.ConfigModuleDto;
import org.lamisplus.modules.central.domain.dto.ConfigTableDto;
import org.lamisplus.modules.central.domain.entity.Config;
import org.lamisplus.modules.central.domain.entity.ConfigModule;
import org.lamisplus.modules.central.domain.entity.ConfigTable;
import org.lamisplus.modules.central.domain.mapper.SyncMapper;
import org.lamisplus.modules.central.service.ConfigModuleService;
import org.lamisplus.modules.central.service.ConfigService;
import org.lamisplus.modules.central.service.ConfigTableService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/sync-config")
public class SyncConfigController {
    private final ConfigService configService;
    private final ConfigModuleService configModuleService;
    private final ConfigTableService configTableService;
    private final SyncMapper mapper;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConfigDto> createConfig(@RequestBody ConfigDto configDto) {
        Config config = configService.Save(mapper.toConfig(configDto));

        for ( ConfigModuleDto configModuleDto:configDto.getConfigModules()) {
            ConfigModule configModule = mapper.toConfigModule(configModuleDto);
            configModule.setConfigId(config.getId());
            configModule = configModuleService.Save(configModule);

            for (ConfigTableDto configTableDto:configModuleDto.getConfigTables()) {
                ConfigTable configTable = mapper.toConfigTable(configTableDto);
                configTable.setConfigModuleId(configModule.getId());
                configTable = configTableService.Save(configTable);
            }
        }

        return ResponseEntity.ok(configDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConfigDto>> getAllConfigs() {
        List<Config> configs = configService.FindAll();
        return ResponseEntity.ok(ConvertConfigListToDtoList(configs));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConfigDto> getConfigById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(ConvertConfigToDto(configService.FindById(id)));
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> DeleteConfig(@PathVariable("id") UUID id) {
        configService.Delete(id);
        return ResponseEntity.accepted().build();
    }

    private List<ConfigDto> ConvertConfigListToDtoList(List<Config> configs) {
        List<ConfigDto> configDtos = new ArrayList<>();

        for (Config config : configs) {
            configDtos.add(ConvertConfigToDto(config));
        }
        return configDtos;
    }

    private ConfigDto ConvertConfigToDto(Config config) {
        ConfigDto configDto = mapper.toConfigDto(config);

        List<ConfigModuleDto> configModuleDtos = mapper.toConfigModuleDtoList(configModuleService.FindAllByConfigId(configDto.getId()));
        configDto.setConfigModules(configModuleDtos);

        for (ConfigModuleDto configModuleDto : configModuleDtos) {
            List<ConfigTableDto> configTableDtos = mapper.toConfigTableDtoList(configTableService.FindAllByModuleId(configModuleDto.getId()));
            configModuleDto.setConfigTables(configTableDtos);
        }
        return configDto;
    }
}

