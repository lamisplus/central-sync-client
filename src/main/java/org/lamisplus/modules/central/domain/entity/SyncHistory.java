package org.lamisplus.modules.central.domain.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sync_history")
@EqualsAndHashCode
@Builder
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
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
    private Integer fileCount;
    private LocalDateTime syncStartDate;
    private LocalDateTime syncEndDate;
    private String configVersion;
    private String generationType;
    private Integer source;
    @Basic
    @Column(name = "uuid", updatable = false, unique = true)
    private UUID uuid;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "error_log", nullable = false)
    private Object errorLog;


    @PrePersist
    public void setUuid(){
        if(uuid == null) uuid = UUID.randomUUID();
    }
}
