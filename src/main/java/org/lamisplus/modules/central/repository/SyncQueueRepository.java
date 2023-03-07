package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.SyncQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SyncQueueRepository extends JpaRepository<SyncQueue, Long> {

    List<SyncQueue> findAll();

    List<SyncQueue> getAllByProcessed(Integer processed);

    @Query(value = "SELECT * from sync_queue sq where  sq.organisation_unit_id " +
            "in (select distinct ou.organisation_unit_id from sync_queue ou " +
            "where ou.processed = 0) order by sq.id asc", nativeQuery = true)
    List<SyncQueue> getAllSyncQueueByFacilitiesNotProcessed();

    @Query(value = "SELECT * from sync_queue sq " +
            "where sq.organisation_unit_id = ? and sq.processed = 0 " +
            "order by sq.date_created desc", nativeQuery = true)
    List<SyncQueue> getAllSyncQueueByFacilityNotProcessed(Long facilityId);

    Optional<SyncQueue> findTopByIdOrderByIdDesc(Long id);

    void deleteById(Long id);

    /*public int save(SyncQueue syncQueue) {
        //having issues with pk violation... this is a temporary measure
        //jdbcTemplate.update("select setval('sync_queue_id_seq', (select max(id) from sync_queue))");

        if(syncQueue.getId() == null || syncQueue.getId() == 0){
            return jdbcTemplate.update("INSERT INTO sync_queue (date_created, file_name, organisation_unit_id, processed, table_name) " +
                            "VALUES (?, ?, ?, ?, ?)", syncQueue.getDateCreated(), syncQueue.getFileName(),
                    syncQueue.getOrganisationUnitId(), syncQueue.getProcessed(), syncQueue.getTableName());

        }
        return jdbcTemplate.update("UPDATE sync_queue SET date_created=?, file_name=?, organisation_unit_id=?, processed=?, table_name=? " +
                        "WHERE id=?", syncQueue.getDateCreated(), syncQueue.getFileName(), syncQueue.getOrganisationUnitId(), syncQueue.getProcessed(), syncQueue.getTableName(), syncQueue.getId());
    }*/

    @Query(value = "SELECT * FROM sync_queue sq ORDER BY sq.date_created DESC limit 1", nativeQuery = true)
    Optional<SyncQueue> getLastSaved();

    @Query(value = "SELECT MAX(id) from sync_queue", nativeQuery = true)
    Optional<Integer> getMaxId();

    Optional<SyncQueue> findByFileNameAndOrganisationUnitIdAndDateCreated(String fileName, Long facilityId, LocalDateTime DateCreated);

    List<SyncQueue> findAllByTableNameAndOrganisationUnitIdAndProcessed(String tableName, Long facilityId, Integer processed);
}
