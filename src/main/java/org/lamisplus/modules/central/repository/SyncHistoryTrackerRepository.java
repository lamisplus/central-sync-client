package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncHistoryTrackerRepository extends JpaRepository<SyncHistoryTracker, Long> {
}
