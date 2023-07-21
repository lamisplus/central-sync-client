package org.lamisplus.modules.central.repository;


import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SyncHistoryRepository  extends JpaRepository<SyncHistory, Long> {

    Optional<SyncHistory> findByTableName(String tableName);

    @Query(value = "SELECT name from base_organisation_unit WHERE id = ?1", nativeQuery = true)
    Optional<String> getFacilityNameById(Long facilityId);

    @Query(value = "SELECT * FROM sync_history WHERE date_last_sync=(SELECT MAX(date_last_sync) from sync_history WHERE organisation_unit_id=?1)", nativeQuery = true)
    Optional<SyncHistory> getDateLastSync(Long facilityId);

    @Query(value = "SELECT * FROM sync_history WHERE table_name ILIKE ?1", nativeQuery = true)
    Optional<SyncHistory> getFile(String name);
}
