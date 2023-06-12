package org.lamisplus.modules.central.repository;


import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SyncHistoryRepository  extends JpaRepository<SyncHistory, Long> {

    Optional<SyncHistory> findByTableName(String tableName);

    @Query(value = "SELECT name from base_organisation_unit WHERE id = ?1", nativeQuery = true)
    Optional<String> getFacilityNameById(Long facilityId);



}
