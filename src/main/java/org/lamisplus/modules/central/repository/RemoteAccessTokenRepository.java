package org.lamisplus.modules.central.repository;

import org.lamisplus.modules.base.domain.entities.User;
import org.lamisplus.modules.central.domain.dto.*;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RemoteAccessTokenRepository extends JpaRepository<RemoteAccessToken, Long> {
    List<RemoteAccessToken> findAll();
    Optional<RemoteAccessToken> findByUsername(String username);

    Optional<RemoteAccessToken> findById(Long id);

    Optional<RemoteAccessToken> findByUrl(String url);

    @Query(value = "SELECT * FROM remote_access_token WHERE url=?1 AND organisation_unit_id=?2", nativeQuery = true)
    Optional<RemoteAccessToken> findByUrlAndFacilityId(String url, Long organisationUnit);

    void deleteById(Long id);

    /*public int save(RemoteAccessToken remoteAccessToken) {
        if(remoteAccessToken.getId() == null || remoteAccessToken.getId() == 0){
            return jdbcTemplate.update("INSERT INTO remote_access_token (password, token, url, username, application_user_id, organisation_unit_id, pr_key, pub_key) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    remoteAccessToken.getPassword(), remoteAccessToken.getToken(), remoteAccessToken.getUrl(),
                    remoteAccessToken.getUsername(), remoteAccessToken.getApplicationUserId(), remoteAccessToken.getOrganisationUnitId(),
                    remoteAccessToken.getPrKey(), remoteAccessToken.getPubKey());

        }
        return jdbcTemplate.update("UPDATE remote_access_token SET password=?, token=?, url=?, username=?, " +
                        "application_user_id=?, organisation_unit_id=?, pr_key=?, pub_key=? WHERE id=? ",
                remoteAccessToken.getPassword(), remoteAccessToken.getToken(), remoteAccessToken.getUrl(),
                remoteAccessToken.getUsername(), remoteAccessToken.getApplicationUserId(),
                remoteAccessToken.getOrganisationUnitId(), remoteAccessToken.getPrKey(), remoteAccessToken.getPubKey(), remoteAccessToken.getId());
    }*/


    List<RemoteAccessToken> findAllByApplicationUserId(Long applicationUserId);

    @Query(value ="INSERT INTO user (user_name, password, archived, " +
            "current_organisation_unit_id, first_name, last_name) " +
            "VALUES (?, ?, ?, ?, ?, ?)", nativeQuery = true)
    public int save(User user);

    Optional<RemoteAccessToken> findByUsernameAndUrlAndOrganisationUnitId(String username, String url, Long organisationUnitId);
    Optional<RemoteAccessToken> findByUsernameAndOrganisationUnitId(String username, Long organisationUnitId);

    @Query(value ="SELECT * FROM remote_access_token WHERE pr_key IS NULL", nativeQuery = true)
    List<RemoteAccessToken> findWherePrivateKeyIsNull(String publicKey);

    @Query(value ="SELECT * FROM patient_person WHERE facility_id=?1", nativeQuery = true)
    public List<PatientSyncDto> getAllPatients(Long facilityId);

    @Query(value ="SELECT * FROM patient_person WHERE last_modified_date > ?1 AND facility_id=?2", nativeQuery = true)
    public List<PatientSyncDto> getPatientsDueForServerUpload(LocalDateTime dateLastSync, Long facilityId);


    @Query(value ="SELECT * FROM patient_visit WHERE facility_id=?1", nativeQuery = true)
    public List<PatientVisitSyncDto> getAllPatientVisits(Long facilityId);

    @Query(value ="SELECT * FROM patient_visit WHERE last_modified_date > ?1 AND facility_id=?2", nativeQuery = true)
    public List<PatientSyncDto> getPatientVisitDueForServerUpload(LocalDateTime dateLastSync, Long facilityId);


    @Query(value ="SELECT * FROM triage_vital_sign WHERE facility_id=?1", nativeQuery = true)
    public List<TriageVitalSignSyncDto> getAllTriageVitalSign(Long facilityId);
    @Query(value ="SELECT * FROM triage_vital_sign WHERE last_modified_date > ?1 AND facility_id=?2", nativeQuery = true)
    public List<TriageVitalSignSyncDto> getTriageVitalSignDueForServerUpload(LocalDateTime dateLastSync, Long facilityId);

    @Query(value ="SELECT * FROM hiv_enrollment WHERE facility_id=?1", nativeQuery = true)
    public List<HivEnrollmentSyncDto> getAllHivEnrollment(Long facilityId);
    @Query(value ="SELECT * FROM hiv_enrollment WHERE last_modified_date > ?1 AND facility_id=?2", nativeQuery = true)
    public List<TriageVitalSignSyncDto> getHivEnrollmentDueForServerUpload(LocalDateTime dateLastSync, Long facilityId);

    @Query(value ="SELECT * FROM hiv_art_clinical WHERE facility_id=?1", nativeQuery = true)
    public List<HivArtClinicSyncDto> getAllHivArtClinic(Long facilityId);
    @Query(value ="SELECT * FROM hiv_art_clinical WHERE last_modified_date > ?1 AND facility_id=?2", nativeQuery = true)
    public List<TriageVitalSignSyncDto> getHivArtClinicDueForServerUpload(LocalDateTime dateLastSync, Long facilityId);

    @Query(value ="SELECT * FROM hiv_art_pharmacy WHERE facility_id=?1", nativeQuery = true)
    public List<PharmacySyncDto> getAllPharmacy(Long facilityId);
    @Query(value ="SELECT * FROM hiv_art_pharmacy WHERE last_modified_date > ?1 AND facility_id=?2", nativeQuery = true)
    public List<TriageVitalSignSyncDto> getPharmacyDueForServerUpload(LocalDateTime dateLastSync, Long facilityId);

    @Query(value ="SELECT * FROM patient_person WHERE uuid=?1", nativeQuery = true)
    public Optional<PatientSyncDto> getByPersonUuid(String uuid);

    @Query(value ="SELECT * FROM patient_visit WHERE uuid=?1", nativeQuery = true)
    public Optional<PatientVisitSyncDto> getByPersonVisitUuid(String uuid);

    @Query(value ="SELECT * FROM hiv_enrollment WHERE uuid=?1", nativeQuery = true)
    public Optional<HivEnrollmentSyncDto> getByHivEnrollmentUuid(String uuid);

    @Query(value = "SELECT org.id, org.name FROM base_organisation_unit org WHERE " +
            "org.id IN (SELECT DISTINCT ps.facility_id FROM patient_person ps)", nativeQuery = true)
    List<FacilityDto> findFacilityWithRecords();

}
