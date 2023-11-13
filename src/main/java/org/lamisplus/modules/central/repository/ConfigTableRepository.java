package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.ConfigTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConfigTableRepository extends JpaRepository<ConfigTable, UUID> {
    List<ConfigTable> findAllByConfigModuleId(UUID configModuleId);
}