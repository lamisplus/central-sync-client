package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConfigRepository extends JpaRepository<Config, UUID> {
}