package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.Laboratory.repository.LabOrderRepository;
import org.lamisplus.modules.Laboratory.repository.ResultRepository;
import org.lamisplus.modules.Laboratory.repository.SampleRepository;
import org.lamisplus.modules.Laboratory.repository.TestRepository;
import org.lamisplus.modules.biometric.domain.Biometric;
import org.lamisplus.modules.biometric.repository.BiometricRepository;
import org.lamisplus.modules.central.domain.entity.Tables;
import org.lamisplus.modules.central.repository.SyncQueueRepository;
import org.lamisplus.modules.hiv.domain.entity.ArtPharmacy;
import org.lamisplus.modules.hiv.domain.entity.HIVStatusTracker;
import org.lamisplus.modules.hiv.repositories.*;
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
    private final BiometricRepository biometricRepository;

    //HIV related repositories
    private final HivEnrollmentRepository hivEnrollmentRepository;
    private final ARTClinicalRepository artClinicalRepository;
    private final ArtPharmacyRepository artPharmacyRepository;
    private final VisitRepository visitRepository;
    private final VitalSignRepository vitalSignRepository;
    private final LabOrderRepository labOrderRepository;
    private final SampleRepository sampleRepository;
    private final TestRepository testRepository;
    private final ResultRepository resultRepository;
    private final HIVStatusTrackerRepository hivStatusTrackerRepository;
    private final HIVEacRepository hivEacRepository;
    private final HIVEacSessionRepository hivEacSessionRepository;
    private final EacOutComeRepository eacOutComeRepository;
    private final ObservationRepository observationRepository;

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

        //Level G - Laboratory Order
        if (table.name().equalsIgnoreCase("laboratory_order")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   labOrderRepository.findAllByFacilityId(facilityId);
            } else {
                return labOrderRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level H - Laboratory Sample
        if (table.name().equalsIgnoreCase("laboratory_sample")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   sampleRepository.findAllByFacilityId(facilityId);
            } else {
                return sampleRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level I - Laboratory test
        if (table.name().equalsIgnoreCase("laboratory_test")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   testRepository.findAllByFacilityId(facilityId);
            } else {
                return testRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level J - Laboratory Result
        if (table.name().equalsIgnoreCase("laboratory_result")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   resultRepository.findAllByFacilityId(facilityId);
            } else {
                return resultRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level K - Biometrics
        if (table.name().equalsIgnoreCase("biometric")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   biometricRepository.findAllByFacilityId(facilityId);
            } else {
                return biometricRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level L - Hiv Status Tracker
        if (table.name().equalsIgnoreCase("hiv_status_tracker")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   hivStatusTrackerRepository.findAllByFacilityId(facilityId);
            } else {
                return hivStatusTrackerRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }


        //Level M - Hiv EAC
        if (table.name().equalsIgnoreCase("hiv_eac")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   hivEacRepository.findAllByFacilityId(facilityId);
            } else {
                return hivEacRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level N - HIV EAC Session
        if (table.name().equalsIgnoreCase("hiv_eac_session")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   hivEacSessionRepository.findAllByFacilityId(facilityId);
            } else {
                return hivEacSessionRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level O - HIV EAC Outcome
        if (table.name().equalsIgnoreCase("hiv_eac_out_come")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   eacOutComeRepository.findAllByFacilityId(facilityId);
            } else {
                return eacOutComeRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }

        //Level P - HIV Art Pharmacy
        if (table.name().equalsIgnoreCase("hiv_observation")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return   observationRepository.findAllByFacilityId(facilityId);
            } else {
                return observationRepository.getAllDueForServerUpload(dateLastSync, facilityId);
            }
        }


        List<String> msg = new LinkedList<>();
        msg.add("No table records was retrieved for server sync");
        return msg;

    }
}