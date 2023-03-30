package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.biometric.domain.Biometric;
import org.lamisplus.modules.biometric.repository.BiometricRepository;
import org.lamisplus.modules.central.domain.entity.SyncQueue;
import org.lamisplus.modules.central.repository.SyncQueueRepository;
import org.lamisplus.modules.hiv.domain.entity.ARTClinical;
import org.lamisplus.modules.hiv.domain.entity.ArtPharmacy;
import org.lamisplus.modules.hiv.domain.entity.HivEnrollment;
import org.lamisplus.modules.hiv.repositories.ARTClinicalRepository;
import org.lamisplus.modules.hiv.repositories.ArtPharmacyRepository;
import org.lamisplus.modules.hiv.repositories.HivEnrollmentRepository;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.domain.entity.Visit;
import org.lamisplus.modules.patient.repository.*;
import org.lamisplus.modules.patient.service.PersonService;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
import org.lamisplus.modules.triage.domain.entity.VitalSign;
import org.lamisplus.modules.triage.repository.VitalSignRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ObjectDeserializer {
    public static final String PATIENT = "patient";
    public static final int PROCESSED = 0;
    public static final String PATIENT_VISIT = "patient_visit";
    public static final String TRIAGE_VITAL_SIGN = "triage_vital_sign";
    public static final String HIV_ART_CLINICAL = "hiv_art_clinical";
    public static final String HIV_ENROLLMENT = "hiv_enrollment";
    public static final String HIV_ART_CLINICAL1 = "hiv_art_clinical";
    private final PersonRepository personRepository;

    private final BiometricRepository biometricRepository;

    private final VisitRepository visitRepository;

    private final VitalSignRepository vitalSignRepository;

    private final ARTClinicalRepository artClinicalRepository;

    private final ArtPharmacyRepository artPharmacyRepository;
    private final HivEnrollmentRepository hivEnrollmentRepository;

    private final SyncQueueRepository syncQueueRepository;

    private final RemoteAccessTokenRepository remoteAccessTokenRepository;


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public List<?> deserialize(byte[] bytes, String table, Long facilityId) throws Exception {
        String data = new String(bytes, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        switch (table){
            case "patient":
                log.info("Saving " + table + " on Server");
                return processAndSavePatientsOnServer(data, objectMapper, facilityId);

            case "biometric":
                log.info("Saving " + table + " on Server");
                return processAndSaveBiometricsOnServer(data, objectMapper, facilityId);

            case "patient_visit":
                log.info("Saving " + table + " on Server");
                return processAndSaveVisitOnServer(data, objectMapper, facilityId);

            case "triage_vital_sign":
                log.info("Saving " + table + " on Server");
                return processAndSaveVitalSignOnServer(data, objectMapper, facilityId);

            case "hiv_enrollment":
                log.info("Saving " + table + " on Server");
                return processAndSaveHivEnrollmentOnServer(data, objectMapper, facilityId);

            case "hiv_art_clinical":
                log.info("Saving " + table + " on Server");
                return processAndSaveArtClinicalOnServer(data, objectMapper, facilityId);

            case "hiv_art_pharmacy":
                log.info("Saving " + table + " on Server");
                return processAndSavePharmacyOnServer(data, objectMapper, facilityId);
        }

        List<String> msg = new LinkedList<>();
        msg.add("Nothing was saved on the server");
        return msg;
    }

    /**
     * Handles patient sync to central server - considered as level A.
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Persons
     */
    private List<Person> processAndSavePatientsOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws JsonProcessingException {
        List<Person> persons = new LinkedList<>();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Person> clientPersonList = objectMapper.readValue(data, new TypeReference<List<Person>>() {
        });

        clientPersonList.forEach(clientPerson -> {
            Person person = new Person();
            BeanUtils.copyProperties(clientPerson, person);
            Optional<Person> foundPerson = personRepository.findByUuidAndFacilityId(clientPerson.getUuid(), facilityId);
            //Set id for new or old person on the server
            if(foundPerson.isPresent()){
                person.setId(foundPerson.get().getId());
            } else {
                person.setId(null);
            }
            persons.add(person);
        });
        List<Person> savedPatients = personRepository.saveAll(persons);
        log.info("number of patients save on server => : {}", savedPatients.size());
        return savedPatients;
    }

    /**
     * Handles patient visits sync to central server - considered as level B .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Patient visit
     */
    private List<Visit> processAndSaveVisitOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Visit> visits = new ArrayList<>();
        //Sync related patient before syncing vitalSign
        if (!syncQueueRepository.findAllByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED).isPresent()) {
            List<Visit> clientVisits = objectMapper.readValue(data, new TypeReference<List<Visit>>() {
            });
            clientVisits.forEach(clientVisit -> {
                Visit visit = new Visit();
                BeanUtils.copyProperties(clientVisit, visit);
                Optional<Visit> foundVisit = visitRepository.findByUuidAndFacilityId(clientVisit.getUuid(), facilityId);
                //Set id for new or old visit on the server
                if(foundVisit.isPresent()){
                    visit.setId(foundVisit.get().getId());
                } else {
                    visit.setId(null);
                }
                visits.add(visit);

            });
            List<Visit> savedVisits;
            savedVisits = visitRepository.saveAll(visits);
            log.info("number of visits save on server => : {}", savedVisits.size());
            return savedVisits;
        }
        //Return empty
        return visits;
    }

    /**
     * Handles patient Hiv Enrollment sync to central server - considered as level C .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Hiv Enrollment
     */
    private List<HivEnrollment> processAndSaveHivEnrollmentOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<HivEnrollment> hivEnrollments = new ArrayList<>();
        //Sync related patient before syncing hiv enrollment
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVisitQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(PATIENT_VISIT, facilityId, PROCESSED);

        if(!optionalPatientQueue.isPresent() && !optionalVisitQueue.isPresent()) {
            List<HivEnrollment> clientEnrollments = objectMapper.readValue(data, new TypeReference<List<HivEnrollment>>() {});

            clientEnrollments.forEach(clientEnrollment -> {
                HivEnrollment hivEnrollment = new HivEnrollment();
                BeanUtils.copyProperties(clientEnrollment, hivEnrollment);
                Optional<HivEnrollment> foundEnrollment = hivEnrollmentRepository.findByUuid(clientEnrollment.getUuid());
                //Set id for new or old enrollment on the server
                if(foundEnrollment.isPresent()){
                    hivEnrollment.setId(foundEnrollment.get().getId());
                } else {
                    hivEnrollment.setId(null);
                }
                hivEnrollments.add(hivEnrollment);

            });

            List<HivEnrollment> savedEnrollment;
            savedEnrollment = hivEnrollmentRepository.saveAll(hivEnrollments);
            log.info("number of Enrollment save on server => : {}", savedEnrollment.size());
            return savedEnrollment;
        }
        //Return empty
        return hivEnrollments;
    }

    /**
     * Handles patient Triage Vital Sign sync to central server - considered as level D .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Vital Sign
     */
    private List<VitalSign> processAndSaveVitalSignOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<VitalSign> vitalSigns = new ArrayList<>();
        //Sync related patient before syncing vitalSign
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVisitQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(PATIENT_VISIT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalEnrollmentQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(HIV_ENROLLMENT, facilityId, PROCESSED);

        if(!optionalPatientQueue.isPresent()
                && !optionalVisitQueue.isPresent()
                && !optionalEnrollmentQueue.isPresent()) {
            List<VitalSign> clientVitalSigns = objectMapper.readValue(data, new TypeReference<List<VitalSign>>() {});

            clientVitalSigns.forEach(clientVital -> {
                VitalSign vitalSign = new VitalSign();
                BeanUtils.copyProperties(clientVital, vitalSign);
                Optional<VitalSign> foundVital = vitalSignRepository.findByUuidAndFacilityId(clientVital.getUuid(), facilityId);
                //Set id for new or old vitals on the server
                if(foundVital.isPresent()){
                    vitalSign.setId(foundVital.get().getId());
                } else {
                    vitalSign.setId(null);
                }
                vitalSigns.add(vitalSign);

            });

            List<VitalSign> savedVitalSigns;
            savedVitalSigns = vitalSignRepository.saveAll(vitalSigns);
            log.info("number of vitalSign save on server => : {}", savedVitalSigns.size());
            return savedVitalSigns;
        }
        //Return empty
        return vitalSigns;
    }


    /**
     * Handles patient ART Clinic sync to central server - considered as level E .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of ART Clinic
     */
    private List<ARTClinical> processAndSaveArtClinicalOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<ARTClinical> artClinicals = new ArrayList<>();
        //Sync related patient before syncing vitalSign
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVisitQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(PATIENT_VISIT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVitals = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(TRIAGE_VITAL_SIGN, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalEnrollmentQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(HIV_ENROLLMENT, facilityId, PROCESSED);

        if(!optionalPatientQueue.isPresent()
                && !optionalVisitQueue.isPresent()
                && !optionalVitals.isPresent()
                && !optionalEnrollmentQueue.isPresent()) {
            List<ARTClinical> clientArtClinics = objectMapper.readValue(data, new TypeReference<List<ARTClinical>>() {});

            clientArtClinics.forEach(ClientClinical -> {
                ARTClinical artClinical = new ARTClinical();
                BeanUtils.copyProperties(ClientClinical, artClinical);
                Optional<ARTClinical> foundClinical = artClinicalRepository.findByUuid(ClientClinical.getUuid());
                //Set id for new or old clinics on the server
                if(foundClinical.isPresent()){
                    artClinical.setId(foundClinical.get().getId());
                } else {
                    artClinical.setId(null);
                }
                artClinicals.add(artClinical);

            });

            List<ARTClinical> savedArtClinical;
            savedArtClinical = artClinicalRepository.saveAll(artClinicals);
            log.info("number of Art Clinical save on server => : {}", savedArtClinical.size());
            return savedArtClinical;
        }
        //Return empty
        return artClinicals;
    }

    /**
     * Handles patient Pharmacy sync to central server - considered as level F .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of patient Pharmacy
     */
    private List<ArtPharmacy> processAndSavePharmacyOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<ArtPharmacy> artPharmacies = new ArrayList<>();
        //Sync related patient before syncing pharmacy
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVisitQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(PATIENT_VISIT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVitals = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(TRIAGE_VITAL_SIGN, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalEnrollmentQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(HIV_ENROLLMENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalClinicQueue = syncQueueRepository
                .findAllByTableNameAndFacilityIdAndProcessed(HIV_ART_CLINICAL1, facilityId, PROCESSED);

        if(!optionalPatientQueue.isPresent()
                && !optionalVisitQueue.isPresent()
                && !optionalEnrollmentQueue.isPresent()
                && !optionalVitals.isPresent()
                && !optionalClinicQueue.isPresent()) {
            List<ArtPharmacy> clientPharmacies = objectMapper.readValue(data, new TypeReference<List<ArtPharmacy>>() {});

            clientPharmacies.forEach(clientPharmacy -> {
                ArtPharmacy artPharmacy = new ArtPharmacy();
                BeanUtils.copyProperties(clientPharmacy, artPharmacy);
                Optional<ARTClinical> foundPharmacy = artClinicalRepository.findByUuid(clientPharmacy.getUuid());
                //Set id for new or old Pharmacy on the server
                if(foundPharmacy.isPresent()){
                    artPharmacy.setId(foundPharmacy.get().getId());
                } else {
                    artPharmacy.setId(null);
                }
                artPharmacies.add(artPharmacy);

            });

            List<ArtPharmacy> savedArtPharmacy;
            savedArtPharmacy = artPharmacyRepository.saveAll(artPharmacies);
            log.info("number of Art Pharmacy save on server => : {}", savedArtPharmacy.size());
            return savedArtPharmacy;
        }
        //Return empty
        return artPharmacies;
    }

    /**
     * Handles biometrics sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Biometrics
     */
    private List<Biometric> processAndSaveBiometricsOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Biometric> savedBiometrics = new ArrayList<>();
        //Sync related patient before syncing biometrics
        if(!syncQueueRepository.findAllByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED).isPresent()) {
            List<Biometric> clientBiometrics = objectMapper.readValue(data, new TypeReference<List<Biometric>>() {
            });
            savedBiometrics = biometricRepository.saveAll(clientBiometrics);
            log.info("number of biometrics save on server => : {}", savedBiometrics.size());
            return savedBiometrics;
        }
        //Return empty
        return savedBiometrics;
    }
}
