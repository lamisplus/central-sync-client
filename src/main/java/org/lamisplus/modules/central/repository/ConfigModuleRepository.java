package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.dto.ModuleProjection;
import org.lamisplus.modules.central.domain.entity.ConfigModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ConfigModuleRepository extends JpaRepository<ConfigModule, UUID> {
    List<ConfigModule> findAllByConfigId(UUID configId);
    @Query(value = "SELECT name, version FROM base_module", nativeQuery = true)
    List<ModuleProjection> getAppModules();

    @Query(value = "SELECT scm.module_name AS name, scm.version FROM sync_config_module scm\n" +
            "INNER JOIN sync_config sc ON sc.id = scm.config_id\n" +
            "WHERE sc.active IS TRUE", nativeQuery = true)
    List<ModuleProjection> getActiveConfigModules();
}
