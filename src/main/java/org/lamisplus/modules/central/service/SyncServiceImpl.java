package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.RadetUploaders;
import org.lamisplus.modules.central.domain.entity.CentralRadet;
import org.lamisplus.modules.central.domain.entity.RadetUploadIssueTrackers;
import org.lamisplus.modules.central.domain.entity.RadetUploadTrackers;
import org.lamisplus.modules.central.repository.CentralRadetRepository;
import org.lamisplus.modules.central.repository.RadetUploadIssueTrackersRepository;
import org.lamisplus.modules.central.repository.RadetUploadTrackersRepository;
import org.lamisplus.modules.central.utility.ConstantUtility;
import org.lamisplus.modules.central.utility.FileUtility;
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
    private final RadetUploadIssueTrackersRepository radetUploadIssueTrackersRepository;
    //private final CentralDataElementRepository centralDataElementRepository;
    private final RadetUploadTrackersRepository radetUploadTrackersRepository;
    // private final HtsRepository htsRepository;
    //private final CentralPrepRepository prepRepository;
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
                try{
                    byte[] data = multipartFile.getBytes();
                    bos.write(data);
                    bos.close();
                    Path target = Paths.get(ConstantUtility.TEMP_SERVER_DIR);
                    Path source = Paths.get(sourceFileName);
                    fileUtility.unzipFile(source, target);
                    log.info("We got here");

                }catch(Exception e){e.printStackTrace();}


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
    public String getDatimId(Long facilityId)
    {
        String datimId = radetUploadTrackersRepository.getDatimCode(facilityId);
        return datimId;

    }

    @Override
    public void importRadet(String jsonFilePath,  String datimId) throws IOException {

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

//    @Override
//    public void importHts(String jsonFilePath) throws IOException {
//        try (JsonParser parser = new JsonFactory().createParser(new FileReader(jsonFilePath))) {
//            List<HtsReport> htsReportList = new ArrayList<>();
//            if (parser.nextToken() == JsonToken.START_ARRAY) {
//                while (parser.nextToken() != JsonToken.END_ARRAY) {
//                    if (parser.currentToken() != JsonToken.START_OBJECT) {
//                        throw new RuntimeException("Expected content to be a  object");
//                    }
//                    HtsReport hts = new HtsReport();
//                    mapHts(hts, parser);
//                    htsReportList.add(hts);
//                }
//            }
//            htsRepository.saveAll(htsReportList);
//        } catch (Exception e) {
//            log.error("Error importing HTS data: {}", e.getMessage());
//            e.printStackTrace();
//        }
//    }

    //   @Override
//    public void importPrep(String jsonFilePath) throws IOException {
//        try (JsonParser parser = new JsonFactory().createParser(new FileReader(jsonFilePath))) {
//            List<PrepReport> prepList = new ArrayList<>();
//            if (parser.nextToken() == JsonToken.START_ARRAY) {
//                while (parser.nextToken() != JsonToken.END_ARRAY) {
//                    if (parser.currentToken() != JsonToken.START_OBJECT) {
//                        throw new RuntimeException("Expected content to be an object");
//                    }
//                    PrepReport prepReport = new PrepReport();
//                    mapPrep(prepReport, parser);
//                    prepList.add(prepReport);
//                }
//            }
//            prepRepository.saveAll(prepList);
//        } catch (Exception e) {
//            log.error("Error importing RADET data: {}", e.getMessage());
//            e.printStackTrace();
//        }
//    }

    void mapRadet(CentralRadet radet, JsonParser parser, String datimId) throws IOException {

        String personUuid = "";
        String period = "2023Q2";

        while (parser.nextToken() != JsonToken.END_OBJECT)
        {
            try
            {
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
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfBirth(date2);
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
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setArtStartDate(date2);
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
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfViralLoadSampleCollection(date2);
            }
            if (fieldName.equals("CurrentViralLoad")) {
                radet.setCurrentViralLoad(parser.getText());
            }
            System.out.println(1);
            if (fieldName.equals("DateOfCurrentViralLoad")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfCurrentViralLoad(date2);
            }
            System.out.println(2);
            if (fieldName.equals("DateOfCurrentViralLoadSample")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfCurrentViralLoadSample(date2);
            }
            if (fieldName.equals("LastCd4Count")) {
                radet.setLastCd4Count(parser.getText());
            }
            if (fieldName.equals("DateOfLastCd4Count")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfLastCd4Count(date2);
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
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setLastPickupDate(date2);
            }
            if (fieldName.equals("NextPickupDate")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setNextPickupDate(date2);
            }
            if (fieldName.equals("CurrentStatusDate")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setCurrentStatusDate(date2);
            }
            if (fieldName.equals("setCurrentStatus")) {
                radet.setCurrentStatus(parser.getText());
            }
            if (fieldName.equals("PreviousStatusDate")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setPreviousStatusDate(date2);
            }
            if (fieldName.equals("PreviousStatus")) {
                radet.setPreviousStatus(parser.getText());
            }
            if (fieldName.equals("DateBiometricsEnrolled")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateBiometricsEnrolled(date2);
            }
            if (fieldName.equals("NumberOfFingersCaptured")) {
                String value = parser.getText();
                if (value != null) radet.setNumberOfFingersCaptured(Double.valueOf(value));
            }
            if (fieldName.equals("DateOfCommencementOfEAC")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfCommencementOfEac(date2);
            }
            if (fieldName.equals("NumberOfEACSessionCompleted")) {
                String value = parser.getText();
                if (value != null) radet.setNumberOfEacSessionCompleted(Double.valueOf(value));
            }
            if (fieldName.equals("DateOfLastEACSessionCompleted")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfLastEacSessionCompleted(date2);
            }
            if (fieldName.equals("DateOfExtendEACCompletion")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfExtendEacCompletion(date2);
            }
            if (fieldName.equals("DateOfRepeatViralLoadResult")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfRepeatViralLoadResult(date2);
            }
            if (fieldName.equals("DateOfRepeatViralLoadEACSampleCollection")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfViralLoadSampleCollection(date2);
            }
            if (fieldName.equals("RepeatViralLoadResult")) {
                radet.setRepeatViralLoadResult(parser.getText());
            }
            if (fieldName.equals("TbStatus")) {
                radet.setTbStatus(parser.getText());
            }
            if (fieldName.equals("DateOfTbScreened")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfTbScreened(date2);
            }
            if (fieldName.equals("DateOfCurrentRegimen")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfCurrentRegimen(date2);
            }
            if (fieldName.equals("DateOfIptStart")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfIptStart(date2);
            }
            if (fieldName.equals("IptCompletionDate")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setIptCompletionDate(date2);
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
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfCervicalCancerScreening(date2);
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
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfVlEligibilityStatus(date2);
            }
            if (fieldName.equals("TbDiagnosticTestType")) {
                radet.setTbDiagnosticTestType(parser.getText());
            }
            if (fieldName.equals("DateOfTbSampleCollection")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfTbSampleCollection(date2);
            }
            if (fieldName.equals("TbDiagnosticResult")) {
                radet.setTbDiagnosticResult(parser.getText());
            }
            if (fieldName.equals("DsdModel")) {
                radet.setDsdModel(parser.getText());
            }
            if (fieldName.equals("DateOfTbDiagnosticResultReceived")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setDateOfTbDiagnosticResultReceived(date2);
            }
            if (fieldName.equals("TbTreatementType")) {
                radet.setTbTreatementType(parser.getText());
            }
            if (fieldName.equals("TbTreatmentOutcome")) {
                radet.setTbTreatmentOutcome(parser.getText());
            }
            if (fieldName.equals("TbTreatmentStartDate")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setTbTreatmentStartDate(date2);
            }
            if (fieldName.equals("TbCompletionDate")) {
                String date = parser.getText();
                LocalDate date2 = LocalDate.parse(date, formatter);
                if (date != null) radet.setTbCompletionDate(date2);
            }
            if (fieldName.equals("IptCompletionStatus()")) {
                radet.setIptCompletionStatus(parser.getText());
            }
            try {
                String radetUniqueNo = personUuid + "-" + datimId;
                radet.setPeriod(period);
                radet.setRadetUniqueNo(radetUniqueNo);
                radet.setSubmissionTime(LocalDate.now() + " @" + LocalTime.now());
                Optional<CentralRadet> centralRadetOptional = radetRepository.getCentralRadetByRadetUniqueNo(radetUniqueNo);
                if (centralRadetOptional.isPresent()) {
                    radet.setId(centralRadetOptional.get().getId());
                    //continue;
                } else radet.setId(null);
            } catch (Exception e) {  }
            } catch (Exception e) {
                creatRadetUploadIssueTracker(datimId, (LocalDate.now() + " @" + LocalTime.now()));// to be done later;
            }
        }

        }
        //    private void mapHts(HtsReport hts, JsonParser parser) throws IOException {
//        while (parser.nextToken() != JsonToken.END_OBJECT) {
//            String fieldName = parser.getCurrentName();
//            parser.nextToken();
//            if (fieldName.equals(STATE)) {
//                hts.setState(parser.getText());
//            }
//            if (fieldName.equals(LGA)) {
//                hts.setLga(parser.getText());
//            }
//            if (fieldName.equals(FACILITY)) {
//                hts.setFacility(parser.getText());
//            }
//            if (fieldName.equals(DATIM_CODE)) {
//                hts.setDatimCode(parser.getText());
//            }
//            if (fieldName.equals(PATIENT_ID)) {
//                hts.setPatientId(parser.getText());
//            }
//            if (fieldName.equals(CLIENT_CODE)) {
//                hts.setClientCode(parser.getText());
//            }
//        }
//    }

//    private void mapPrep(PrepReport prep, JsonParser parser) throws IOException {
//        while (parser.nextToken() != JsonToken.END_OBJECT) {
//            String fieldName = parser.getCurrentName();
//            parser.nextToken();
//            if (fieldName.equals(STATE)) {
//                prep.setState(parser.getText());
//            }
//            if (fieldName.equals(LGA)) {
//                prep.setLga(parser.getText());
//            }
//            if (fieldName.equals(FACILITY_NAME)) {
//                prep.setFacilityName(parser.getText());
//            }
//            if (fieldName.equals(DATIM_ID)) {
//                prep.setDatimId(parser.getText());
//            }
//            if (fieldName.equals(HOSPITAL_NUMBER)) {
//                prep.setHospitalNumber(parser.getText());
//            }
//            if (fieldName.equals(PERSON_UUID)) {
//                prep.setPersonUuid(parser.getText());
//            }
//            if (fieldName.equals("firstName")) {
//                prep.setFirstName(parser.getText());
//            }
//
//        }
//    }

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
//            filePath = ConstantUtility.TEMP_SERVER_DIR + ConstantUtility.HTS_FILENAME;
//            if (Files.exists(Paths.get(filePath))) {
//                log.info("Importing HTS file");
//                //  List<HtsReportDto> htsReportDtoList = objectMapper.readValue(new File(filePath), new TypeReference<List<HtsReportDto>>() {});
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



}
