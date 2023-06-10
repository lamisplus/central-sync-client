package org.lamisplus.modules.central.domain.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.Interceptor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sync_history")
public class SyncHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateLastSync;
    private Long organisationUnitId;
    private String tableName;
    private Integer processed;
    private Long syncQueueId;
    private Long remoteAccessTokenId;
    private Integer uploadSize;
    private Integer processedSize;


    public SyncHistory(LocalDateTime dateLastSync, Long organisationUnitId, String tableName, Integer processed,
                       Long syncQueueId, Long remoteAccessTokenId, Integer uploadSize, Integer processedSize) {
        this.dateLastSync = dateLastSync;
        this.organisationUnitId = organisationUnitId;
        this.tableName = tableName;
        this.processed = processed;
        this.syncQueueId = syncQueueId;
        this.remoteAccessTokenId = remoteAccessTokenId;
        this.uploadSize = uploadSize;
        this.processedSize = processedSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SyncHistory that = (SyncHistory) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
