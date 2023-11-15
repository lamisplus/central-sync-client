package org.lamisplus.modules.central.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "sync_facility_app_key")
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class FacilityAppKey {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "facility_id", nullable = false)
    private Integer facilityId;

    @Column(name = "app_key", nullable = false)
    private String appKey;
}
