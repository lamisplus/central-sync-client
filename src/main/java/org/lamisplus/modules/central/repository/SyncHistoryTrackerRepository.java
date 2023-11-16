package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SyncHistoryTrackerRepository extends JpaRepository<SyncHistoryTracker, Long> {
    List<SyncHistoryTracker> findAllBySyncHistoryId(Long syncHistoryId);

    List<SyncHistoryTracker> findAllBySyncHistoryIdAndArchived(Long syncHistoryId, int archived);
}
