package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.base.domain.entities.User;
import org.lamisplus.modules.base.service.UserService;
import org.lamisplus.modules.central.controller.ExportController;
import org.lamisplus.modules.central.domain.dto.RadetUploaders;
import org.lamisplus.modules.central.domain.dto.RemoteUrlDTO;
import org.lamisplus.modules.central.domain.entity.*;
import org.lamisplus.modules.central.repository.*;
import org.lamisplus.modules.central.utility.ConstantUtility;
import org.lamisplus.modules.central.utility.FileUtility;
import org.lamisplus.modules.central.utility.HttpConnectionManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncServiceImpl implements SyncService {

    private final CentralRadetRepository radetRepository;
    private final CentralHtsRepository htsRepository;
    private final CentralPrepRepository prepRepository;
    private final SyncHistoryRepository syncHistoryRepository;
    private final RemoteAccessTokenRepository remoteAccessTokenRepository;
    private final RadetUploadIssueTrackersRepository radetUploadIssueTrackersRepository;
    //private final CentralDataElementRepository centralDataElementRepository;
    private final RadetUploadTrackersRepository radetUploadTrackersRepository;

    private final FileUtility fileUtility;
    private static final String STATE = "state";
    private static final String LGA = "lga";
    private static final String FACILITY_NAME = "facilityName";
    private static final String FACILITY = "facility";
    private static final String DATIM_ID = "datimId";
    private static final String PERSON_UUID = "personUuid";
    private static final String HOSPITAL_NUMBER = "hospitalNumber";
    private static final String DATIM_CODE = "datimCode";
    private static final String CLIENT_CODE = "clientCode";
    private static final String PATIENT_ID = "patientId";
    private static final String PERIOD = "2023Q2";
    private final RemoteAccessTokenRepository accessTokenRepository;
    private final UserService userService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean bulkImport(MultipartFile multipartFile, String datimId) throws IOException {
        boolean isProcessed = false;
        log.info("Initializing data import");
        // ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
        String sourceFileName = ConstantUtility.TEMP_SERVER_DIR + multipartFile.getOriginalFilename();

        File inputFile = new File(sourceFileName);
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(inputFile))) {
            log.info("Unzipping the compressed file");
//            multipartFile.transferTo(new File(sourceFileName));
//            log.info("1");
//            Path target = Paths.get(ConstantUtility.TEMP_SERVER_DIR);
//            log.info("2");
//            Path source = Paths.get(sourceFileName);
//            log.info("3");
//            fileUtility.unzipFile(source, target);
//            log.info("4");
            try {
                byte[] data = multipartFile.getBytes();
                bos.write(data);
                bos.close();
                Path target = Paths.get(ConstantUtility.TEMP_SERVER_DIR);
                Path source = Paths.get(sourceFileName);
                fileUtility.unzipFile(source, target);
                log.info("We got here");

            } catch (Exception e) {
                e.printStackTrace();
            }


            String filePath = ConstantUtility.TEMP_SERVER_DIR + ConstantUtility.RADET_FILENAME;
            if (Files.exists(Paths.get(filePath))) {
                log.info("Importing RADET file");
                // List<Radet> radetList = objectMapper.readValue(new File(filePath), new TypeReference<List<Radet>>() {});
                importRadet(filePath, datimId);
            }
//            filePath = ConstantUtility.TEMP_SERVER_DIR + ConstantUtility.HTS_FILENAME;
//            if (Files.exists(Paths.get(filePath))) {
//                log.info("Importing HTS file");
//              //  List<HtsReportDto> htsReportDtoList = objectMapper.readValue(new File(filePath), new TypeReference<List<HtsReportDto>>() {});
//                importHts(filePath);
//            }
//            filePath = ConstantUtility.TEMP_SERVER_DIR + ConstantUtility.PREP_FILENAME;
//            if (Files.exists(Paths.get(filePath))) {
//                log.info("Importing PREP file");
//                //List<PrepReportDto> radetList = objectMapper.readValue(new File(filePath), new TypeReference<List<PrepReportDto>>() {});
//                importPrep(filePath);
//            }
            isProcessed = true;
            //  FileUtils.forceDelete(new File(sourceFileName));
            log.info("Data import completed successfully.");
        } catch (Exception exception) {
            log.info(exception.getMessage());
        }

        return isProcessed;
    }

    @Override
    public String getDatimId(Long facilityId) {
        String datimId = radetUploadTrackersRepository.getDatimCode(facilityId);
        return datimId;

    }

    @Override
    public void importRadet(String jsonFilePath, String datimId) throws IOException {

        try (JsonParser parser = new JsonFactory().createParser(new FileReader(jsonFilePath))) {
            List<CentralRadet> radetList = new ArrayList<>();
            if (parser.nextToken() == JsonToken.START_ARRAY) {
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    if (parser.currentToken() != JsonToken.START_OBJECT) {
                        throw new RuntimeException("Expected content to be a RADET object");
                    }
                    CentralRadet radet = new CentralRadet();
                    mapRadet(radet, parser, datimId);
                    radetList.add(radet);
                }
            }
            radetRepository.saveAll(radetList);
            creatRadetUploadTracker(datimId, LocalDate.now() + " @" + LocalTime.now());
        } catch (Exception e) {
            log.error("Error importing RADET data: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    void mapRadet(CentralRadet radet, JsonParser parser, String datimId) throws IOException {

        String personUuid = "";
        //String period = "2023Q2";

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            try {
                String fieldName = parser.getCurrentName();
                parser.nextToken();
                if (fieldName.equals(STATE)) {
                    radet.setState(parser.getText());
                }
                if (fieldName.equals(LGA)) {
                    radet.setLga(parser.getText());
                }
                if (fieldName.equals(FACILITY_NAME)) {
                    radet.setFacilityName(parser.getText());
                }
                if (fieldName.equals(DATIM_ID)) {
                    radet.setDatimId(parser.getText());
                }
                if (fieldName.equals(HOSPITAL_NUMBER)) {
                    radet.setHospitalNumber(parser.getText());
                }
                if (fieldName.equals(PERSON_UUID)) {
                    personUuid = parser.getText();
                    radet.setPersonUuid(personUuid);
                }

                if (fieldName.equals("DateOfBirth")) {
                    String date = parser.getText();
                    if (date != null) radet.setDateOfBirth(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("Age")) {
                    String value = parser.getText();
                    if (value != null) radet.setAge(Double.valueOf(parser.getText()));
                }
                if (fieldName.equals("Gender")) {
                    radet.setGender(parser.getText());
                }
                if (fieldName.equals("TargetGroup")) {
                    radet.setTargetGroup(parser.getText());
                }

                if (fieldName.equals("EnrollmentSetting")) {
                    radet.setEnrollmentSetting(parser.getText());
                }
                if (fieldName.equals("ArtStartDate")) {
                    String date = parser.getText();
                    if (date != null) radet.setArtStartDate(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("RegimenAtStart")) {
                    radet.setRegimenAtStart(parser.getText());
                }
                if (fieldName.equals("RegimenLineAtStart")) {
                    radet.setRegimenLineAtStart(parser.getText());
                }
                if (fieldName.equals("PregnancyStatus")) {
                    radet.setPregnancyStatus(parser.getText());
                }
                if (fieldName.equals("CurrentClinicalStage")) {
                    radet.setCurrentClinicalStage(parser.getText());
                }
                if (fieldName.equals("CurrentWeight")) {
                    radet.setCurrentWeight(new Double(parser.getText()));
                }
                if (fieldName.equals("ViralLoadIndication")) {
                    radet.setViralLoadIndication(parser.getText());
                }
                if (fieldName.equals("DateOfViralLoadSampleCollection")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfViralLoadSampleCollection(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("CurrentViralLoad")) {
                    radet.setCurrentViralLoad(parser.getText());
                }
                System.out.println(1);
                if (fieldName.equals("DateOfCurrentViralLoad")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfCurrentViralLoad(LocalDate.parse(date, formatter));
                }
                System.out.println(2);
                if (fieldName.equals("DateOfCurrentViralLoadSample")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfCurrentViralLoadSample(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("LastCd4Count")) {
                    radet.setLastCd4Count(parser.getText());
                }
                if (fieldName.equals("DateOfLastCd4Count")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfLastCd4Count(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("CurrentRegimenLine")) {
                    radet.setCurrentRegimenLine(parser.getText());
                }
                if (fieldName.equals("CurrentARTRegimen")) {
                    radet.setCurrentArtRegimen(parser.getText());
                }
                if (fieldName.equals("MonthsOfARVRefill")) {
                    radet.setMonthsOfArvRefill(Double.valueOf(parser.getText()));
                }
                if (fieldName.equals("LastPickupDate")) {
                    String date = parser.getText();

                    if (date != null) radet.setLastPickupDate(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("NextPickupDate")) {
                    String date = parser.getText();

                    if (date != null) radet.setNextPickupDate(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("CurrentStatusDate")) {
                    String date = parser.getText();

                    if (date != null) radet.setCurrentStatusDate(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("setCurrentStatus")) {
                    radet.setCurrentStatus(parser.getText());
                }
                if (fieldName.equals("PreviousStatusDate")) {
                    String date = parser.getText();

                    if (date != null) radet.setPreviousStatusDate(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("PreviousStatus")) {
                    radet.setPreviousStatus(parser.getText());
                }
                if (fieldName.equals("DateBiometricsEnrolled")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateBiometricsEnrolled(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("NumberOfFingersCaptured")) {
                    String value = parser.getText();
                    if (value != null) radet.setNumberOfFingersCaptured(Double.valueOf(value));
                }
                if (fieldName.equals("DateOfCommencementOfEAC")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfCommencementOfEac(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("NumberOfEACSessionCompleted")) {
                    String value = parser.getText();
                    if (value != null) radet.setNumberOfEacSessionCompleted(Double.valueOf(value));
                }
                if (fieldName.equals("DateOfLastEACSessionCompleted")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfLastEacSessionCompleted(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("DateOfExtendEACCompletion")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfExtendEacCompletion(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("DateOfRepeatViralLoadResult")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfRepeatViralLoadResult(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("DateOfRepeatViralLoadEACSampleCollection")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfViralLoadSampleCollection(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("RepeatViralLoadResult")) {
                    radet.setRepeatViralLoadResult(parser.getText());
                }
                if (fieldName.equals("TbStatus")) {
                    radet.setTbStatus(parser.getText());
                }
                if (fieldName.equals("DateOfTbScreened")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfTbScreened(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("DateOfCurrentRegimen")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfCurrentRegimen(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("DateOfIptStart")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfIptStart(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("IptCompletionDate")) {
                    String date = parser.getText();

                    if (date != null) radet.setIptCompletionDate(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("IptType")) {
                    radet.setIptType(parser.getText());
                }
                if (fieldName.equals("ResultOfCervicalCancerScreening")) {
                    radet.setResultOfCervicalCancerScreening(parser.getText());
                }
                if (fieldName.equals("CervicalCancerScreeningType")) {
                    radet.setCervicalCancerScreeningType(parser.getText());
                }
                if (fieldName.equals("CervicalCancerScreeningMethod")) {
                    radet.setCervicalCancerScreeningMethod(parser.getText());
                }
                if (fieldName.equals("CervicalCancerTreatmentScreened")) {
                    radet.setCervicalCancerTreatmentScreened(parser.getText());
                }
                if (fieldName.equals("DateOfCervicalCancerScreening")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfCervicalCancerScreening(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("OvcNumber")) {
                    radet.setOvcNumber(parser.getText());
                }
                if (fieldName.equals("HouseholdNumber")) {
                    radet.setHouseHoldNumber(parser.getText());
                }
                if (fieldName.equals("CareEntry")) {
                    radet.setCareEntry(parser.getText());
                }
                if (fieldName.equals("CauseOfDeath")) {
                    radet.setCauseOfDeath(parser.getText());
                }
                if (fieldName.equals("VlEligibilityStatus")) {
                    radet.setVlEligibilityStatus(Boolean.valueOf(parser.getText()));
                }
                if (fieldName.equals("DateOfVlEligibilityStatu")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfVlEligibilityStatus(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("TbDiagnosticTestType")) {
                    radet.setTbDiagnosticTestType(parser.getText());
                }
                if (fieldName.equals("DateOfTbSampleCollection")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfTbSampleCollection(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("TbDiagnosticResult")) {
                    radet.setTbDiagnosticResult(parser.getText());
                }
                if (fieldName.equals("DsdModel")) {
                    radet.setDsdModel(parser.getText());
                }
                if (fieldName.equals("DateOfTbDiagnosticResultReceived")) {
                    String date = parser.getText();

                    if (date != null) radet.setDateOfTbDiagnosticResultReceived(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("TbTreatementType")) {
                    radet.setTbTreatementType(parser.getText());
                }
                if (fieldName.equals("TbTreatmentOutcome")) {
                    radet.setTbTreatmentOutcome(parser.getText());
                }
                if (fieldName.equals("TbTreatmentStartDate")) {
                    String date = parser.getText();

                    if (date != null) radet.setTbTreatmentStartDate(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("TbCompletionDate")) {
                    String date = parser.getText();

                    if (date != null) radet.setTbCompletionDate(LocalDate.parse(date, formatter));
                }
                if (fieldName.equals("IptCompletionStatus()")) {
                    radet.setIptCompletionStatus(parser.getText());
                }
                try {
                    String radetUniqueNo = personUuid + "-" + datimId + "-" + PERIOD;
                    radet.setPeriod(PERIOD);
                    radet.setRadetUniqueNo(radetUniqueNo);
                    radet.setSubmissionTime(LocalDate.now() + " @" + LocalTime.now());
                    Optional<CentralRadet> centralRadetOptional = radetRepository.getCentralRadetByRadetUniqueNo(radetUniqueNo);
                    if (centralRadetOptional.isPresent()) {
                        radet.setId(centralRadetOptional.get().getId());
                        //continue;
                    } else radet.setId(null);
                } catch (Exception e) {
                }

            } catch (Exception e) {
                creatRadetUploadIssueTracker(datimId, (LocalDate.now() + " @" + LocalTime.now()));// to be done later;
            }


        }
    }
      public void creatRadetUploadTracker(String datimId, String submissionTime) {
        RadetUploaders radetUploaders = radetRepository.getRadetUploaders(datimId);
        RadetUploadTrackers radetUploadTrackers = new RadetUploadTrackers();
        radetUploadTrackers.setIpCode(radetUploaders.getIpcode());
        radetUploadTrackers.setIpName(radetUploaders.getIpname());
        radetUploadTrackers.setFacilityId(radetUploaders.getId());
        radetUploadTrackers.setFacilityName(radetUploaders.getFacility());
        radetUploadTrackers.setState(radetUploaders.getState());
        radetUploadTrackers.setLga(radetUploaders.getLga());
//        radetUploadTrackers.setFiscalYear(fy);
//        radetUploadTrackers.setReportingQuarter(qt);
        radetUploadTrackers.setCreatedDate(submissionTime);
        List<RadetUploadTrackers> radetUploadTrackersList = radetUploadTrackersRepository.getRadetUploadTrackersByFacilityId(radetUploaders.getId());
        if (radetUploadTrackersList.isEmpty()) {
            this.radetUploadTrackersRepository.save(radetUploadTrackers);
        } else {
            Iterator iterator = radetUploadTrackersList.iterator();
            while (iterator.hasNext()) {
                RadetUploadTrackers radetUploadTrackers1 = (RadetUploadTrackers) iterator.next();
                this.radetUploadTrackersRepository.delete(radetUploadTrackers1);
            }
            this.radetUploadTrackersRepository.save(radetUploadTrackers);

        }


    }


    public void creatRadetUploadIssueTracker(String datimId, String submissionTime) {
        try {
            System.out.println("datimId = " + datimId);
            RadetUploaders radetUploaders = radetRepository.getRadetUploaders(datimId);
            RadetUploadIssueTrackers radetUploadTrackers = new RadetUploadIssueTrackers();
            radetUploadTrackers.setIpCode(radetUploaders.getIpcode());
            radetUploadTrackers.setIpName(radetUploaders.getIpname());
            radetUploadTrackers.setFacilityId(radetUploaders.getId());
            radetUploadTrackers.setFacilityName(radetUploaders.getFacility());
            radetUploadTrackers.setState(radetUploaders.getState());
            radetUploadTrackers.setLga(radetUploaders.getLga());
//            radetUploadTrackers.setFiscalYear(fy);
//            radetUploadTrackers.setReportingQuarter(qt);
            radetUploadTrackers.setCreatedDate(submissionTime);
            List<RadetUploadIssueTrackers> radetUploadTrackersList = radetUploadIssueTrackersRepository.findRadetUploadIssueTrackersByFacilityId(radetUploaders.getId());
            if (radetUploadTrackersList.isEmpty()) {
                this.radetUploadIssueTrackersRepository.save(radetUploadTrackers);
            } else {
                Iterator iterator = radetUploadTrackersList.iterator();
                while (iterator.hasNext()) {
                    RadetUploadIssueTrackers radetUploadTrackers1 = (RadetUploadIssueTrackers) iterator.next();
                    this.radetUploadIssueTrackersRepository.delete(radetUploadTrackers1);
                }
                this.radetUploadIssueTrackersRepository.save(radetUploadTrackers);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean bulkImport(String sourceFileName, String datimId) throws IOException {
        boolean isProcessed = false;
        log.info("Initializing data import");

        try {
            log.info("Unzipping the compressed file");
            Path target = Paths.get(ConstantUtility.TEMP_SERVER_DIR);
            Path source = Paths.get(sourceFileName);
            fileUtility.unzipFile(source, target);

            String filePath = ConstantUtility.TEMP_SERVER_DIR + ConstantUtility.RADET_FILENAME;
            if (Files.exists(Paths.get(filePath))) {
                log.info("Importing RADET file");
                // List<Radet> radetList = objectMapper.readValue(new File(filePath), new TypeReference<List<Radet>>() {});
                importRadet(filePath, datimId);
            }
            filePath = ConstantUtility.TEMP_SERVER_DIR + ConstantUtility.HTS_FILENAME;
            if (Files.exists(Paths.get(filePath))) {
                log.info("Importing HTS file");
                //  List<HtsReportDto> htsReportDtoList = objectMapper.readValue(new File(filePath), new TypeReference<List<HtsReportDto>>() {});
                importHts(filePath, datimId);
            }
            filePath = ConstantUtility.TEMP_SERVER_DIR + ConstantUtility.PREP_FILENAME;
            if (Files.exists(Paths.get(filePath))) {
                log.info("Importing PREP file");
                //List<PrepReportDto> radetList = objectMapper.readValue(new File(filePath), new TypeReference<List<PrepReportDto>>() {});
                importPrep(filePath, datimId);
            }
            isProcessed = true;
            //  FileUtils.forceDelete(new File(sourceFileName));
            log.info("Data import completed successfully.");
        } catch (Exception exception) {
            log.info(exception.getMessage());
        }

        return isProcessed;
    }
//----------------------------------Kennedy-------------------------------------------
@Override
      public void importHts(String jsonFilePath, String datimId) throws IOException {
        try (JsonParser parser = new JsonFactory().createParser(new FileReader(jsonFilePath))) {
            List<CentralHts> htsList = new ArrayList<>();
            if (parser.nextToken() == JsonToken.START_ARRAY) {
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    if (parser.currentToken() != JsonToken.START_OBJECT) {
                        throw new RuntimeException("Expected content to be a HTS object");
                    }
                    CentralHts hts = new CentralHts();
                    mapHts(hts, parser, datimId);
                    htsList.add(hts);
                }
            }
            htsRepository.saveAll(htsList);
            creatRadetUploadTracker(datimId, LocalDate.now() + " @" + LocalTime.now());
        } catch (Exception e) {
            log.error("Error importing HTS data: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void importPrep(String jsonFilePath, String datimId) throws IOException {

        try (JsonParser parser = new JsonFactory().createParser(new FileReader(jsonFilePath))) {
            List<CentralPrep> prepList = new ArrayList<>();
            if (parser.nextToken() == JsonToken.START_ARRAY) {
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    if (parser.currentToken() != JsonToken.START_OBJECT) {
                        throw new RuntimeException("Expected content to be a PREP object");
                    }
                    CentralPrep prep = new CentralPrep();
                    mapPrep(prep, parser, datimId);
                    prepList.add(prep);
                }
            }
            prepRepository.saveAll(prepList);
            creatRadetUploadTracker(datimId, LocalDate.now() + " @" + LocalTime.now());
        } catch (Exception e) {
            log.error("Error importing PREP data: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    void mapHts(CentralHts hts, JsonParser parser, String datimId) throws IOException {

        String personUuid = "";
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            try {
                String fieldName = parser.getCurrentName();
                parser.nextToken();

                if (fieldName.equals("clientcode")) {
                    hts.setClientCode(parser.getText());
                }

                if (fieldName.equals("firstname")) {
                    hts.setFirstName(parser.getText());
                }

                if (fieldName.equals("surname")) {
                    hts.setSurname(parser.getText());
                }

                if (fieldName.equals("othername")) {
                    hts.setOtherName(parser.getText());
                }

                if (fieldName.equals("sex")) {
                    hts.setSex(parser.getText());
                }

                if (fieldName.equals("age")) {
                    String value = parser.getText();
                    if(value != null) hts.setAge(Integer.parseInt(value));
                }

                if (fieldName.equals("dateofbirth")) {

                    String value = parser.getText();
                    if(value != null)hts.setDateOfBirth(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("phonenumber")) {
                    hts.setPhoneNumber(parser.getText());
                }

                if (fieldName.equals("maritalstatus")) {
                    hts.setMaritalStatus(parser.getText());
                }

                if (fieldName.equals("lgaofresidence")) {
                    hts.setLgaOfResidence(parser.getText());
                }

                if (fieldName.equals("stateofresidence")) {
                    hts.setStateOfResidence(parser.getText());
                }

                if (fieldName.equals("facility")) {
                    hts.setFacility(parser.getText());
                }

                if (fieldName.equals("state")) {
                    hts.setState(parser.getText());
                }

                if (fieldName.equals("lga")) {
                    hts.setLga(parser.getText());
                }

                if (fieldName.equals("patientid")) {
                    hts.setPatientId(parser.getText());
                }

                if (fieldName.equals("education")) {
                    hts.setEducation(parser.getText());
                }

                if (fieldName.equals("occupation")) {
                    hts.setOccupation(parser.getText());
                }

                if (fieldName.equals("datimcode")) {
                    hts.setDatimCode(parser.getText());
                }

                if (fieldName.equals("htslatitude")) {
                    hts.setHtsLatitude(parser.getText());
                }

                if (fieldName.equals("htslongitude")) {
                    hts.setHtsLongitude(parser.getText());
                }

                if (fieldName.equals("clientaddress")) {
                    hts.setClientAddress(parser.getText());
                }

                if (fieldName.equals("datevisit")) {
                    String value = parser.getText();
                    if(value != null) hts.setDateVisit(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("firsttimevisit")) {
                    hts.setFirstTimeVisit(parser.getText());
                }

                if (fieldName.equals("numberofchildren")) {
                    String value = parser.getText();
                    if(value != null) hts.setNumberOfChildren(Integer.parseInt(value));
                }

                if (fieldName.equals("numberofwives")) {
                    String value = parser.getText();
                    if(value != null) hts.setNumberOfWives(Integer.parseInt(value));
                }

                if (fieldName.equals("indexclient")) {
                    hts.setIndexClient(parser.getText());
                }

                if (fieldName.equals("prepoffered")) {
                    String value = parser.getText();
                    if(value != null) hts.setPrepOffered(Boolean.parseBoolean(value));
                }

                if (fieldName.equals("prepaccepted")) {
                    String value = parser.getText();
                    if(value != null) hts.setPrepAccepted(Boolean.parseBoolean(value));
                }

                if (fieldName.equals("previouslytested")) {
                    hts.setPreviouslyTested(parser.getText());
                }

                if (fieldName.equals("targetgroup")) {
                    hts.setTargetGroup(parser.getText());
                }

                if (fieldName.equals("referredfrom")) {
                    hts.setReferredFrom(parser.getText());
                }

                if (fieldName.equals("testingsetting")) {
                    hts.setTestingSetting(parser.getText());
                }

                if (fieldName.equals("counselingtype")) {
                    hts.setCounselingType(parser.getText());
                }

                if (fieldName.equals("pregnancystatus")) {
                    hts.setPregnancyStatus(parser.getText());
                }

                if (fieldName.equals("breastfeeding")) {
                    String value = parser.getText();
                    if(value != null) hts.setBreastfeeding(Boolean.parseBoolean(value));
                }

                if (fieldName.equals("indextype")) {
                    hts.setIndexType(parser.getText());
                }

                if (fieldName.equals("ifrecencytestingoptin")) {
                    hts.setRecencyTestingOptIn(parser.getText());
                }

                if (fieldName.equals("recencyid")) {
                    hts.setRecencyId(parser.getText());
                }

                if (fieldName.equals("recencytesttype")) {
                    hts.setRecencyTestType(parser.getText());
                }

                if (fieldName.equals("recencytestdate")) {
                    String value = parser.getText();
                    if(value != null) hts.setRecencyTestDate(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("recencyinterpretation")) {
                    hts.setRecencyInterpretation(parser.getText());
                }

                if (fieldName.equals("finalrecencyresult")) {
                    hts.setFinalRecencyResult(parser.getText());
                }

                if (fieldName.equals("viralloadresult")) {
                    hts.setViralLoadResult(parser.getText());
                }

                if (fieldName.equals("viralloadsamplecollectiondate")) {
                    String value = parser.getText();
                    if(value != null) hts.setViralLoadSampleCollectionDate(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("viralloadconfirmationresult")) {
                    hts.setViralLoadConfirmationResult(parser.getText());
                }

                if (fieldName.equals("viralloadconfirmationdate")) {
                    String value = parser.getText();
                    if(value != null) hts.setViralLoadConfirmationDate(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("assessmentcode")) {
                    hts.setAssessmentCode(parser.getText());
                }

                if (fieldName.equals("modality")) {
                    hts.setModality(parser.getText());
                }

                if (fieldName.equals("syphilistestresult")) {
                    hts.setSyphilisTestResult(parser.getText());
                }

                if (fieldName.equals("hepatitisbtestresult")) {
                    hts.setHepatitisBTestResult(parser.getText());
                }

                if (fieldName.equals("hepatitisctestresult")) {
                    hts.setHepatitisCTestResult(parser.getText());
                }

                if (fieldName.equals("cd4type")) {
                    hts.setCd4Type(parser.getText());
                }

                if (fieldName.equals("cd4testresult")) {
                    hts.setCd4TestResult(parser.getText());
                }

                if (fieldName.equals("hivtestresult")) {
                    hts.setHivTestResult(parser.getText());
                }

                if (fieldName.equals("finalhivtestresult")) {
                    hts.setFinalHivTestResult(parser.getText());
                }

                if (fieldName.equals("dateofhivtesting")) {
                    String value = parser.getText();
                    if(value != null) hts.setDateOfHivTesting(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("numberofcondomsgiven")) {
                    hts.setNumberOfCondomsGiven(parser.getText());
                }

                if (fieldName.equals("numberoflubricantsgiven")) {
                    hts.setNumberOfLubricantsGiven(parser.getText());
                }

                if (fieldName.equals("htsuniqueno")) {
                    hts.setHtsUniqueNo(parser.getText());
                }

                if (fieldName.equals("period")) {
                    hts.setPeriod(parser.getText());
                }

                if (fieldName.equals("submissiontime")) {
                    hts.setSubmissionTime(parser.getText());
                }
                try {
                    String htsUniqueNo = personUuid + "-" + datimId + "-" + PERIOD;
                    hts.setPeriod(PERIOD);
                    hts.setHtsUniqueNo(htsUniqueNo);
                    hts.setSubmissionTime(LocalDate.now() + " @" + LocalTime.now());
                    Optional<CentralHts> centralHtsOptional = htsRepository.getCentralHtsByHtsUniqueNo(htsUniqueNo);
                    if (centralHtsOptional.isPresent()) {
                        hts.setId(centralHtsOptional.get().getId());
                        //continue;
                    } else hts.setId(null);
                } catch (Exception e) {
                }
            } catch (Exception e) {
                creatRadetUploadIssueTracker(datimId, (LocalDate.now() + " @" + LocalTime.now()));// to be done later;
            }
        }

    }

    //======================================================================================
    void mapPrep(CentralPrep prep, JsonParser parser, String datimId) throws IOException {

        String personUuid = "";
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            try {
                String fieldName = parser.getCurrentName();
                parser.nextToken();

                if (fieldName.equals("hospitalnumber")) {
                    prep.setHospitalNumber(parser.getText());
                }

                if (fieldName.equals("personuuid")) {
                    prep.setPersonUuid(parser.getText());
                }

                if (fieldName.equals("uuid")) {
                    prep.setUuid(parser.getText());
                }

                if (fieldName.equals("hospitalnumber")) {
                    prep.setHospitalNumber(parser.getText());
                }

                if (fieldName.equals("surname")) {
                    prep.setSurname(parser.getText());
                }

                if (fieldName.equals("firstname")) {
                    prep.setFirstName(parser.getText());
                }

                if (fieldName.equals("hivenrollmentdate")) {
                    String value = parser.getText();
                    if (value != null)prep.setHivEnrollmentDate(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("age")) {
                    prep.setAge(Integer.parseInt(parser.getText()));
                }

                if (fieldName.equals("othername")) {
                    prep.setOtherName(parser.getText());
                }

                if (fieldName.equals("sex")) {
                    prep.setSex(parser.getText());
                }

                if (fieldName.equals("dateofbirth")) {
                    String value = parser.getText();
                    if (value != null) prep.setDateOfBirth(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("dateofregistration")) {
                    String value = parser.getText();
                    if (value != null) prep.setDateOfRegistration(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("maritalstatus")) {
                    prep.setMaritalStatus(parser.getText());
                }

                if (fieldName.equals("education")) {
                    prep.setEducation(parser.getText());
                }

                if (fieldName.equals("occupation")) {
                    prep.setOccupation(parser.getText());
                }

                if (fieldName.equals("facilityname")) {
                    prep.setFacilityName(parser.getText());
                }

                if (fieldName.equals("lga")) {
                    prep.setLga(parser.getText());
                }

                if (fieldName.equals("state")) {
                    prep.setState(parser.getText());
                }

                if (fieldName.equals("datimid")) {
                    prep.setDatimId(parser.getText());
                }

                if (fieldName.equals("residentialstate")) {
                    prep.setResidentialState(parser.getText());
                }

                if (fieldName.equals("residentiallga")) {
                    prep.setResidentialLga(parser.getText());
                }

                if (fieldName.equals("address")) {
                    prep.setAddress(parser.getText());
                }

                if (fieldName.equals("phone")) {
                    prep.setPhone(parser.getText());
                }

                if (fieldName.equals("baselineregimen")) {
                    prep.setBaselineRegimen(parser.getText());
                }

                if (fieldName.equals("baselinesystolicbp")) {
                    String value = parser.getText();
                    if (value != null) prep.setBaselineSystolicBp(Double.parseDouble(value));
                }

                if (fieldName.equals("baselinediastolicbp")) {
                    String value = parser.getText();
                    if (value != null)prep.setBaselineDiastolicBp(Double.parseDouble(value));
                }

                if (fieldName.equals("baselinetweight")) {
                    String value = parser.getText();
                    if (value != null) prep.setBaselineTWeight(Double.parseDouble(value));
                }

                if (fieldName.equals("baselineheight")) {
                    String value = parser.getText();
                    if (value != null) prep.setBaselineHeight(Double.parseDouble(value));
                }

                if (fieldName.equals("targetgroup")) {
                    prep.setTargetGroup(parser.getText());
                }

                if (fieldName.equals("prepcommencementdate")) {
                    String value = parser.getText();
                    if (value != null) prep.setPrepCommencementDate(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("baselineurinalysis")) {
                    prep.setBaselineUrinalysis(parser.getText());
                }

                if (fieldName.equals("baselineurinalysisdate")) {
                    String value = parser.getText();
                    if (value != null) prep.setBaselineUrinalysisDate(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("baselinecreatinine")) {
                    prep.setBaselineCreatinine(parser.getText());
                }

                if (fieldName.equals("baselinehepatitisb")) {
                    prep.setBaselineHepatitisB(parser.getText());
                }

                if (fieldName.equals("baselinehepatitisc")) {
                    prep.setBaselineHepatitisC(parser.getText());
                }

                if (fieldName.equals("interruptionreason")) {
                    prep.setInterruptionReason(parser.getText());
                }

                if (fieldName.equals("interruptiondate")) {
                    String value = parser.getText();
                    if (value != null) prep.setInterruptionDate(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("hivstatusatprepinitiation")) {
                    prep.setHivStatusAtPrepInitiation(parser.getText());
                }

                if (fieldName.equals("indicationforprep")) {
                    prep.setIndicationForPrep(parser.getText());
                }

                if (fieldName.equals("currentregimen")) {
                    prep.setCurrentRegimen(parser.getText());
                }

                if (fieldName.equals("dateoflastpickup")) {
                    String value = parser.getText();
                    if (value != null) prep.setDateOfLastPickup(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("currentsystolicbp")) {
                    String value = parser.getText();
                    if (value != null) prep.setCurrentSystolicBp(Double.parseDouble(value));
                }

                if (fieldName.equals("currentdiastolicbp")) {
                    String value = parser.getText();
                    if (value != null) prep.setCurrentDiastolicBp(Double.parseDouble(value));
                }

                if (fieldName.equals("currentweight")) {
                    String value = parser.getText();
                    if (value != null)prep.setCurrentWeight(Double.parseDouble(value));
                }

                if (fieldName.equals("currentheight")) {
                    String value = parser.getText();
                    if (value != null) prep.setCurrentHeight(Double.parseDouble(value));
                }

                if (fieldName.equals("currenturinalysis")) {
                    prep.setCurrentUrinalysis(parser.getText());
                }

                if (fieldName.equals("currenturinalysisdate")) {
                    String value = parser.getText();
                    if (value != null) prep.setCurrentUrinalysisDate(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("currenthivstatus")) {
                    prep.setCurrentHivStatus(parser.getText());
                }

                if (fieldName.equals("dateofcurrenthivstatus")) {
                    String value = parser.getText();
                    if (value != null) prep.setDateOfCurrentHivStatus(LocalDate.parse(value, formatter));
                }

                if (fieldName.equals("pregnancystatus")) {
                    prep.setPregnancyStatus(parser.getText());
                }

                if (fieldName.equals("currentstatus")) {
                    prep.setCurrentStatus(parser.getText());
                }

                if (fieldName.equals("dateofcurrentstatus")) {
                    String value = parser.getText();
                    if (value != null) prep.setDateOfCurrentStatus(LocalDate.parse(value, formatter));
                }
                try {
                    String UniqueNo = personUuid + "-" + datimId + "-" + PERIOD;
                    prep.setPeriod(PERIOD);
                    prep.setPrepUniqueNo(UniqueNo);
                    prep.setSubmissionTime(LocalDate.now() + " @" + LocalTime.now());
                    Optional<CentralPrep> centralPrepOptional = prepRepository.getCentralPrepByPrepUniqueNo(UniqueNo);
                    if (centralPrepOptional.isPresent()) {
                        prep.setId(centralPrepOptional.get().getId());
                        //continue;
                    } else prep.setId(null);
                } catch (Exception e) {
                }
            } catch (Exception e) {
                //creatRadetUploadIssueTracker
                creatRadetUploadIssueTracker(datimId, (LocalDate.now() + " @" + LocalTime.now()));// to be done later;
            }
        }
    }

    public String authorize(RemoteAccessToken remoteAccessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        log.info("remoteAccessToken is {}", remoteAccessToken);
        String url = remoteAccessToken.getUrl() + "/api/v1/authenticate";
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername(remoteAccessToken.getUsername());
        loginVM.setPassword(remoteAccessToken.getPassword());
        try {
            String connect = new HttpConnectionManager().post(loginVM, null, url);

            //For serializing the date on the sync queue
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            ExportController.JWTToken jwtToken = objectMapper.readValue(connect, ExportController.JWTToken.class);

            if (jwtToken.getIdToken() != null) {
                String token = "Bearer " + jwtToken.getIdToken().replace("'", "");
                log.info("token is {}", token);
                //saving the remote access token after authentication
                saveRemoteAccessToken(remoteAccessToken);
                return token;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private RemoteAccessToken saveRemoteAccessToken(RemoteAccessToken remoteAccessToken){
        return accessTokenRepository.save(remoteAccessToken);
    }

    public List<RemoteUrlDTO> getRemoteUrls() {
        List<RemoteAccessToken> remoteAccessTokens = accessTokenRepository.findAll();

        List<RemoteUrlDTO> remoteUrlDTOS = new ArrayList<>();
        remoteAccessTokens.forEach(remoteAccessToken -> {
            RemoteUrlDTO remoteUrlDTO = new RemoteUrlDTO();
            remoteUrlDTO.setId(remoteAccessToken.getId());
            remoteUrlDTO.setUrl(remoteAccessToken.getUrl());
            remoteUrlDTO.setUsername(remoteAccessToken.getUsername());
            remoteUrlDTOS.add(remoteUrlDTO);
        });
        return remoteUrlDTOS;
    }

    public void deleteSyncHistory(Long id){
        SyncHistory syncHistory = syncHistoryRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException(SyncHistory.class, "id", String.valueOf(id)));
        syncHistoryRepository.delete(syncHistory);
    }

    public void deleteRemoteAccessToken(Long id){
        RemoteAccessToken remoteAccessToken = remoteAccessTokenRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException(RemoteAccessToken.class, "id", String.valueOf(id)));
        remoteAccessTokenRepository.delete(remoteAccessToken);
    }

}
