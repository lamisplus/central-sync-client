package org.lamisplus.modules.central.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "update_column", nullable = false)
    private String updateColumn;

    @Column(name = "exclude_columns", nullable = false)
    private String excludeColumns;

    @Column(name = "has_facility_id")
    private boolean hasFacilityId;

    @Column(name = "config_module_id", nullable = false)
    private UUID configModuleId;

    @Column(name = "archived")
    public Boolean archived;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "config_module_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ToString.Exclude
    private ConfigModule module;


}

