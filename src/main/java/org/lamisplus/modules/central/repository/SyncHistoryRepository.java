package org.lamisplus.modules.central.repository;

import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

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

    /*public int save(SyncHistory syncHistory) {
        if(syncHistory.getId() == null || syncHistory.getId() == 0){
            return jdbcTemplate.update("INSERT INTO sync_history (date_last_sync, organisation_unit_id, table_name, " +
                            "processed, sync_queue_id, remote_access_token_id, upload_size) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    syncHistory.getDateLastSync(), syncHistory.getOrganisationUnitId(),
                    syncHistory.getTableName(), syncHistory.getProcessed(), syncHistory.getSyncQueueId(),
                    syncHistory.getRemoteAccessTokenId(), syncHistory.getUploadSize());

        }
        return jdbcTemplate.update("UPDATE sync_history SET date_last_sync=?, organisation_unit_id=?, table_name=?, " +
                        "processed=?, sync_queue_id=?, remote_access_token_id=?, upload_size=? WHERE id=?",
                syncHistory.getDateLastSync(), syncHistory.getOrganisationUnitId(),
                syncHistory.getTableName(), syncHistory.getProcessed(), syncHistory.getSyncQueueId(),
                syncHistory.getRemoteAccessTokenId(), syncHistory.getUploadSize(), syncHistory.getId());
    }*/

    /*public int saveAll(List<SyncHistory> syncHistoryList) {
        int saveCount = 0;
        for (SyncHistory syncHistory : syncHistoryList) {
            saveCount = saveCount + this.save(syncHistory);
        }
        return saveCount;
    }*/
}
