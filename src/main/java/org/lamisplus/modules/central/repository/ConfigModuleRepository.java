package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.ConfigModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConfigModuleRepository extends JpaRepository<ConfigModule, UUID> {
    List<ConfigModule> findAllByConfigId(UUID configId);
}
