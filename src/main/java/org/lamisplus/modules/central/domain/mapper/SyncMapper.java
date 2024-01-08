package org.lamisplus.modules.central.domain.mapper;

import org.lamisplus.modules.central.domain.dto.ConfigDto;
import org.lamisplus.modules.central.domain.dto.ConfigModuleDto;
import org.lamisplus.modules.central.domain.dto.ConfigTableDto;
import org.lamisplus.modules.central.domain.dto.FacilityAppKeyDto;
import org.lamisplus.modules.central.domain.entity.Config;
import org.lamisplus.modules.central.domain.entity.ConfigModule;
import org.lamisplus.modules.central.domain.entity.ConfigTable;
import org.lamisplus.modules.central.domain.entity.FacilityAppKey;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SyncMapper {
    Config toConfig(ConfigDto configDto);
    ConfigModule toConfigModule(ConfigModuleDto configModuleDto);
    ConfigTable toConfigTable(ConfigTableDto configTableDto);
    ConfigDto toConfigDto(Config config);
    ConfigModuleDto toConfigModuleDto(ConfigModule configModule);
    ConfigTableDto toConfigTableDto(ConfigTable configTable);
    FacilityAppKeyDto toFacilityAppKeyDto(FacilityAppKey facilityAppKey);

    List<Config> toConfigList(List<ConfigDto> configDtoList);
    List<ConfigModule> toConfigModuleList(List<ConfigModuleDto> configModuleDtoList);
    List<ConfigTable> toConfigTableList(List<ConfigTableDto> configTableDtoList);
    List<ConfigDto> toConfigDtoList(List<Config> configList);
    List<ConfigModuleDto> toConfigModuleDtoList(List<ConfigModule> configModuleList);
    List<ConfigTableDto> toConfigTableDtoList(List<ConfigTable> configTableList);
    List<FacilityAppKeyDto> toFacilityAppKeyDtoList(List<FacilityAppKey> facilityAppKeys);
}
