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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "module_name", nullable = false)
    private String moduleName;

    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "config_id", nullable = false)
    private UUID configId;
}
