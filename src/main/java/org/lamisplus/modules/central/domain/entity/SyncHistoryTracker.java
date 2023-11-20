package org.lamisplus.modules.central.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sync_history_tracker")
public class SyncHistoryTracker {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sync_history_id")
    private Long syncHistoryId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "record_size")
    private Integer recordSize;
    @Column(name = "status")
    private String status;
    @Column(name = "time_created")
    private LocalDateTime timeCreated;
    @Column(name = "archived")
    private Integer archived;
    @Column(name = "facility_id")
    private Long facilityId;

    @Column(name = "sync_history_uuid")
    private UUID syncHistoryUuid;

    @Basic
    @Column(name = "uuid", updatable = false)
    private UUID uuid;

    @PrePersist
    public void setUuid(){
        if(uuid == null) uuid = UUID.randomUUID();
    }
}
