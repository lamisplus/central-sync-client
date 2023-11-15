package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.Config;
import org.lamisplus.modules.central.domain.entity.FacilityAppKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FacilityAppKeyRepository extends JpaRepository<FacilityAppKey, UUID> {
    public List<FacilityAppKey> findFacilityAppKeyByFacilityId(Integer facilityId);
}
