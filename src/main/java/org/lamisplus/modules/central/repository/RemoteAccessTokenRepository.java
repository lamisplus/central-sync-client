package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.base.domain.entities.User;
import org.lamisplus.modules.central.domain.dto.*;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RemoteAccessTokenRepository extends JpaRepository<RemoteAccessToken, Long> {
    @Query(value = "SELECT * FROM remote_access_token LIMIT 1", nativeQuery = true)
    Optional<RemoteAccessToken> findOneAccess();

    @Query(value = "SELECT org.id, org.name FROM base_organisation_unit org WHERE " +
            "org.id IN (SELECT DISTINCT ps.facility_id FROM patient_person ps)", nativeQuery = true)
    List<FacilityDto> findFacilityWithRecords();
}
