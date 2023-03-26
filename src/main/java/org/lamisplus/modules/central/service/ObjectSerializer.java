package org.lamisplus.modules.central.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.biometric.domain.Biometric;
import org.lamisplus.modules.biometric.repository.BiometricRepository;
import org.lamisplus.modules.central.domain.entity.Tables;
import org.lamisplus.modules.central.repository.SyncQueueRepository;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.domain.entity.Visit;
import org.lamisplus.modules.patient.repository.PersonRepository;
import org.lamisplus.modules.central.domain.dto.PatientSyncDto;
import org.lamisplus.modules.central.domain.dto.PatientVisitSyncDto;
import org.lamisplus.modules.central.domain.dto.PharmacySyncDto;
import org.lamisplus.modules.central.domain.dto.TriageVitalSignSyncDto;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
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

    public List<?> serialize(String table, long facilityId, LocalDateTime dateLastSync) {

        if (table.equalsIgnoreCase("patient")) {
            log.info(" Retrieving records from  {} ", table);
            if (dateLastSync == null) {
                return personRepository.findAllByFacilityIdAndArchived(facilityId, 0);
            } else {
                return personRepository.getPatientsDueForServerUpload(dateLastSync, facilityId, 0);
               }
        }

        if (table.equalsIgnoreCase("biometric")) {
            log.info(" Retrieving records from  {} ", table);
            if (dateLastSync == null) {
                return   biometricRepository.findAllByFacilityIdAndArchived(facilityId, 0);
             } else {
                return biometricRepository.getBiometricDueForServerUpload(dateLastSync, facilityId, 0);
             }
        }

    /**
        if (table.name().equals("visit")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return remoteAccessTokenRepository.findAllVisitsByFacilityId(facilityId);
            } else {
                return remoteAccessTokenRepository.findAllVisitsDueForServerUpload(dateLastSync, facilityId);
            }
        }


        if (table.name().equals("triage_vital_sign")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return remoteAccessTokenRepository.findAllVitalSignslByFacilityId(facilityId);
            } else {
                return remoteAccessTokenRepository.findAllVitalSignsDueForServerUpload(dateLastSync, facilityId);
            }
        }

        if (table.name().equals("hiv_enrollment")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                return remoteAccessTokenRepository.findAllHivByFacilityId(facilityId);
            } else {
                return remoteAccessTokenRepository.findAllByHivEnrollmentsDueForServerUpload(dateLastSync, facilityId);
            }
        }

        if (table.name().equals("hiv_art_clinical")) {
            log.info(" Retrieving records from  {} ", table.name());
           if (dateLastSync == null) {
                return remoteAccessTokenRepository.findAllByARTClinicalFacilityId(facilityId);
            } else {
                return remoteAccessTokenRepository.findAllByARTClinicalDueForServerUpload(dateLastSync, facilityId);
            }
        }
        //List<Integer> artPharmacyId=null;

        if (table.name().equals("hiv_art_pharmacy")) {
            log.info(" Retrieving records from  {} ", table.name());
            if (dateLastSync == null) {
                //artPharmacyId = remoteAccessTokenRepository.findAllArtPharmacyByFacilityId2(facilityId);
                return remoteAccessTokenRepository.findAllArtPharmacyByFacilityId(facilityId);
            } else {
                return remoteAccessTokenRepository.findAllByArtPharmacyDueForServerUpload(dateLastSync, facilityId);
            }
        }
    */


        List<String> msg = new LinkedList<>();
        msg.add("No table records was retrieved for server sync");
        return msg;

    }
}