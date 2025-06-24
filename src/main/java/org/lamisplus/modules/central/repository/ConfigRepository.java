package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.UUID;

public interface ConfigRepository extends JpaRepository<Config, UUID> {

    @Query(value = "SELECT version FROM sync_config sc " +
            "WHERE sc.active IS TRUE LIMIT 1", nativeQuery = true)
    Optional<String> getActiveConfigVersion();
}