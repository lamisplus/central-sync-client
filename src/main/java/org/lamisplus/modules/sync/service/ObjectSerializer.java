package org.lamisplus.modules.sync.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.sync.domain.dto.PatientSyncDto;
import org.lamisplus.modules.sync.domain.dto.PatientVisitSyncDto;
import org.lamisplus.modules.sync.domain.dto.PharmacySyncDto;
import org.lamisplus.modules.sync.domain.dto.TriageVitalSignSyncDto;
import org.lamisplus.modules.sync.domain.entity.*;
import org.lamisplus.modules.sync.repository.RemoteAccessTokenRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ObjectSerializer {
    private final RemoteAccessTokenRepository remoteAccessTokenRepository;

    public List<?> serialize(Tables table, long facilityId, LocalDateTime dateLastSync) {

        if (table.name().equals("patient")) {
            log.info(" Retrieving records from  {} ", table.name());
            List<PatientSyncDto> patientList = new LinkedList<>();
            if (dateLastSync == null) {
                return remoteAccessTokenRepository.getAllPatients(facilityId);
            } else {
                return remoteAccessTokenRepository.getPatientsDueForServerUpload(dateLastSync, facilityId);
            }
        }


        if (table.name().equals("visit")) {
            log.info(" Retrieving records from  {} ", table.name());
            List<PatientVisitSyncDto> visitList = new LinkedList<>();
            if (dateLastSync == null) {
                return remoteAccessTokenRepository.getAllPatientVisits(facilityId);
            } else {
                return remoteAccessTokenRepository.getPatientVisitDueForServerUpload(dateLastSync, facilityId);
            }
        }


        if (table.name().equals("triage_vital_sign")) {
            log.info(" Retrieving records from  {} ", table.name());
            List<TriageVitalSignSyncDto> triageVitalSigns = new LinkedList<>();
            if (dateLastSync == null) {
                return remoteAccessTokenRepository.getAllTriageVitalSign(facilityId);
            } else {
                return remoteAccessTokenRepository.getTriageVitalSignDueForServerUpload(dateLastSync, facilityId);
            }
        }

        if (table.name().equals("hiv_enrollment")) {
            log.info(" Retrieving records from  {} ", table.name());
            List<TriageVitalSignSyncDto> triageVitalSigns = new LinkedList<>();
            if (dateLastSync == null) {
                return remoteAccessTokenRepository.getAllHivEnrollment(facilityId);
            } else {
                return remoteAccessTokenRepository.getHivEnrollmentDueForServerUpload(dateLastSync, facilityId);
            }
        }

        if (table.name().equals("hiv_art_clinical")) {
            log.info(" Retrieving records from  {} ", table.name());
            List<TriageVitalSignSyncDto> triageVitalSigns = new LinkedList<>();
            if (dateLastSync == null) {
                return remoteAccessTokenRepository.getAllHivArtClinic(facilityId);
            } else {
                return remoteAccessTokenRepository.getHivArtClinicDueForServerUpload(dateLastSync, facilityId);
            }
        }

        if (table.name().equals("hiv_art_pharmacy")) {
            log.info(" Retrieving records from  {} ", table.name());
            List<PharmacySyncDto> pharmacies = new LinkedList<>();
            if (dateLastSync == null) {
                return remoteAccessTokenRepository.getAllPharmacy(facilityId);
            } else {
                return remoteAccessTokenRepository.getPharmacyDueForServerUpload(dateLastSync, facilityId);
            }
        }


        List<String> msg = new LinkedList<>();
        msg.add("No table records was retrieved for server sync");
        return msg;

    }
}