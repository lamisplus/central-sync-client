package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.dto.ModuleProjection;
import org.lamisplus.modules.central.domain.entity.ConfigTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ConfigTableRepository extends JpaRepository<ConfigTable, UUID> {
    List<ConfigTable> findAllByConfigModuleId(UUID configModuleId);

    @Query(value = "SELECT sct.* FROM sync_config_table sct\n" +
            "INNER JOIN sync_config_module scm ON scm.id = sct.config_module_id\n" +
            "INNER JOIN sync_config sc ON sc.id = scm.config_id\n" +
            "WHERE sc.active IS TRUE", nativeQuery = true)
    List<ConfigTable> getTablesForSyncing();
}