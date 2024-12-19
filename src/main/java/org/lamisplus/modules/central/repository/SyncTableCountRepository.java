package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.SyncTableCount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SyncTableCountRepository extends JpaRepository<SyncTableCount, UUID> {
}