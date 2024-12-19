package org.lamisplus.modules.central.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sync_table_count")
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class SyncTableCount {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "time_generated", nullable = false)
    private LocalDateTime timeGenerated;

    @Column(name = "total_record", nullable = false)
    private Long totalRecord;

    @Column(name = "facility_id", nullable = false)
    private Long facilityId;

}
