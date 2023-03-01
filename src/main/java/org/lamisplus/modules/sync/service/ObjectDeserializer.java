package org.lamisplus.modules.sync.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.hiv.domain.entity.HivEnrollment;
import org.lamisplus.modules.hiv.repositories.HivEnrollmentRepository;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.domain.entity.Visit;
import org.lamisplus.modules.patient.repository.*;
import org.lamisplus.modules.patient.service.PersonService;
import org.lamisplus.modules.sync.domain.dto.HivEnrollmentSyncDto;
import org.lamisplus.modules.sync.domain.dto.PatientSyncDto;
import org.lamisplus.modules.sync.domain.dto.PatientVisitSyncDto;
import org.lamisplus.modules.sync.repository.RemoteAccessTokenRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
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

    private final VisitRepository visitRepository;

    private final RemoteAccessTokenRepository remoteAccessTokenRepository;


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    public List<?> deserialize(byte[] bytes, String table) throws Exception {
        String data = new String(bytes, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        if (table.equals("patient")) {
            log.info("Saving " + table + " on Server");
            return processAndSavePatientsOnServer(data, objectMapper);
        }
        if (table.equals("visit")) {
            log.info("Saving " + table + " on Server");
            return processAndSaveVisitsOnServer(data, objectMapper);
        }
        /*if (table.equals("triage_vital_sign")) {
            log.info("Saving " + table + " on Server");
            return processAndSaveEnrollmentOnServer(data, objectMapper);
        }*/
        if (table.equals("hiv_enrollment")) {
            log.info("Saving " + table + " on Server");
            return processAndSaveHivEnrollmentOnServer(data, objectMapper);
        }
        /*if (table.equals("hiv_art_clinical")) {
            log.info("Saving " + table + " on Server");
            return processAndSaveAppointmentsOnServer(data, objectMapper);
        }*/
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

    private List<Visit> processAndSaveVisitsOnServer(String data, ObjectMapper objectMapper) throws JsonProcessingException {
        List<Visit> visits = new LinkedList<>();
        List<PatientVisitSyncDto> patientVisitSyncDtoList = objectMapper.readValue(data, new TypeReference<List<PatientVisitSyncDto>>() {
        });
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        patientVisitSyncDtoList.forEach(visitDTO -> {
            Visit visit = new Visit();
            BeanUtils.copyProperties(visitDTO, visit);
            Optional<PatientVisitSyncDto> saveVisit = remoteAccessTokenRepository.getByPersonVisitUuid(visitDTO.getUuid());

            saveVisit.ifPresent(value -> visit.setId(value.getId()));
            visits.add(visit);
        });
        List<Visit> savedVisits = visitRepository.saveAll(visits);
        log.info("number of visits save on server => : {}", savedVisits.size());
        return savedVisits;
    }

    private List<HivEnrollment> processAndSaveHivEnrollmentOnServer(String data, ObjectMapper objectMapper) throws JsonProcessingException {
        List<HivEnrollment> hivEnrollments = new LinkedList<>();
        List<HivEnrollmentSyncDto> enrollmentSyncDtos = objectMapper.readValue(data, new TypeReference<List<HivEnrollmentSyncDto>>() {
        });
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        enrollmentSyncDtos.forEach(enrollmentSyncDto -> {
            HivEnrollment hivEnrollment = new HivEnrollment();
            BeanUtils.copyProperties(enrollmentSyncDto, hivEnrollment);
            Optional<HivEnrollmentSyncDto> saveEnrollment = remoteAccessTokenRepository.getByHivEnrollmentUuid(enrollmentSyncDto.getUuid());

            saveEnrollment.ifPresent(value -> hivEnrollment.setId(value.getId()));
            hivEnrollments.add(hivEnrollment);
        });
        List<HivEnrollment> saveEnrollments = enrollmentRepository.saveAll(hivEnrollments);
        log.info("number of form-data save on server => : {}", saveEnrollments.size());
        return saveEnrollments;
    }

    /*private List<Triage> processAndSaveEnrollmentOnServer(String data, ObjectMapper objectMapper) throws JsonProcessingException {
        List<Encounter> encounters = new LinkedList<>();
        List<EncounterDTO> encounterDTOS =
                objectMapper.readValue(data, new TypeReference<List<EncounterDTO>>() {
                });
        encounterDTOS.forEach(encounterDTO -> {
            Encounter encounter = encounterMapper.toEncounter(encounterDTO);
            personRepository.findByUuid(encounterDTO.getPatientUuid())
                    .ifPresent(patient -> {
                        encounter.setPatientId(patient.getId());
                        visitRepository.findByUuid(encounterDTO.getVisitUuid())
                                .ifPresent(visit -> {
                                    encounter.setVisitId(visit.getId());
                                    encounterRepository.findByUuid(encounter.getUuid())
                                            .ifPresent(encounterDb -> encounter.setId(encounterDb.getId()));
                                    encounters.add(encounter);
                                });

                    });
        });
        List<Encounter> savedEncounters = encounterRepository.saveAll(encounters);
        log.info("number of encounters save on server => : {}", savedEncounters.size());
        return savedEncounters;
    }*/

    /*private List<Appointment> processAndSaveAppointmentsOnServer(String data, ObjectMapper objectMapper) throws JsonProcessingException {
        List<Appointment> appointments = new LinkedList<>();
        List<AppointmentDTO> appointmentDTOS =
                objectMapper.readValue(data, new TypeReference<List<AppointmentDTO>>() {
                });
        appointmentDTOS.forEach(appointmentDTO -> {
            Appointment appointment = appointmentMapper.toAppointment(appointmentDTO);
            personRepository.findByUuid(appointmentDTO.getPatientUuid())
                    .ifPresent(patient -> {
                        appointment.setPatientId(patient.getId());
                        visitRepository.findByUuid(appointmentDTO.getVisitUuid())
                                .ifPresent(visit -> {
                                    appointment.setVisitId(visit.getId());
                                    encounterRepository.findByUuid(appointment.getUuid())
                                            .ifPresent(encounter -> {
                                                appointment.setId(encounter.getId());
                                            });
                                    appointmentRepository.findByUuid(appointment.getUuid())
                                            .ifPresent(appointmentDb
                                                    -> appointment.setId(appointmentDb.getId()));
                                    appointments.add(appointmentRepository.save(appointment));
                                });
                    });
        });
        log.info("number of appointments save on server => : {}", appointments.size());
        return appointments;
    }*/
}
