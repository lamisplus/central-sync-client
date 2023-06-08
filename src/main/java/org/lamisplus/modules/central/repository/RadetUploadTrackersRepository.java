package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.dto.RadetUploaders;
import org.lamisplus.modules.central.domain.entity.RadetUploadTrackers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RadetUploadTrackersRepository extends JpaRepository<RadetUploadTrackers, Long>
{
    @Query(value = "SELECT *From public.central_radet_upload_tracker order by id DESC", nativeQuery = true)
    List<RadetUploadTrackers> allTrackers();

    @Query(value = "SELECT *From public.central_radet_upload_tracker where ip_code =?1 order by created_date DESC", nativeQuery = true)
    List<RadetUploadTrackers> ipTrackers(Long ipCode);

    List<RadetUploadTrackers> getRadetUploadTrackersByFacilityId(Long facilityId);

    @Query(value = "Select a.facility_id as id, a.facility_name as facility, a.facility_state as state, a.facility_lga as lga, a.ip_code as ipcode, a.ip_name as ipname from public.aggregate_data a where a.facility_id in (Select organisation_unit_id from public.base_organisation_unit_identifier where code = ?1)", nativeQuery = true)
    RadetUploaders getRadetUploaders(String datimIdList);

    @Query(value = "SELECT code FROM public.base_organisation_unit_identifier  where organisation_unit_id = ?1", nativeQuery = true)
    String getDatimCode(Long orgUnit);


}
