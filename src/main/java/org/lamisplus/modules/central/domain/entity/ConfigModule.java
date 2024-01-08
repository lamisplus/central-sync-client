package org.lamisplus.modules.central.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "sync_config_module")
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ConfigModule {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "module_name", nullable = false)
    private String moduleName;

    @Column(name = "min_version", nullable = false)
    private String minVersion;

    @Column(name = "max_version", nullable = false)
    private String maxVersion;

    @Column(name = "config_id", nullable = false)
    private UUID configId;
}
