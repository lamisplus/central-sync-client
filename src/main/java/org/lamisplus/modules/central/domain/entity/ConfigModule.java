package org.lamisplus.modules.central.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.lamisplus.modules.base.domain.entities.ApplicationUserOrganisationUnit;

import javax.persistence.*;
import java.util.List;
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

    @Column(name = "main_version", nullable = false)
    private String mainVersion;

    @Column(name = "config_id", nullable = false)
    private UUID configId;

    @OneToMany(mappedBy = "module", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<ConfigTable> configTables;
}
