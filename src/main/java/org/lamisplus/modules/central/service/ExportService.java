package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.domain.entities.OrganisationUnit;
import org.lamisplus.modules.base.domain.repositories.OrganisationUnitRepository;
import org.lamisplus.modules.central.domain.entity.RemoteAccessToken;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.domain.entity.Tables;
import org.lamisplus.modules.central.repository.RemoteAccessTokenRepository;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.lamisplus.modules.central.utility.AESUtil;
import org.lamisplus.modules.patient.domain.entity.Person;
import org.lamisplus.modules.patient.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExportService {
    private final SyncHistoryService syncHistoryService;
    private final RemoteAccessTokenRepository remoteAccessTokenRepository;
    private final SyncHistoryRepository syncHistoryRepository;
    private final OrganisationUnitRepository facility;
    private final PersonRepository personRepository;

    private ObjectMapper configureObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    private SyncHistory setHistoryForGeneratedData(String tableName,
                                                   Long facilityId,
                                                   Integer uploadSize,
                                                   Long remoteAccessTokenId){
        SyncHistory newSyncHistory = new SyncHistory();
        newSyncHistory.setDateLastSync(LocalDateTime.now());
        newSyncHistory.setOrganisationUnitId(facilityId);
        newSyncHistory.setProcessed(3);
        newSyncHistory.setTableName(tableName);
        newSyncHistory.setUploadSize(uploadSize);
        newSyncHistory.setRemoteAccessTokenId(remoteAccessTokenId);
        return newSyncHistory;
    }

    public ByteArrayOutputStream generatePersonData(HttpServletResponse response, String tableName, Long facilityId, String userName){
        RemoteAccessToken remoteAccessToken = remoteAccessTokenRepository
                .findByUsername(userName)
                .orElseThrow(()-> new EntityNotFoundException(RemoteAccessToken.class, "User", "does not exist"));

        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        if(!facility.findById(facilityId).isPresent())throw new EntityNotFoundException(OrganisationUnit.class, "Facility", "does not exist");

        SyncHistory syncHistory = new SyncHistory();
        if(!Arrays.stream(Tables.values()).anyMatch(table -> String.valueOf(table).equals(tableName))) {
            throw new EntityNotFoundException(OrganisationUnit.class, "Table", "does not exist");
        }
        syncHistory=syncHistoryService.getSyncHistory(tableName, facilityId);

        List<Person> persons;
        if (syncHistory == null) {
            persons =  personRepository.findAllByFacilityId(facilityId);
        } else {
            persons = personRepository.getAllDueForServerUpload(syncHistory.getDateLastSync(), facilityId);
        }
        syncHistory = setHistoryForGeneratedData(tableName, facilityId, persons.size(), remoteAccessToken.getId());
        try{
            ObjectMapper mapper = configureObjectMapper();
            byte[] bytes = mapper.writeValueAsBytes(persons);
            SecretKey secretKey = AESUtil.getPrivateAESKeyFromDB(remoteAccessToken);
            bytes = AESUtil.encrypt(bytes, secretKey);
            bao.write(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(syncHistory != null && syncHistory.getId() == null){
                syncHistoryRepository.save(syncHistory);
            }
        }
        return bao;
    }

}
