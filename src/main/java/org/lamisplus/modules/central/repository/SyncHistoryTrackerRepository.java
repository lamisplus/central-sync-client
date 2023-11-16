package org.lamisplus.modules.central.repository;

import org.apache.kafka.common.internals.PartitionStates;
import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SyncHistoryTrackerRepository extends JpaRepository<SyncHistoryTracker, Long> {
    List<SyncHistoryTracker> findAllByIdSyncHistoryId(Long syncHistoryId);

    List<SyncHistoryTracker> findAllByIdSyncHistoryIdAndArchived(Long syncHistoryId, int archived);
}
