package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.biometric.domain.Biometric;
import org.lamisplus.modules.biometric.repository.BiometricRepository;
import org.lamisplus.modules.central.domain.entity.Tables;
import org.lamisplus.modules.central.repository.SyncQueueRepository;
import org.lamisplus.modules.hiv.domain.entity.ArtPharmacy;
import org.lamisplus.modules.hiv.repositories.ARTClinicalRepository;
import org.lamisplus.modules.hiv.repositories.ArtPharmacyRepository;
import org.lamisplus.modules.hiv.repositories.HivEnrollmentRepository;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.domain.entity.Visit;
import org.lamisplus.modules.patient.repository.PersonRepository;
import org.lamisplus.modules.central.domain.dto.PatientSyncDto;
import org.lamisplus.modules.central.domain.dto.PatientVisitSyncDto;
import org.lamisplus.modules.central.domain.dto.PharmacySyncDto;
import org.lamisplus.modules.central.domain.dto.TriageVitalSignSyncDto;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
import org.lamisplus.modules.patient.repository.VisitRepository;
import org.lamisplus.modules.triage.repository.VitalSignRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ObjectSerializer {
    private final RemoteAccessTokenRepository remoteAccessTokenRepository;
    private final PersonRepository personRepository;

    private final SyncQueueRepository syncQueueRepository;

    private final BiometricRepository biometricRepository;

    //HIV related repositories
    private final HivEnrollmentRepository hivEnrollmentRepository;
    private final ARTClinicalRepository artClinicalRepository;
    private final ArtPharmacyRepository artPharmacyRepository;
    private final VisitRepository visitRepository;
    private final VitalSignRepository vitalSignRepository;

    public List<?> serialize(Tables table, long facilityId, LocalDateTime dateLastSync) {

        //Level A - Patient
        if (table.name().equalsIgnoreCase("patient")) {
            log.info(" Retrieving records from  {} ", table);
            if (dateLastSync == null) {
                return personRepository.findAllByFacilityId(facilityId);
            } else {
                return personRepository.getAllDueForServerUpload(dateLastSync, facilityId);
               }
        }

        //Level B - Patient Visit
        if (table.name().equalsIgnoreCase("patient_visit")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   visitRepository.findAllByFacilityId(facilityId);
            } else {
                return visitRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level C - HIV Enrollment
        if (table.name().equalsIgnoreCase("hiv_enrollment")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   hivEnrollmentRepository.findAllByFacilityId(facilityId);
            } else {
                return hivEnrollmentRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level D - Triage Vital Sign
        if (table.name().equalsIgnoreCase("triage_vital_sign")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   vitalSignRepository.findAllByFacilityId(facilityId);
            } else {
                return vitalSignRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level E - HIV ART Clinical
        if (table.name().equalsIgnoreCase("hiv_art_clinical")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   artClinicalRepository.findAllByFacilityId(facilityId);
            } else {
                return artClinicalRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level F - HIV Art Pharmacy
        if (table.name().equalsIgnoreCase("hiv_art_pharmacy")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   artPharmacyRepository.findAllByFacilityId(facilityId);
            } else {
                return artPharmacyRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        if (table.name().equalsIgnoreCase("biometric")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   biometricRepository.findAllByFacilityId(facilityId);
            } else {
                return biometricRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }


        List<String> msg = new LinkedList<>();
        msg.add("No table records was retrieved for server sync");
        return msg;

    }
}