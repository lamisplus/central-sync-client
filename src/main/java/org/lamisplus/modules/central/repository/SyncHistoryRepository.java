package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.dto.SyncTable;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SyncHistoryRepository extends JpaRepository<SyncHistory, Long> {
    List<SyncHistory> findAll();
    @Query(value = "SELECT * from sync_history sh order by sh.date_last_sync desc limit 100", nativeQuery = true)
    List<SyncHistory> findSyncHistories();
    @Query(value = "SELECT * FROM sync_history WHERE table_name=?1 AND organisation_unit_id=?2 order by id desc limit 1", nativeQuery = true)
    Optional<SyncHistory> findByTableNameAndOrganisationUnitId(String tableName, Long organisationUnitId);
    @Query(value = "SELECT * from sync_history sh where sh.processed = ?1 order by sh.date_last_sync desc limit 5", nativeQuery = true)
    List<SyncHistory> findSyncQueueByProcessed(int processed);
    @Query(value = "SELECT id FROM sync_history WHERE processed=?", nativeQuery = true)
    List<SyncHistory> findSyncQueueIdByProcessed(int processed);
    Optional<SyncHistory> findById(Long id);
    @Query(value = "DELETE FROM Sync_history WHERE id=?1", nativeQuery = true)
    void deleteById(Long id);

    @Query(value = "SELECT (SELECT COUNT(uuid) FROM patient_person  " +
            "WHERE facility_id=?1 AND last_modified_date > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='patient' AND organisation_unit_id=?1)) patient, " +
            "(SELECT COUNT(uuid) FROM patient_visit  " +
            "WHERE facility_id=?1 AND last_modified_date > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='patient_visit' AND organisation_unit_id=?1)) patient_visit, " +
            "(SELECT COUNT(uuid) FROM triage_vital_sign  " +
            "WHERE facility_id=?1 AND last_modified_date > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='triage_vital_sign' AND organisation_unit_id=?1)) triage_vital_sign, " +
            "(SELECT COUNT(uuid) FROM hiv_enrollment  " +
            "WHERE facility_id=?1 AND last_modified_date > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='hiv_enrollment' AND organisation_unit_id=?1)) hiv_enrollment, " +
            "(SELECT COUNT(uuid) FROM hiv_art_clinical  " +
            "WHERE facility_id=?1 AND last_modified_date > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='hiv_art_clinical' AND organisation_unit_id=?1)) hiv_art_clinical, " +
            "(SELECT COUNT(uuid) FROM hiv_art_pharmacy  " +
            "WHERE facility_id=?1 AND last_modified_date > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='hiv_art_pharmacy' AND organisation_unit_id=?1)) hiv_art_pharmacy, " +
            "(SELECT COUNT(uuid) FROM laboratory_order  " +
            "WHERE facility_id=?1 AND date_modified > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='laboratory_order' AND organisation_unit_id=?1)) laboratory_order, " +
            "(SELECT COUNT(uuid) FROM laboratory_order  " +
            "WHERE facility_id=?1 AND date_modified > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='laboratory_order' AND organisation_unit_id=?1)) laboratory_test, " +
            "(SELECT COUNT(uuid) FROM laboratory_sample  " +
            "WHERE facility_id=?1 AND date_modified > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='laboratory_sample' AND organisation_unit_id=?1)) laboratory_sample, " +
            "(SELECT COUNT(uuid) FROM laboratory_result  " +
            "WHERE facility_id=?1 AND date_modified > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='laboratory_result' AND organisation_unit_id=?1)) laboratory_result, " +
            "(SELECT COUNT(uuid) FROM hiv_status_tracker  " +
            "WHERE facility_id=?1 AND last_modified_date > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='hiv_status_tracker' AND organisation_unit_id=?1)) hiv_status_tracker, " +
            "(SELECT COUNT(uuid) FROM hiv_eac  " +
            "WHERE facility_id=?1 AND last_modified_date > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='hiv_eac' AND organisation_unit_id=?1)) hiv_eac, " +
            "(SELECT COUNT(uuid) FROM hiv_eac_session  " +
            "WHERE facility_id=?1 AND last_modified_date > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='hiv_eac_session' AND organisation_unit_id=?1)) hiv_eac_session, " +
            "(SELECT COUNT(uuid) FROM hiv_eac_out_come  " +
            "WHERE facility_id=?1 AND last_modified_date > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='hiv_eac_out_come' AND organisation_unit_id=?1)) hiv_eac_out_come, " +
            "(SELECT COUNT(uuid) FROM hiv_observation  " +
            "WHERE facility_id=?1 AND last_modified_date > (SELECT date_last_sync FROM sync_history  " +
            " WHERE table_name='hiv_observation' AND organisation_unit_id=?1)) hiv_observation", nativeQuery = true)
    List<SyncTable> findTableToSync(Long facility);
}
