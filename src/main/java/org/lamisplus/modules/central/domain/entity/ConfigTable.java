package org.lamisplus.modules.central.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "sync_config_table")
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ConfigTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "update_column", nullable = false)
    private String updateColumn;

    @Column(name = "columns", nullable = false)
    private String columns;

    @Column(name = "config_module_id", nullable = false)
    private UUID configModuleId;
}

