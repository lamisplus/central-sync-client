package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.central.domain.entity.RadetUploadIssueTrackers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RadetUploadIssueTrackersRepository extends JpaRepository<RadetUploadIssueTrackers, Long>
{
    @Query(value = "SELECT *From public.central_radet_upload_issue_tracker order by id DESC", nativeQuery = true)
    List<RadetUploadIssueTrackers> allTrackers();

    @Query(value = "SELECT *From public.central_radet_upload_issue_tracker where ip_code =?1 order by created_date DESC", nativeQuery = true)
    List<RadetUploadIssueTrackers> ipTrackers(Long ipCode);

    List<RadetUploadIssueTrackers> findRadetUploadIssueTrackersByFacilityId(Long facilityId);


}
