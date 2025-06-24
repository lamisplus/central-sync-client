package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.ConfigDto;
import org.lamisplus.modules.central.domain.dto.ConfigModuleDto;
import org.lamisplus.modules.central.domain.dto.ConfigTableDto;
import org.lamisplus.modules.central.domain.dto.ModuleStatus;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/sync/sync-config")
public class SyncConfigController {
    private final ConfigService configService;
    private final ConfigModuleService configModuleService;
    private final ConfigTableService configTableService;
    private final SyncMapper mapper;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createConfig(@RequestBody ConfigDto configDto) {
        configDto.setUploadDate(LocalDateTime.now());
        configService.save(mapper.toConfig(configDto));

        for ( ConfigModuleDto configModuleDto:configDto.getConfigModules()) {
            ConfigModule configModule = mapper.toConfigModule(configModuleDto);
            configModuleService.save(configModule);

            for (ConfigTableDto configTableDto:configModuleDto.getConfigTables()) {
                ConfigTable configTable = mapper.toConfigTable(configTableDto);
                configTableService.save(configTable);
            }
        }

        return ResponseEntity.ok("Saved successfully");
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConfigDto>> getAllConfigs() {
        List<Config> configs = configService.findAll();
        return ResponseEntity.ok(convertConfigListToDtoList(configs));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConfigDto> getConfigById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(convertConfigToDto(configService.findById(id)));
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteConfig(@PathVariable("id") UUID id) {
        configService.delete(id);
        return ResponseEntity.accepted().build();
    }

    private List<ConfigDto> convertConfigListToDtoList(List<Config> configs) {
        List<ConfigDto> configDtos = new ArrayList<>();

        for (Config config : configs) {
            configDtos.add(convertConfigToDto(config));
        }
        return configDtos;
    }

    private ConfigDto convertConfigToDto(Config config) {
        ConfigDto configDto = mapper.toConfigDto(config);

        List<ConfigModuleDto> configModuleDtos = mapper.toConfigModuleDtoList(configModuleService.findAllByConfigId(configDto.getId()));
        configDto.setConfigModules(configModuleDtos);

        for (ConfigModuleDto configModuleDto : configModuleDtos) {
            List<ConfigTableDto> configTableDtos = mapper.toConfigTableDtoList(configTableService.findAllByModuleId(configModuleDto.getId()));
            configModuleDto.setConfigTables(configTableDtos);
        }
        return configDto;
    }

    @GetMapping(value = "/modules")
    public ResponseEntity<List<ModuleStatus>> getModuleCheck() {
        return ResponseEntity.ok(configModuleService.moduleCheck());
    }
}

