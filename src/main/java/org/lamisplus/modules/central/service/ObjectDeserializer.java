package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.IllegalTypeException;
import org.lamisplus.modules.base.domain.entities.OrganisationUnit;
import org.lamisplus.modules.laboratory.domain.entity.LabOrder;
import org.lamisplus.modules.laboratory.domain.entity.Result;
import org.lamisplus.modules.laboratory.domain.entity.Sample;
import org.lamisplus.modules.laboratory.domain.entity.Test;
import org.lamisplus.modules.laboratory.repository.LabOrderRepository;
import org.lamisplus.modules.laboratory.repository.ResultRepository;
import org.lamisplus.modules.laboratory.repository.SampleRepository;
import org.lamisplus.modules.laboratory.repository.TestRepository;
import org.lamisplus.modules.biometric.domain.Biometric;
import org.lamisplus.modules.biometric.repository.BiometricRepository;
import org.lamisplus.modules.central.domain.entity.SyncQueue;
import org.lamisplus.modules.central.repository.SyncQueueRepository;
import org.lamisplus.modules.hiv.domain.entity.*;
import org.lamisplus.modules.hiv.repositories.*;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.domain.entity.Visit;
import org.lamisplus.modules.patient.repository.*;
import org.lamisplus.modules.prep.domain.entity.PrepClinic;
import org.lamisplus.modules.prep.domain.entity.PrepEligibility;
import org.lamisplus.modules.prep.domain.entity.PrepEnrollment;
import org.lamisplus.modules.prep.domain.entity.PrepInterruption;
import org.lamisplus.modules.prep.repository.PrepClinicRepository;
import org.lamisplus.modules.prep.repository.PrepEligibilityRepository;
import org.lamisplus.modules.prep.repository.PrepEnrollmentRepository;
import org.lamisplus.modules.prep.repository.PrepInterruptionRepository;
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
import java.util.stream.Collectors;

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
    public static final String LABORATORY_ORDER = "laboratory_order";
    public static final String LABORATORY_SAMPLE = "laboratory_sample";
    public static final String LABORATORY_TEST = "laboratory_test";
    public static final String HIV_EAC = "hiv_eac";
    public static final String PREP_ELIGIBILITY = "prep_eligibility";
    public static final String PREP_ENROLLMENT = "prep_enrollment";
    public static final String PREP_CLINIC = "prep_clinic";
    public static final String BIOMETRIC = "biometric";
    public static final String HIV_ART_PHARMACY = "hiv_art_pharmacy";
    public static final String LABORATORY_RESULT = "laboratory_result";
    public static final String HIV_STATUS_TRACKER = "hiv_status_tracker";
    public static final String HIV_EAC_SESSION = "hiv_eac_session";
    public static final String HIV_EAC_OUT_COME = "hiv_eac_out_come";
    public static final String HIV_OBSERVATION = "hiv_observation";
    public static final String PREP_INTERRUPTION = "prep_interruption";
    private final PersonRepository personRepository;
    private final BiometricRepository biometricRepository;
    private final VisitRepository visitRepository;
    private final VitalSignRepository vitalSignRepository;
    private final ARTClinicalRepository artClinicalRepository;
    private final ArtPharmacyRepository artPharmacyRepository;
    private final HivEnrollmentRepository hivEnrollmentRepository;
    private final SyncQueueRepository syncQueueRepository;
    private final LabOrderRepository labOrderRepository;
    private final ResultRepository resultRepository;
    private final SampleRepository sampleRepository;
    private final TestRepository testRepository;
    private final HIVStatusTrackerRepository hivStatusTrackerRepository;
    private final HIVEacRepository hivEacRepository;
    private final HIVEacSessionRepository hivEacSessionRepository;
    private final EacOutComeRepository eacOutComeRepository;
    private final ObservationRepository observationRepository;
    private final PrepEligibilityRepository prepEligibilityRepository;
    private final PrepEnrollmentRepository prepEnrollmentRepository;
    private final PrepClinicRepository prepClinicRepository;
    private final PrepInterruptionRepository interruptionRepository;
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Handles deserialization of data received on central server.
     * @param bytes
     * @param table
     * @param facilityId
     * @return a List of any table
     */
    public List<?> deserialize(byte[] bytes, String table, Long facilityId) throws Exception {
        String data = new String(bytes, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        switch (table){
            case PATIENT:
                log.info("Saving " + table + " on Server");
                return processAndSavePatientsOnServer(data, objectMapper, facilityId);

            case BIOMETRIC:
                log.info("Saving " + table + " on Server");
                return processAndSaveBiometricsOnServer(data, objectMapper, facilityId);

            case PATIENT_VISIT:
                log.info("Saving " + table + " on Server");
                return processAndSaveVisitOnServer(data, objectMapper, facilityId);

            case TRIAGE_VITAL_SIGN:
                log.info("Saving " + table + " on Server");
                return processAndSaveVitalSignOnServer(data, objectMapper, facilityId);

            case HIV_ENROLLMENT:
                log.info("Saving " + table + " on Server");
                return processAndSaveHivEnrollmentOnServer(data, objectMapper, facilityId);

            case HIV_ART_CLINICAL:
                log.info("Saving " + table + " on Server");
                return processAndSaveArtClinicalOnServer(data, objectMapper, facilityId);

            case HIV_ART_PHARMACY:
                log.info("Saving " + table + " on Server");
                return processAndSavePharmacyOnServer(data, objectMapper, facilityId);

            case LABORATORY_ORDER:
                log.info("Saving " + table + " on Server");
                return processAndSaveLabOrderOnServer(data, objectMapper, facilityId);

            case LABORATORY_SAMPLE:
                log.info("Saving " + table + " on Server");
                return processAndSaveLabSampleOnServer(data, objectMapper, facilityId);

            case LABORATORY_TEST:
                log.info("Saving " + table + " on Server");
                return processAndSaveLabTestOnServer(data, objectMapper, facilityId);

            case LABORATORY_RESULT:
                log.info("Saving " + table + " on Server");
                return processAndSaveLabResultOnServer(data, objectMapper, facilityId);

            case HIV_STATUS_TRACKER:
                log.info("Saving " + table + " on Server");
                return processAndSaveStatusTrackerOnServer(data, objectMapper, facilityId);

            case HIV_EAC:
                log.info("Saving " + table + " on Server");
                return processAndSaveEacOnServer(data, objectMapper, facilityId);

            case HIV_EAC_SESSION:
                log.info("Saving " + table + " on Server");
                return processAndSaveEacSessionOnServer(data, objectMapper, facilityId);

            /*case HIV_EAC_OUT_COME:
                log.info("Saving " + table + " on Server");
                return processAndSaveEacOutComeOnServer(data, objectMapper, facilityId);*/

            case HIV_OBSERVATION:
                log.info("Saving " + table + " on Server");
                return processAndSaveHivObservationOnServer(data, objectMapper, facilityId);

            case PREP_ELIGIBILITY:
                log.info("Saving " + table + " on Server");
                return processAndSavePrepEligibilityOnServer(data, objectMapper, facilityId);

            case PREP_ENROLLMENT:
                log.info("Saving " + table + " on Server");
                return processAndSavePrepEnrollmentOnServer(data, objectMapper, facilityId);

            case PREP_CLINIC:
                log.info("Saving " + table + " on Server");
                return processAndSavePrepClinicOnServer(data, objectMapper, facilityId);

            case PREP_INTERRUPTION:
                log.info("Saving " + table + " on Server");
                return processAndSavePrepInterruptionOnServer(data, objectMapper, facilityId);
        }

        List<String> msg = new LinkedList<>();
        msg.add("Nothing was saved on the server");
        return msg;
    }

    /**
     * Check Facility id to prevent incorrect processing
     * @param facilityId1
     * @param facilityId2
     * @return void
     */
    private void checkFacilityId(Long facilityId1, Long facilityId2){
        /*if(facilityId1 != facilityId2)throw new IllegalTypeException(OrganisationUnit.class,
                "Facility id mis-match", "wrong facility id import, possibly during export & import");*/
    }

    /**
     * Handles patient sync to central server - considered as level A.
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Persons
     */
    private List<Person> processAndSavePatientsOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        List<Person> persons = new LinkedList<>();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Person> clientPersonList = objectMapper.readValue(data, new TypeReference<List<Person>>() {
        });

        clientPersonList.forEach(clientPerson -> {
            checkFacilityId(clientPerson.getFacilityId(), facilityId);
            Person person = new Person();
            BeanUtils.copyProperties(clientPerson, person);
            Optional<Person> foundPerson = personRepository.findByUuidAndFacilityId(clientPerson.getUuid(), facilityId);
            Optional<Person> foundReConstructedPersonUuid = personRepository.findByUuidAndFacilityId(clientPerson.getUuid()+'-'+facilityId, facilityId);

            Optional<Person> foundPersonUuid = personRepository.findByUuid(clientPerson.getUuid());

            //Set id for new or old person on the server
            if(foundPerson.isPresent()){
                person.setId(foundPerson.get().getId());
            }
            if(foundReConstructedPersonUuid.isPresent()){
                person.setId(foundPerson.get().getId());
                person.setUuid(clientPerson.getUuid()+'-'+clientPerson.getFacilityId());
            }
            //Same uuid different facility - we concatenate the uuid & facility_id
            if(foundPersonUuid.isPresent()){
                person.setUuid(clientPerson.getUuid()+'-'+clientPerson.getFacilityId());
            }
            else {
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
    private List<Visit> processAndSaveVisitOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Visit> visits = new ArrayList<>();
        //Sync related patient before syncing visit
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);
        if (optionalPatientQueue.isPresent()) {
            //Return empty
            return visits;
        }
        List<Visit> clientVisits = objectMapper.readValue(data, new TypeReference<List<Visit>>() {
        });
        clientVisits.forEach(clientVisit -> {
            checkFacilityId(clientVisit.getFacilityId(), facilityId);
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

    /**
     * Handles patient Hiv Enrollment sync to central server - considered as level C .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Hiv Enrollment
     */
    private List<HivEnrollment> processAndSaveHivEnrollmentOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<HivEnrollment> hivEnrollments = new ArrayList<>();
        //Sync related patient before syncing hiv enrollment
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVisitQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT_VISIT, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent() || optionalVisitQueue.isPresent()) {
            //Return empty
            return hivEnrollments;
        }
        List<HivEnrollment> clientEnrollments = objectMapper.readValue(data, new TypeReference<List<HivEnrollment>>() {});
        clientEnrollments.forEach(clientEnrollment -> {
            checkFacilityId(clientEnrollment.getFacilityId(), facilityId);
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

    /**
     * Handles patient Triage Vital Sign sync to central server - considered as level D .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Vital Sign
     */
    private List<VitalSign> processAndSaveVitalSignOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<VitalSign> vitalSigns = new ArrayList<>();
        //Sync related patient before syncing vitalSign
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVisitQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT_VISIT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalEnrollmentQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(HIV_ENROLLMENT, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent() || optionalVisitQueue.isPresent()
                || optionalEnrollmentQueue.isPresent()) {
            //Return empty
            return vitalSigns;
        }
        List<VitalSign> clientVitalSigns = objectMapper.readValue(data, new TypeReference<List<VitalSign>>() {});

        clientVitalSigns.forEach(clientVital -> {
            checkFacilityId(clientVital.getFacilityId(), facilityId);
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


    /**
     * Handles patient ART Clinic sync to central server - considered as level E .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of ART Clinic
     */
    private List<ARTClinical> processAndSaveArtClinicalOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<ARTClinical> artClinicals = new ArrayList<>();
        //Sync related patient before syncing vitalSign
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVisitQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT_VISIT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVitals = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(TRIAGE_VITAL_SIGN, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalEnrollmentQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(HIV_ENROLLMENT, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent() || optionalVisitQueue.isPresent()
                || optionalVitals.isPresent()  || optionalEnrollmentQueue.isPresent()) {
            //Return empty
            return artClinicals;
        }
        List<ARTClinical> clientArtClinics = objectMapper.readValue(data, new TypeReference<List<ARTClinical>>() {});

        clientArtClinics.forEach(ClientClinical -> {
            checkFacilityId(ClientClinical.getFacilityId(), facilityId);
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

    /**
     * Handles patient Pharmacy sync to central server - considered as level F .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of patient Pharmacy
     */
    private List<ArtPharmacy> processAndSavePharmacyOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<ArtPharmacy> artPharmacies = new ArrayList<>();
        //Sync related patient before syncing pharmacy
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVisitQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT_VISIT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVitals = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(TRIAGE_VITAL_SIGN, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalEnrollmentQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(HIV_ENROLLMENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalClinicQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(HIV_ART_CLINICAL, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent()
                || optionalVisitQueue.isPresent()
                || optionalEnrollmentQueue.isPresent()
                || optionalVitals.isPresent()
                || optionalClinicQueue.isPresent()) {
            //Return empty
            return artPharmacies;
        }


        List<ArtPharmacy> clientPharmacies = objectMapper.readValue(data, new TypeReference<List<ArtPharmacy>>() {});

        clientPharmacies.forEach(clientPharmacy -> {
            checkFacilityId(clientPharmacy.getFacilityId(), facilityId);
            ArtPharmacy artPharmacy = new ArtPharmacy();
            BeanUtils.copyProperties(clientPharmacy, artPharmacy);
            Optional<ArtPharmacy> foundPharmacy = artPharmacyRepository.findByUuidAndFacilityId(clientPharmacy.getUuid(), clientPharmacy.getFacilityId());
            Optional<ArtPharmacy> foundPharmacyUuid = artPharmacyRepository.findByUuid(clientPharmacy.getUuid());
            Optional<ArtPharmacy> foundReconstructedPharmacy = artPharmacyRepository
                    .findByUuidAndFacilityId(clientPharmacy.getUuid()+"-"+clientPharmacy.getFacilityId(), clientPharmacy.getFacilityId());
            //Set id for new or old Pharmacy on the server
            if(foundPharmacy.isPresent()){
                artPharmacy.setId(foundPharmacy.get().getId());
            }
            if(foundReconstructedPharmacy.isPresent()){
                artPharmacy.setId(foundPharmacy.get().getId());
                artPharmacy.setUuid(foundReconstructedPharmacy.get().getUuid());
            }
            if(foundPharmacyUuid.isPresent()){
                artPharmacy.setUuid(clientPharmacy.getUuid()+"-"+clientPharmacy.getFacilityId());
                artPharmacy.setId(null);
            }
            else {
                artPharmacy.setId(null);
            }
            artPharmacies.add(artPharmacy);

        });

        List<ArtPharmacy> savedArtPharmacy;
        savedArtPharmacy = artPharmacyRepository.saveAll(artPharmacies);
        log.info("number of Art Pharmacy save on server => : {}", savedArtPharmacy.size());
        return savedArtPharmacy;
    }

    /**
     * Handles patient Lab Order sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of patient Lab Order
     */
    private List<LabOrder> processAndSaveLabOrderOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<LabOrder> labOrders = new ArrayList<>();
        //Sync related patient before syncing Lab Order
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent()) {
            //Return empty
            return labOrders;
        }
        List<LabOrder> clientLabOrders = objectMapper.readValue(data, new TypeReference<List<LabOrder>>() {});

        clientLabOrders.forEach(clientLabOrder -> {
            checkFacilityId(clientLabOrder.getFacilityId(), facilityId);
            LabOrder labOrder = new LabOrder();
            BeanUtils.copyProperties(clientLabOrder, labOrder);
            Optional<LabOrder> foundLabOrder = labOrderRepository.findByUuid(clientLabOrder.getUuid());
            List<Test> tests = new ArrayList<>();
            //Set id for new or old Lab Order on the server
            if(foundLabOrder.isPresent()){
                labOrder.setId(foundLabOrder.get().getId());
                clientLabOrder.getTests().forEach(test -> {
                    Optional<Test> foundTest = testRepository.findByUuid(test.getUuid());

                    //Set id for new or old Lab test on the server
                    if(foundTest.isPresent()){
                        test.setId(foundTest.get().getId());
                    } else {
                        test.setId(null);
                    }
                    test.setLabOrderId(foundLabOrder.get().getId());
                    tests.add(test);
                });
                labOrder.setTests(tests);

            } else {
                labOrder.setId(null);
                labOrder.setTests(labOrder
                        .getTests()
                        .stream()
                        .map(test -> {test.setId(null);return test;})
                        .collect(Collectors.toList()));
            }
            labOrders.add(labOrder);

        });

        List<LabOrder> savedLabOrder = labOrderRepository.saveAll(labOrders);
        log.info("number of Lab Order save on server => : {}", savedLabOrder.size());
        return savedLabOrder;
    }

    /**
     * Handles patient Lab Sample sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of patient Lab Sample
     */
    private List<Sample> processAndSaveLabSampleOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Sample> samples = new ArrayList<>();
        //Sync related patient before syncing pharmacy
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalLabOrderQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(LABORATORY_ORDER, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent() || optionalLabOrderQueue.isPresent()) {
            //Return empty
            return samples;
        }
        List<Sample> clientLabSamples = objectMapper.readValue(data, new TypeReference<List<Sample>>() {});

        clientLabSamples.forEach(clientLabSample -> {
            checkFacilityId(clientLabSample.getFacilityId(), facilityId);
            Sample sample = new Sample();
            BeanUtils.copyProperties(clientLabSample, sample);
            Optional<Sample> foundLabSample = sampleRepository.findByUuid(clientLabSample.getUuid());
            //Set id for new or old Lab Sample on the server
            if(foundLabSample.isPresent()){
                sample.setId(foundLabSample.get().getId());
            } else {
                sample.setId(null);
            }
            samples.add(sample);

        });

        List<Sample> savedLabSample = sampleRepository.saveAll(samples);
        log.info("number of Lab Sample save on server => : {}", savedLabSample.size());
        return savedLabSample;
    }

    /**
     * Handles patient Lab Sample sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of patient Lab Test
     */
    private List<Test> processAndSaveLabTestOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Test> tests = new ArrayList<>();
        //Sync related patient before syncing Lab test
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalLabOrderQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(LABORATORY_ORDER, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalLabSampleQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(LABORATORY_SAMPLE, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent() || optionalLabOrderQueue.isPresent() || optionalLabSampleQueue.isPresent()) {
            //Return empty
            return tests;
        }
        List<Test> clientLabTests = objectMapper.readValue(data, new TypeReference<List<Test>>() {});

        clientLabTests.forEach(clientLabTest -> {
            checkFacilityId(clientLabTest.getFacilityId(), facilityId);
            Test test = new Test();
            BeanUtils.copyProperties(clientLabTest, test);
            Optional<Test> foundLabTest = testRepository.findByUuid(clientLabTest.getUuid());
            //Set id for new or old Lab Sample on the server
            if(foundLabTest.isPresent()){
                test.setId(foundLabTest.get().getId());
            } else {
                test.setId(null);
            }
            tests.add(test);

        });

        List<Test> savedLabTests = testRepository.saveAll(tests);
        log.info("number of Lab Test save on server => : {}", savedLabTests.size());
        return savedLabTests;
    }

    /**
     * Handles patient Lab Result sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of patient Lab Result
     */
    private List<Result> processAndSaveLabResultOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Result> results = new ArrayList<>();
        //Sync related patient before syncing Lab test
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalLabOrderQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(LABORATORY_ORDER, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalLabSampleQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(LABORATORY_SAMPLE, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalLabResultQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(LABORATORY_TEST, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent()
                || optionalLabOrderQueue.isPresent()
                || optionalLabSampleQueue.isPresent()
                || optionalLabResultQueue.isPresent()) {
            //Return empty
            return results;
        }

        List<Result> clientLabResults = objectMapper.readValue(data, new TypeReference<List<Result>>() {});

        clientLabResults.forEach(clientLabResult -> {
            checkFacilityId(clientLabResult.getFacilityId(), facilityId);
            Result result = new Result();
            BeanUtils.copyProperties(clientLabResult, result);
            Optional<Result> foundResult = resultRepository.findByUuid(clientLabResult.getUuid());
            //Set id for new or old Lab Result on the server
            if(foundResult.isPresent()){
                result.setId(foundResult.get().getId());
            } else {
                result.setId(null);
            }
            results.add(result);

        });

        List<Result> savedLabResults = resultRepository.saveAll(results);
        log.info("number of Lab Results save on server => : {}", savedLabResults.size());
        return savedLabResults;
    }


    /**
     * Handles biometrics sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Biometrics
     */
    private List<Biometric> processAndSaveBiometricsOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Biometric> savedBiometrics = new ArrayList<>();
        //Sync related patient before syncing biometrics
        if(!syncQueueRepository.findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED).isPresent()) {
            List<Biometric> clientBiometrics = objectMapper.readValue(data, new TypeReference<List<Biometric>>() {
            });
            savedBiometrics = biometricRepository.saveAll(clientBiometrics);
            log.info("number of biometrics save on server => : {}", savedBiometrics.size());
            return savedBiometrics;
        }
        //Return empty
        return savedBiometrics;
    }

    /**
     * Handles HIVStatusTracker sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of HIVStatusTracker
     */
    private List<HIVStatusTracker> processAndSaveStatusTrackerOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<HIVStatusTracker> statusTrackers = new ArrayList<>();
        //Sync related patient before syncing HIV Status Tracker
        if(!syncQueueRepository.findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED).isPresent()) {
            List<HIVStatusTracker> clientStatusTrackers = objectMapper.readValue(data, new TypeReference<List<HIVStatusTracker>>() {
            });

            clientStatusTrackers.forEach(clientStatus -> {
                checkFacilityId(clientStatus.getFacilityId(), facilityId);
                HIVStatusTracker statusTracker = new HIVStatusTracker();
                BeanUtils.copyProperties(clientStatus, statusTracker);
                Optional<HIVStatusTracker> foundStatus = hivStatusTrackerRepository.findByUuid(clientStatus.getUuid());
                //Set id for new or old Lab Result on the server
                if(foundStatus.isPresent()){
                    statusTracker.setId(foundStatus.get().getId());
                } else {
                    statusTracker.setId(null);
                }
                statusTrackers.add(statusTracker);

            });

            List<HIVStatusTracker> savedStatus = hivStatusTrackerRepository.saveAll(statusTrackers);
            log.info("number of Status Tracker save on server => : {}", savedStatus.size());
            return savedStatus;
        }
        //Return empty
        return statusTrackers;
    }

    /**
     * Handles HIVEac sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of HIVEac
     */
    private List<HIVEac> processAndSaveEacOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<HIVEac> hivEacs = new ArrayList<>();
        //Sync related patient before syncing HIV Eac
        if(!syncQueueRepository.findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED).isPresent()) {
            List<HIVEac> eacs = objectMapper.readValue(data, new TypeReference<List<HIVEac>>() {});

            eacs.forEach(clientEac -> {
                checkFacilityId(clientEac.getFacilityId(), facilityId);
                HIVEac eac = new HIVEac();
                BeanUtils.copyProperties(clientEac, eac);
                Optional<HIVEac> foundEac = hivEacRepository.findByUuid(clientEac.getUuid());
                //Set id for new or old EAC on the server
                if(foundEac.isPresent()){
                    eac.setId(foundEac.get().getId());
                } else {
                    eac.setId(null);
                }
                hivEacs.add(eac);

            });

            List<HIVEac> savedEacs = hivEacRepository.saveAll(hivEacs);
            log.info("number of Hiv EAC save on server => : {}", savedEacs.size());
            return savedEacs;
        }
        //Return empty
        return hivEacs;
    }

    /**
     * Handles HIV Eac Session sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of HIV Eac Session
     */
    private List<HIVEacSession> processAndSaveEacSessionOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<HIVEacSession> hivEacSessions = new ArrayList<>();
        //Sync related patient before syncing HIV Eac
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalHivEacQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(HIV_EAC, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent() || optionalHivEacQueue.isPresent()) {
            //Return empty
            return hivEacSessions;
        }
        List<HIVEacSession> clientEacSessions = objectMapper.readValue(data, new TypeReference<List<HIVEacSession>>() {});

        clientEacSessions.forEach(clientEacSession -> {
            checkFacilityId(clientEacSession.getFacilityId(), facilityId);
            HIVEacSession eacSession = new HIVEacSession();
            BeanUtils.copyProperties(clientEacSession, eacSession);
            Optional<HIVEacSession> foundSession = hivEacSessionRepository.findByUuid(clientEacSession.getUuid());
            //Set id for new or old EAC on the server
            if(foundSession.isPresent()){
                eacSession.setId(foundSession.get().getId());
            } else {
                eacSession.setId(null);
            }
            hivEacSessions.add(eacSession);
        });

        List<HIVEacSession> savedEacSession = hivEacSessionRepository.saveAll(hivEacSessions);
        log.info("number of Eac Session save on server => : {}", savedEacSession.size());
        return savedEacSession;
    }

    /**
     * Handles Eac Outcome sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Eac Outcome
     */
    private List<EacOutCome> processAndSaveEacOutComeOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<EacOutCome> eacOutComes = new ArrayList<>();
        //Sync related patient before syncing HIV Eac
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalHivEacQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(HIV_EAC, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVisitQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT_VISIT, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent()
                || optionalHivEacQueue.isPresent()
                || optionalVisitQueue.isPresent()) {
            //Return empty
            return eacOutComes;

        }
        List<EacOutCome> clientEacOutcomes = objectMapper.readValue(data, new TypeReference<List<EacOutCome>>() {});

        clientEacOutcomes.forEach(clientEacOutcome -> {
            checkFacilityId(clientEacOutcome.getFacilityId(), facilityId);
            EacOutCome eacOutCome = new EacOutCome();
            BeanUtils.copyProperties(clientEacOutcome, eacOutCome);
            Optional<EacOutCome> foundOutcomes = eacOutComeRepository.findByUuid(clientEacOutcome.getUuid());
            //Set id for new or old EAC Outcome on the server
            if(foundOutcomes.isPresent()){
                eacOutCome.setId(foundOutcomes.get().getId());
            } else {
                eacOutCome.setId(null);
            }
            eacOutComes.add(eacOutCome);

        });

        List<EacOutCome> savedEacOutcome = eacOutComeRepository.saveAll(eacOutComes);
        log.info("number of Eac Outcome save on server => : {}", savedEacOutcome.size());
        return savedEacOutcome;
    }

    /**
     * Handles patient Hiv Observation sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Hiv Observation
     */
    private List<Observation> processAndSaveHivObservationOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Observation> observations = new ArrayList<>();
        //Sync related patient before syncing Observation
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalVisitQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT_VISIT, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent() || optionalVisitQueue.isPresent()) {
            //Return empty
            return observations;
        }
        List<Observation> clientObservations = objectMapper.readValue(data, new TypeReference<List<Observation>>() {});
        clientObservations.forEach(clientObservation -> {
            checkFacilityId(clientObservation.getFacilityId(), facilityId);
            Observation observation = new Observation();
            BeanUtils.copyProperties(clientObservation, observation);
            Optional<Observation> foundObservation = observationRepository.findByUuid(clientObservation.getUuid());
            //Set id for new or old Observation on the server
            if(foundObservation.isPresent()){
                observation.setId(foundObservation.get().getId());
            } else {
                observation.setId(null);
            }
            observations.add(observation);

        });

        List<Observation> savedObservation = observationRepository.saveAll(observations);
        log.info("number of Observation save on server => : {}", savedObservation.size());
        return savedObservation;
    }


    /**
     * Handles PrEP Eligibility sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Prep Eligibility
     */
    private List<PrepEligibility> processAndSavePrepEligibilityOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<PrepEligibility> eligibilities = new ArrayList<>();
        //Sync related patient before syncing Observation
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent()) {
            //Return empty
            return eligibilities;
        }
        List<PrepEligibility> clientEligibilities = objectMapper.readValue(data, new TypeReference<List<PrepEligibility>>() {});
        clientEligibilities.forEach(clientEligibility -> {
            checkFacilityId(clientEligibility.getFacilityId(), facilityId);
            PrepEligibility eligibility = new PrepEligibility();
            BeanUtils.copyProperties(clientEligibility, eligibility);
            Optional<PrepEligibility> foundEligibility = prepEligibilityRepository
                    .findByUuid(clientEligibility.getUuid());
            //Set id for new or old Eligibility on the server
            if(foundEligibility.isPresent()){
                eligibility.setId(foundEligibility.get().getId());
            } else {
                eligibility.setId(null);
            }
            eligibilities.add(eligibility);

        });

        List<PrepEligibility> savedEligibilities = prepEligibilityRepository.saveAll(eligibilities);
        log.info("number of Observation save on server => : {}", savedEligibilities.size());
        return savedEligibilities;
    }

    /**
     * Handles Prep Enrollment sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Prep Enrollment
     */
    private List<PrepEnrollment> processAndSavePrepEnrollmentOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<PrepEnrollment> enrollments = new ArrayList<>();
        //Sync related patient before syncing Observation
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalEligibilityQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PREP_ELIGIBILITY, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent() || optionalEligibilityQueue.isPresent()) {
            //Return empty
            return enrollments;
        }
        List<PrepEnrollment> clientEnrollments = objectMapper.readValue(data, new TypeReference<List<PrepEnrollment>>() {});
        clientEnrollments.forEach(prepEnrollment -> {
            checkFacilityId(prepEnrollment.getFacilityId(), facilityId);
            PrepEnrollment enrollment = new PrepEnrollment();
            BeanUtils.copyProperties(prepEnrollment, enrollment);
            Optional<PrepEnrollment> foundEnrollment = prepEnrollmentRepository
                    .findByUuid(prepEnrollment.getUuid());
            //Set id for new or old Enrollment on the server
            if(foundEnrollment.isPresent()){
                enrollment.setId(foundEnrollment.get().getId());
            } else {
                enrollment.setId(null);
            }
            enrollments.add(enrollment);

        });

        List<PrepEnrollment> savedEnrollments = prepEnrollmentRepository.saveAll(enrollments);
        log.info("number of Prep Enrollment save on server => : {}", savedEnrollments.size());
        return savedEnrollments;
    }

    /**
     * Handles Prep Clinic sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Prep Clinic
     */
    private List<PrepClinic> processAndSavePrepClinicOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<PrepClinic> prepClinics = new ArrayList<>();
        //Sync related patient before syncing Observation
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalEligibilityQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PREP_ELIGIBILITY, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalEnrollmentQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PREP_ENROLLMENT, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent()
                || optionalEligibilityQueue.isPresent()
                || optionalEnrollmentQueue.isPresent()) {
            //Return empty
            return prepClinics;
        }
        List<PrepClinic> clientClinics = objectMapper.readValue(data, new TypeReference<List<PrepClinic>>() {});
        clientClinics.forEach(clientClinic -> {
            checkFacilityId(clientClinic.getFacilityId(), facilityId);
            PrepClinic clinic = new PrepClinic();
            BeanUtils.copyProperties(clientClinic, clinic);
            Optional<PrepClinic> foundClinic = prepClinicRepository
                    .findByUuid(clientClinic.getUuid());
            //Set id for new or old Prep Clinic on the server
            if(foundClinic.isPresent()){
                clinic.setId(foundClinic.get().getId());
            } else {
                clinic.setId(null);
            }
            prepClinics.add(clinic);

        });

        List<PrepClinic> savedClinics = prepClinicRepository.saveAll(prepClinics);
        log.info("number of Prep Clinic save on server => : {}", savedClinics.size());
        return savedClinics;
    }

    /**
     * Handles Prep Interruption sync to central server - considered as level .
     * @param data
     * @param objectMapper
     * @param facilityId
     * @return the List of Prep Interruption
     */
    private List<PrepInterruption> processAndSavePrepInterruptionOnServer(String data, ObjectMapper objectMapper, Long facilityId) throws Exception {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<PrepInterruption> interruptions = new ArrayList<>();
        //Sync related patient before syncing Prep Interruption
        Optional<SyncQueue>  optionalPatientQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PATIENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalEligibilityQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PREP_ELIGIBILITY, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalEnrollmentQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PREP_ENROLLMENT, facilityId, PROCESSED);

        Optional<SyncQueue>  optionalClinicQueue = syncQueueRepository
                .findOneByTableNameAndFacilityIdAndProcessed(PREP_CLINIC, facilityId, PROCESSED);

        if(optionalPatientQueue.isPresent()
                || optionalEligibilityQueue.isPresent()
                || optionalEnrollmentQueue.isPresent()
                || optionalClinicQueue.isPresent()) {
            //Return empty
            return interruptions;
        }
        List<PrepInterruption> clientInterruptions = objectMapper.readValue(data, new TypeReference<List<PrepInterruption>>() {});
        clientInterruptions.forEach(clientInterruption -> {
            checkFacilityId(clientInterruption.getFacilityId(), facilityId);
            PrepInterruption interruption = new PrepInterruption();
            BeanUtils.copyProperties(clientInterruption, interruption);
            Optional<PrepInterruption> foundInterruption = interruptionRepository
                    .findByUuid(clientInterruption.getUuid());
            //Set id for new or old Prep Interruption on the server
            if(foundInterruption.isPresent()){
                interruption.setId(foundInterruption.get().getId());
            } else {
                interruption.setId(null);
            }
            interruptions.add(interruption);

        });

        List<PrepInterruption> savedInterruptions = interruptionRepository.saveAll(interruptions);
        log.info("number of Prep Interruption save on server => : {}", savedInterruptions.size());
        return savedInterruptions;
    }

}
