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
import org.lamisplus.modules.central.domain.dto.HivEnrollmentSyncDto;
import org.lamisplus.modules.central.domain.dto.PatientVisitSyncDto;
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
    private final PersonRepository personRepository;
    private final HivEnrollmentRepository enrollmentRepository;

    private final PersonService personService;
    private final BiometricRepository biometricRepository;

    private final VisitRepository visitRepository;

    private final VitalSignRepository vitalSignRepository;

    private final ARTClinicalRepository artClinicalRepository;

    private final ArtPharmacyRepository artPharmacyRepository;

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
                return processAndSavePatientsOnServer(data, objectMapper);
            case "biometric":
                log.info("Saving " + table + " on Server");
                return processAndSaveBiometricsOnServer(data, objectMapper);

        /**    case "triage_vital_sign":
                log.info("Saving " + table + " on Server");
                return processAndSaveVitalSignOnServer(data, objectMapper);

            case "visit":
                log.info("Saving " + table + " on Server");
                return processAndSaveVisitsOnServer(data, objectMapper);

            case "hiv_enrollment":
                log.info("Saving " + table + " on Server");
                return processAndSaveHivEnrollmentsOnServer(data, objectMapper);
            case "hiv_art_clinical":
                log.info("Saving " + table + " on Server");
                return this.processAndSaveARTClinicalsOnServer(data, objectMapper);
            case "hiv_art_pharmacy":
                log.info("Saving " + table + " on Server");
                return this.processAndSaveArtPharmacyOnServer(data, objectMapper);
         */
        }

        List<String> msg = new LinkedList<>();
        msg.add("Nothing was saved on the server");
        return msg;
    }

    private List<Person> processAndSavePatientsOnServer(String data, ObjectMapper objectMapper) throws JsonProcessingException {
        List<Person> persons = new LinkedList<>();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Person> clientPersonList = objectMapper.readValue(data, new TypeReference<List<Person>>() {
        });

        clientPersonList.forEach(clientPerson -> {
            Person person = new Person();
            BeanUtils.copyProperties(clientPerson, person);
            Optional<Person> foundPerson = personRepository.findByUuid(clientPerson.getUuid());
            //Set Id for new or old person on the server
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

    private List<Biometric> processAndSaveBiometricsOnServer(String data, ObjectMapper objectMapper) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Biometric> clientBiometrics = objectMapper.readValue(data, new TypeReference<List<Biometric>>() {});
        List<Biometric> savedBiometrics = biometricRepository.saveAll(clientBiometrics);
        log.info("number of patients save on server => : {}", savedBiometrics.size());
        return savedBiometrics;
    }

   //========================Dr Karim Coding Session Start from here============================
  /**  private List<VitalSign> processAndSaveVitalSignOnServer(String data, ObjectMapper objectMapper) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<VitalSign> clientTriages = objectMapper.readValue(data, new TypeReference<List<VitalSign>>() {});
        List<VitalSign> savedTriages = vitalSignRepository.saveAll(clientTriages);
        log.info("number of patients save on server => : {}", savedTriages.size());
        return savedTriages;
    }

    private List<Visit> processAndSaveVisitsOnServer(String data, ObjectMapper objectMapper) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Visit> clientVisit = objectMapper.readValue(data, new TypeReference<List<Visit>>() {});
        List<Visit> savedVisit = visitRepository.saveAll(clientVisit);
        log.info("number of patients save on server => : {}", savedVisit.size());
        return savedVisit;
    }

    private List<HivEnrollment> processAndSaveHivEnrollmentsOnServer(String data, ObjectMapper objectMapper) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<HivEnrollment> clientHivEnrollment = objectMapper.readValue(data, new TypeReference<List<HivEnrollment>>() {});
        List<HivEnrollment> savedHivEnrollment = enrollmentRepository.saveAll(clientHivEnrollment);
        log.info("number of patients save on server => : {}", savedHivEnrollment.size());
        return savedHivEnrollment;
    }

    private List<ARTClinical> processAndSaveARTClinicalsOnServer(String data, ObjectMapper objectMapper) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<ARTClinical> clientARTClinical = objectMapper.readValue(data, new TypeReference<List<ARTClinical>>() {});
        List<ARTClinical> savedARTClinical = artClinicalRepository.saveAll(clientARTClinical);
        log.info("number of patients save on server => : {}", savedARTClinical.size());
        return savedARTClinical;
    }
    private List<ArtPharmacy> processAndSaveArtPharmacyOnServer(String data, ObjectMapper objectMapper) throws JsonProcessingException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<ArtPharmacy> clientArtPharmacy = objectMapper.readValue(data, new TypeReference<List<ArtPharmacy>>() {});
        List<ArtPharmacy> savedArtPharmacy = artPharmacyRepository.saveAll(clientArtPharmacy);
        log.info("number of patients save on server => : {}", savedArtPharmacy.size());
        return savedArtPharmacy;
    }*/
}
