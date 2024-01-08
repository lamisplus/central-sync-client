package org.lamisplus.modules.central.domain.entity;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.Interceptor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sync_history")
@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
        @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})
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
    private String filePath;
    private String genKey;
    @Basic
    @Column(name = "uuid", updatable = false, unique = true)
    private UUID uuid;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "error_log", nullable = false)
    private Object errorLog;


    public SyncHistory(LocalDateTime dateLastSync, Long organisationUnitId, String tableName, Integer processed,
                       Long syncQueueId, Long remoteAccessTokenId, Integer uploadSize, Integer processedSize, Object errorLog) {
        this.dateLastSync = dateLastSync;
        this.organisationUnitId = organisationUnitId;
        this.tableName = tableName;
        this.processed = processed;
        this.syncQueueId = syncQueueId;
        this.remoteAccessTokenId = remoteAccessTokenId;
        this.uploadSize = uploadSize;
        this.processedSize = processedSize;
        this.errorLog = errorLog;
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

    @PrePersist
    public void setUuid(){
        if(uuid == null) uuid = UUID.randomUUID();
    }
}
