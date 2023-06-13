package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.service.UserService;
import org.lamisplus.modules.central.domain.dto.*;
import org.lamisplus.modules.central.repository.RadetUploadTrackersRepository;
import org.lamisplus.modules.central.repository.ReportRepository;
import org.lamisplus.modules.central.utility.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {
    private final ReportRepository repository;
    private final QuarterUtility quarterUtility;
    private final FileUtility fileUtility;

    private final SyncHistoryService syncHistoryService;

    private final RadetUploadTrackersRepository radetUploadTrackersRepository;

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

    private final DateUtility dateUtility;
    private final UserService userService;
//
//    private static final String STATE = "state";
//    private static final String LGA = "lga";
//    private static final String FACILITY_NAME = "facilityName";
//    private static final String FACILITY = "facility";
//    private static final String DATIM_ID = "datimId";
//    private static final String PERSON_UUID = "personUuid";
//    private static final String HOSPITAL_NUMBER = "hospitalNumber";
//    private static final String DATIM_CODE = "datimCode";
//    private static final String CLIENT_CODE = "clientCode";
//    private static final String PATIENT_ID = "patientId";
    private static final String FIRST_NAME = "firstname";
    private static final String SURNAME = "surname";
    private static final String OTHER_NAME = "othername";
    private static final String SEX = "sex";
    private static final String AGE = "age";
    private static final String DATE_OF_BIRTH = "dateofbirth";
    private static final String PHONE_NUMBER = "phonenumber";
    private static final String MARITAL_STATUS = "maritalstatus";
    private static final String LGA_OF_RESIDENCE = "lgaofresidence";
    private static final String STATE_OF_RESIDENCE = "stateofresidence";
    private static final String EDUCATION = "education";
    private static final String OCCUPATION = "occupation";
    private static final String HTS_LATITUDE = "htslatitude";
    private static final String HTS_LONGITUDE = "htslongitude";
    private static final String CLIENT_ADDRESS = "clientaddress";
    private static final String DATE_VISIT = "datevisit";
    private static final String FIRST_TIME_VISIT = "firsttimevisit";
    private static final String NUMBER_OF_CHILDREN = "numberofchildren";
    private static final String NUMBER_OF_WIVES = "numberofwives";
    private static final String INDEX_CLIENT = "indexclient";
    private static final String PREP_OFFERED = "prepoffered";
    private static final String PREP_ACCEPTED = "prepaccepted";
    private static final String PREVIOUSLY_TESTED = "previouslytested";
    private static final String TARGET_GROUP = "targetgroup";
    private static final String REFERRED_FROM = "referredfrom";
    private static final String TESTING_SETTING = "testingsetting";
    private static final String COUNSELING_TYPE = "counselingtype";
    private static final String PREGNANCY_STATUS = "pregnacystatus";
    private static final String BREASTFEEDING = "breastfeeding";
    private static final String INDEX_TYPE = "indextype";
    private static final String IF_RECENCY_TESTING_OPT_IN = "ifrecencytestingoptin";
    private static final String RECENCY_ID = "recencyid";
    private static final String RECENCY_TEST_TYPE = "recencytesttype";
    private static final String RECENCY_TEST_DATE = "recencytestdate";
    private static final String RECENCY_INTERPRETATION = "recencyinterpretation";
    private static final String FINAL_RECENCY_RESULT = "finalrecencyresult";
    private static final String VIRAL_LOAD_RESULT = "viralloadresult";
    private static final String VIRAL_LOAD_SAMPLE_COLLECTION_DATE = "viralloadsamplecollectiondate";
    private static final String VIRAL_LOAD_CONFIRMATION_RESULT = "viralloadconfirmationresult";
    private static final String VIRAL_LOAD_CONFIRMATION_DATE = "viralloadconfirmationdate";
    private static final String ASSESSMENT_CODE = "assessmentcode";
    private static final String MODALITY = "modality";
    private static final String SYPHILIS_TEST_RESULT = "syphilistestresult";
    private static final String HEPATITIS_B_TEST_RESULT = "hepatitisbtestresult";
    private static final String HEPATITIS_C_TEST_RESULT = "hepatitisctestresult";
    private static final String CD4_TYPE = "cd4type";
    private static final String CD4_TEST_RESULT = "cd4testresult";
    private static final String HIV_TEST_RESULT = "hivtestresult";
    private static final String FINAL_HIV_TEST_RESULT = "finalhivtestresult";
    private static final String DATE_OF_HIV_TESTING = "dateofhivtesting";
    private static final String NUMBER_OF_CONDOMS_GIVEN = "numberofcondomsgiven";
    private static final String NUMBER_OF_LUBRICANTS_GIVEN = "numberoflubricantsgiven";

    //PREP variables
    private static final String HIV_ENROLLMENT_DATE = "hivenrollmentdate";
    private static final String DATE_OF_REGISTRATION = "dateofregistration";
    private static final String RESIDENTIAL_STATE = "residentialstate";
    private static final String RESIDENTIAL_LGA = "residentiallga";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String BASELINE_REGIMEN = "baselineregimen";
    private static final String BASELINE_SYSTOLIC_BP = "baselinesystolicbp";
    private static final String BASELINE_DIASTOLIC_BP = "baselinediastolicbp";
    private static final String BASELINE_T_WEIGHT = "baselinetweight";
    private static final String BASELINE_HEIGHT = "baselineheight";
    private static final String PREP_COMMENCEMENT_DATE = "prepcommencementdate";
    private static final String BASELINE_URINALYSIS = "baselineurinalysis";
    private static final String BASELINE_URINALYSIS_DATE = "baselineurinalysisdate";
    private static final String BASELINE_CREATININE = "baselinecreatinine";
    private static final String BASELINE_HEPATITIS_B = "baselinehepatitisb";
    private static final String BASELINE_HEPATITIS_C = "baselinehepatitisc";
    private static final String INTERRUPTION_REASON = "interruptionreason";
    private static final String INTERRUPTION_DATE = "interruptiondate";
    private static final String HIV_STATUS_AT_PREP_INITIATION = "hivstatusatprepinitiation";
    private static final String INDICATION_FOR_PREP = "indicationforprep";
    private static final String CURRENT_REGIMEN = "currentregimen";
    private static final String DATE_OF_LAST_PICKUP = "dateoflastpickup";
    private static final String CURRENT_SYSTOLIC_BP = "currentsystolicbp";
    private static final String CURRENT_DIASTOLIC_BP = "currentdiastolicbp";
    private static final String CURRENT_WEIGHT = "currentweight";
    private static final String CURRENT_HEIGHT = "currentheight";
    private static final String CURRENT_URINALYSIS = "currenturinalysis";
    private static final String CURRENT_URINALYSIS_DATE = "currenturinalysisdate";
    private static final String CURRENT_HIV_STATUS = "currenthivstatus";
    private static final String DATE_OF_CURRENT_HIV_STATUS = "dateofcurrenthivstatus";
    private static final String CURRENT_STATUS = "currentstatus";
    private static final String DATE_OF_CURRENT_STATUS = "dateofcurrentstatus";





    @Override
    public String bulkExport(Long facilityId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
        LocalDate reportStartDate = LocalDate.parse("1980-01-01", formatter);
        LocalDate reportEndDate = LocalDate.now();
        String zipFileName = "None";
        try {
            log.info("Initializing data export");
            Set<String> fileList = fileUtility.listFilesUsingDirectoryStream(ConstantUtility.TEMP_BATCH_DIR);
            cleanDirectory(fileList);
            log.info("Extracting RADET data to JSON");
            boolean radetIsProcessed = radetExport(facilityId, reportStartDate, reportEndDate);
            log.info("Extracting HTS data to JSON");
            boolean htsIsProcessed = htsExport(facilityId, reportStartDate, reportEndDate);
            log.info("Extracting PrEP data to JSON");
            boolean prepIsProcessed = prepExport(facilityId, reportStartDate, reportEndDate);

            if (radetIsProcessed || htsIsProcessed || prepIsProcessed) {
                log.info("Writing all exports to a zip file");
                Date date1 = new Date();
                String datimCode = getDatimId(facilityId);
                zipFileName = datimCode+"_" + ConstantUtility.DATE_FORMAT.format(date1) + ".zip";
                File file = new File(ConstantUtility.TEMP_BATCH_DIR);
                fileUtility.zipDirectory(file, ConstantUtility.TEMP_BATCH_DIR + zipFileName);

                //update synchistory
                int fileSize = (int) fileUtility.getFileSize(ConstantUtility.TEMP_BATCH_DIR + zipFileName);
                SyncHistoryRequest request = new SyncHistoryRequest(facilityId, zipFileName, fileSize);
                SyncHistoryResponse syncResponse = syncHistoryService.saveSyncHistory(request);
                cleanDirectory(fileList);
                if (syncResponse != null) {
                    log.info("Sync history updated successfully.");
                }
                log.info("Data export completed");
            } else {
                zipFileName = "NO_RECORD";
            }

        } catch (Exception e) {
            log.debug("Something went wrong. Error: {}", e.getMessage());
        }

        return zipFileName;
    }

    private void cleanDirectory(Set<String> fileList) {
       try {
           for (String fileName : fileList) {
               if (!fileName.contains(".zip")) {
                   String strFile = ConstantUtility.TEMP_BATCH_DIR + fileName;
                   Files.deleteIfExists(Paths.get(strFile));
               }
           }
       } catch (Exception e) {
           log.info(e.getMessage());
       }
    }

    @Override
    public boolean radetExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate) {
        boolean isProcessed = false;
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = ConstantUtility.TEMP_BATCH_DIR + ConstantUtility.RADET_FILENAME;
            LocalDate previousQuarterEnd = quarterUtility.getPreviousQuarter(reportEndDate).getEndDate();
            LocalDate previousPreviousQuarterEnd = quarterUtility.getPreviousQuarter(previousQuarterEnd).getEndDate();
            List<RadetReportDto> radetList = repository.getRadetData(facilityId, reportStartDate, reportEndDate.plusDays(1),
                    previousQuarterEnd, previousPreviousQuarterEnd);
            System.out.println("Total Radet Generated "+ radetList.size());
            if (!radetList.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildRadetJson(jsonGenerator, radetList);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (IOException e) {
                    isProcessed = false;
                    log.error("Error writing RADET to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Error mapping RADET: {}", e.getMessage());
        }

        return false;
    }

    @Override
    public boolean htsExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate) {
        boolean isProcessed = false;
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = ConstantUtility.TEMP_BATCH_DIR + ConstantUtility.HTS_FILENAME;
            LocalDate previousQuarterEnd = quarterUtility.getPreviousQuarter(reportEndDate).getEndDate();
            LocalDate previousPreviousQuarterEnd = quarterUtility.getPreviousQuarter(previousQuarterEnd).getEndDate();
            List<HtsReportDto> htsList = repository.getHtsReport(facilityId, reportStartDate, reportEndDate);
            System.out.println("Total HTS Generated "+ htsList.size());
            if (!htsList.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildHtsJson(jsonGenerator, htsList);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (IOException e) {
                    isProcessed = false;
                    log.error("Error writing HTS to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Error mapping HTS: {}", e.getMessage());
        }

        return isProcessed;
    }

    @Override
    public boolean prepExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate) {
        boolean isProcessed = false;
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = ConstantUtility.TEMP_BATCH_DIR + ConstantUtility.PREP_FILENAME;
            List<PrepReportDto> prepList = repository.getPrepReport(facilityId, reportStartDate, reportEndDate);
            System.out.println("Total Prep Generated "+ prepList.size());
            if (!prepList.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildPrepJson(jsonGenerator, prepList);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (IOException e) {
                    isProcessed = false;
                    log.error("Error writing Prep to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Error mapping PrEP: {}", e.getMessage());
        }

        return isProcessed;
    }

    private void buildRadetJson(JsonGenerator jsonGenerator, List<RadetReportDto> radetList) throws IOException {
        for (RadetReportDto radet : radetList)
        {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(STATE, radet.getState());
                jsonGenerator.writeStringField(LGA, radet.getLga());
                jsonGenerator.writeStringField(FACILITY_NAME, radet.getFacilityName());
                jsonGenerator.writeStringField(DATIM_ID, radet.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, radet.getPersonUuid());
                jsonGenerator.writeStringField(HOSPITAL_NUMBER, radet.getHospitalNumber());
                if (radet.getDateOfBirth() != null)
                    jsonGenerator.writeStringField("DateOfBirth", radet.getDateOfBirth().toString());
                if (radet.getAge() != null) jsonGenerator.writeStringField("Age", radet.getAge().toString());
                jsonGenerator.writeStringField("Gender", radet.getGender());
                jsonGenerator.writeStringField("TargetGroup", radet.getTargetGroup());
                jsonGenerator.writeStringField("EnrollmentSetting", radet.getEnrollmentSetting());
                if (radet.getArtStartDate() != null)
                    jsonGenerator.writeStringField("ArtStartDate", radet.getArtStartDate().toString());
                jsonGenerator.writeStringField("RegimenAtStart", radet.getRegimenAtStart());
                jsonGenerator.writeStringField("RegimenLineAtStart", radet.getRegimenLineAtStart());
                jsonGenerator.writeStringField("PregnancyStatus", radet.getPregnancyStatus());
                jsonGenerator.writeStringField("CurrentClinicalStage", radet.getCurrentClinicalStage());
                if (radet.getCurrentWeight() != null)
                    jsonGenerator.writeStringField("CurrentWeight", radet.getCurrentWeight().toString());
                jsonGenerator.writeStringField("ViralLoadIndication", radet.getViralLoadIndication());
                if (radet.getDateOfViralLoadSampleCollection() != null)
                    jsonGenerator.writeStringField("DateOfViralLoadSampleCollection", radet.getDateOfViralLoadSampleCollection().toString());
                jsonGenerator.writeStringField("CurrentViralLoad", radet.getCurrentViralLoad());
                System.out.println(1);
                if (radet.getDateOfCurrentViralLoad() != null)
                    jsonGenerator.writeStringField("DateOfCurrentViralLoad", radet.getDateOfCurrentViralLoad().toString());
                System.out.println(2);
                if (radet.getDateOfCurrentViralLoadSample() != null)
                    jsonGenerator.writeStringField("DateOfCurrentViralLoadSample", radet.getDateOfCurrentViralLoadSample().toString());
                jsonGenerator.writeStringField("LastCd4Count", radet.getLastCd4Count());
                if (radet.getDateOfLastCd4Count() != null)
                    jsonGenerator.writeStringField("DateOfLastCd4Count", radet.getDateOfLastCd4Count().toString());
                jsonGenerator.writeStringField("CurrentRegimenLine", radet.getCurrentRegimenLine());
                jsonGenerator.writeStringField("CurrentARTRegimen", radet.getCurrentARTRegimen());
                if (radet.getMonthsOfARVRefill() != null)
                    jsonGenerator.writeStringField("MonthsOfARVRefill", radet.getMonthsOfARVRefill().toString());
                if (radet.getDateOfCurrentViralLoad() != null)
                    jsonGenerator.writeStringField("LastPickupDate", radet.getLastPickupDate().toString());
                if (radet.getNextPickupDate() != null)
                    jsonGenerator.writeStringField("NextPickupDate", radet.getNextPickupDate().toString());
                if (radet.getCurrentStatusDate() != null)
                    jsonGenerator.writeStringField("CurrentStatusDate", radet.getCurrentStatusDate().toString());
                jsonGenerator.writeStringField("getCurrentStatus", radet.getCurrentStatus());
                if (radet.getPreviousStatusDate() != null)
                    jsonGenerator.writeStringField("PreviousStatusDate", radet.getPreviousStatusDate().toString());
                jsonGenerator.writeStringField("PreviousStatus", radet.getPreviousStatus());
                if (radet.getDateBiometricsEnrolled() != null)
                    jsonGenerator.writeStringField("DateBiometricsEnrolled", radet.getDateBiometricsEnrolled().toString());
                if (radet.getNumberOfFingersCaptured() != null)
                    jsonGenerator.writeStringField("NumberOfFingersCaptured", radet.getNumberOfFingersCaptured().toString());
                if (radet.getDateOfCommencementOfEAC() != null)
                    jsonGenerator.writeStringField("DateOfCommencementOfEAC", radet.getDateOfCommencementOfEAC().toString());
                if (radet.getNumberOfEACSessionCompleted() != null)
                    jsonGenerator.writeStringField("NumberOfEACSessionCompleted", radet.getNumberOfEACSessionCompleted().toString());
                if (radet.getDateOfLastEACSessionCompleted() != null)
                    jsonGenerator.writeStringField("DateOfLastEACSessionCompleted", radet.getDateOfLastEACSessionCompleted().toString());
                if (radet.getDateOfExtendEACCompletion() != null)
                    jsonGenerator.writeStringField("DateOfExtendEACCompletion", radet.getDateOfExtendEACCompletion().toString());
                if (radet.getDateOfRepeatViralLoadResult() != null)
                    jsonGenerator.writeStringField("DateOfRepeatViralLoadResult", radet.getDateOfRepeatViralLoadResult().toString());
                if (radet.getDateOfRepeatViralLoadEACSampleCollection() != null)
                    jsonGenerator.writeStringField("DateOfRepeatViralLoadEACSampleCollection", radet.getDateOfRepeatViralLoadEACSampleCollection().toString());
                jsonGenerator.writeStringField("RepeatViralLoadResult", radet.getRepeatViralLoadResult());
                jsonGenerator.writeStringField("TbStatus", radet.getTbStatus());
                if (radet.getDateOfTbScreened() != null)
                    jsonGenerator.writeStringField("DateOfTbScreened", radet.getDateOfTbScreened().toString());
                if (radet.getDateOfCurrentRegimen() != null)
                    jsonGenerator.writeStringField("DateOfCurrentRegimen", radet.getDateOfCurrentRegimen().toString());
                if (radet.getDateOfIptStart() != null)
                    jsonGenerator.writeStringField("DateOfIptStart", radet.getDateOfIptStart().toString());
                if (radet.getIptCompletionDate() != null)
                    jsonGenerator.writeStringField("IptCompletionDate", radet.getIptCompletionDate().toString());
                jsonGenerator.writeStringField("IptType", radet.getIptType());
                jsonGenerator.writeStringField("ResultOfCervicalCancerScreening", radet.getResultOfCervicalCancerScreening());
                jsonGenerator.writeStringField("CervicalCancerScreeningType", radet.getCervicalCancerScreeningType());
                jsonGenerator.writeStringField("CervicalCancerScreeningMethod", radet.getCervicalCancerScreeningMethod());
                jsonGenerator.writeStringField("CervicalCancerTreatmentScreened", radet.getCervicalCancerTreatmentScreened());
                if (radet.getDateOfCervicalCancerScreening() != null)
                    jsonGenerator.writeStringField("DateOfCervicalCancerScreening", radet.getDateOfCervicalCancerScreening().toString());
                jsonGenerator.writeStringField("OvcNumber", radet.getOvcNumber());
                jsonGenerator.writeStringField("HouseholdNumber", radet.getHouseholdNumber());
                jsonGenerator.writeStringField("CareEntry", radet.getCareEntry());
                jsonGenerator.writeStringField("CauseOfDeath", radet.getCauseOfDeath());
                jsonGenerator.writeStringField("VlEligibilityStatus", radet.getVlEligibilityStatus() + "");
                if (radet.getDateOfVlEligibilityStatus() != null)
                    jsonGenerator.writeStringField("DateOfVlEligibilityStatu", radet.getDateOfVlEligibilityStatus().toString());
                jsonGenerator.writeStringField("TbDiagnosticTestType", radet.getTbDiagnosticTestType());
                if (radet.getDateOfTbSampleCollection() != null)
                    jsonGenerator.writeStringField("DateOfTbSampleCollection", radet.getDateOfTbSampleCollection().toString());
                jsonGenerator.writeStringField("TbDiagnosticResult", radet.getTbDiagnosticResult());
                jsonGenerator.writeStringField("DsdModel", radet.getDsdModel());
                if (radet.getDateOfTbDiagnosticResultReceived() != null)
                    jsonGenerator.writeStringField("DateOfTbDiagnosticResultReceived", radet.getDateOfTbDiagnosticResultReceived().toString());
                jsonGenerator.writeStringField("TbTreatementType", radet.getTbTreatementType());
                jsonGenerator.writeStringField("TbTreatmentOutcome", radet.getTbTreatmentOutcome());
                if (radet.getTbTreatmentStartDate() != null)
                    jsonGenerator.writeStringField("TbTreatmentStartDate", radet.getTbTreatmentStartDate().toString());
                if (radet.getTbCompletionDate() != null)
                    jsonGenerator.writeStringField("TbCompletionDate", radet.getTbCompletionDate().toString());
                jsonGenerator.writeStringField("IptCompletionStatus()", radet.getIptCompletionStatus());

                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating radet JSON: {}", e.getMessage());
            }
        }

    }

    private void buildHtsJson(JsonGenerator jsonGenerator,  List<HtsReportDto> htsList) throws IOException {
        for (HtsReportDto hts : htsList) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(STATE, hts.getState());
                jsonGenerator.writeStringField(LGA, hts.getLga());
                jsonGenerator.writeStringField(FACILITY, hts.getFacility());
                jsonGenerator.writeStringField(DATIM_CODE, hts.getDatimCode());
                jsonGenerator.writeStringField(PATIENT_ID, hts.getPatientId());
                jsonGenerator.writeStringField(CLIENT_CODE, hts.getClientCode());
                jsonGenerator.writeStringField(FIRST_NAME, hts.getFirstName());
                jsonGenerator.writeStringField(SURNAME, hts.getSurname());
                jsonGenerator.writeStringField(OTHER_NAME, hts.getOtherName());
                jsonGenerator.writeStringField(SEX, hts.getSex());
                jsonGenerator.writeStringField(AGE, String.valueOf(hts.getAge()));
                jsonGenerator.writeStringField(DATE_OF_BIRTH, dateUtility.ConvertDateToString(hts.getDateOfBirth()));
                jsonGenerator.writeStringField(PHONE_NUMBER, hts.getPhoneNumber());
                jsonGenerator.writeStringField(MARITAL_STATUS, hts.getMaritalStatus());
                jsonGenerator.writeStringField(LGA_OF_RESIDENCE, hts.getLgaOfResidence());
                jsonGenerator.writeStringField(STATE_OF_RESIDENCE, hts.getStateOfResidence());
                jsonGenerator.writeStringField(EDUCATION, hts.getEducation());
                jsonGenerator.writeStringField(OCCUPATION, hts.getOccupation());
                jsonGenerator.writeStringField(HTS_LATITUDE, hts.getHtsLatitude());
                jsonGenerator.writeStringField(HTS_LONGITUDE, hts.getHtsLongitude());
                jsonGenerator.writeStringField(CLIENT_ADDRESS, hts.getClientAddress());
                jsonGenerator.writeStringField(DATE_VISIT, dateUtility.ConvertDateToString(hts.getDateVisit()));
                jsonGenerator.writeStringField(FIRST_TIME_VISIT, hts.getFirstTimeVisit());
                jsonGenerator.writeStringField(NUMBER_OF_CHILDREN, String.valueOf(hts.getNumberOfChildren()));
                jsonGenerator.writeStringField(NUMBER_OF_WIVES, String.valueOf(hts.getNumberOfWives()));
                jsonGenerator.writeStringField(INDEX_CLIENT, hts.getIndexClient());
                jsonGenerator.writeStringField(PREP_OFFERED, hts.getPrepOffered());
                jsonGenerator.writeStringField(PREP_ACCEPTED, hts.getPrepAccepted());
                jsonGenerator.writeStringField(PREVIOUSLY_TESTED, hts.getPreviouslyTested());
                jsonGenerator.writeStringField(TARGET_GROUP, hts.getTargetGroup());
                jsonGenerator.writeStringField(REFERRED_FROM, hts.getReferredFrom());
                jsonGenerator.writeStringField(TESTING_SETTING, hts.getTestingSetting());
                jsonGenerator.writeStringField(COUNSELING_TYPE, hts.getCounselingType());
                jsonGenerator.writeStringField(PREGNANCY_STATUS, hts.getPregnancyStatus());
                jsonGenerator.writeStringField(BREASTFEEDING, hts.getBreastFeeding());
                jsonGenerator.writeStringField(INDEX_TYPE, hts.getIndexType());
                jsonGenerator.writeStringField(IF_RECENCY_TESTING_OPT_IN, hts.getIfRecencyTestingOptIn());
                jsonGenerator.writeStringField(RECENCY_ID, hts.getRecencyId());
                jsonGenerator.writeStringField(RECENCY_TEST_TYPE, hts.getRecencyTestType());
                jsonGenerator.writeStringField(RECENCY_TEST_DATE, hts.getRecencyTestDate());
                jsonGenerator.writeStringField(RECENCY_INTERPRETATION, hts.getRecencyInterpretation());
                jsonGenerator.writeStringField(FINAL_RECENCY_RESULT, hts.getFinalRecencyResult());
                jsonGenerator.writeStringField(VIRAL_LOAD_RESULT, hts.getViralLoadResult());
                jsonGenerator.writeStringField(VIRAL_LOAD_SAMPLE_COLLECTION_DATE, dateUtility.ConvertDateToString(hts.getViralLoadSampleCollectionDate()));
                jsonGenerator.writeStringField(VIRAL_LOAD_CONFIRMATION_RESULT, hts.getViralLoadConfirmationResult());
                jsonGenerator.writeStringField(VIRAL_LOAD_CONFIRMATION_DATE, dateUtility.ConvertDateToString(hts.getViralLoadConfirmationDate()));
                jsonGenerator.writeStringField(ASSESSMENT_CODE, hts.getAssessmentCode());
                jsonGenerator.writeStringField(MODALITY, hts.getModality());
                jsonGenerator.writeStringField(SYPHILIS_TEST_RESULT, hts.getSyphilisTestResult());
                jsonGenerator.writeStringField(HEPATITIS_B_TEST_RESULT, hts.getHepatitisBTestResult());
                jsonGenerator.writeStringField(HEPATITIS_C_TEST_RESULT, hts.getHepatitisCTestResult());
                jsonGenerator.writeStringField(CD4_TYPE, hts.getCd4Type());
                jsonGenerator.writeStringField(CD4_TEST_RESULT, hts.getCd4TestResult());
                jsonGenerator.writeStringField(HIV_TEST_RESULT, hts.getHivTestResult());
                jsonGenerator.writeStringField(FINAL_HIV_TEST_RESULT, hts.getFinalHivTestResult());
                jsonGenerator.writeStringField(DATE_OF_HIV_TESTING, dateUtility.ConvertDateToString(hts.getDateOfHivTesting()));
                jsonGenerator.writeStringField(NUMBER_OF_CONDOMS_GIVEN, hts.getNumberOfCondomsGiven());
                jsonGenerator.writeStringField(NUMBER_OF_LUBRICANTS_GIVEN, hts.getNumberOfLubricantsGiven());
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating HTS JSON: {}", e.getMessage());
            }
        }

    }

    private void buildPrepJson(JsonGenerator jsonGenerator,  List<PrepReportDto> prepList) throws IOException {
        for (PrepReportDto prep : prepList) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(STATE, prep.getState());
                jsonGenerator.writeStringField(LGA, prep.getLga());
                jsonGenerator.writeStringField(FACILITY_NAME, prep.getFacilityName());
                jsonGenerator.writeStringField(DATIM_ID, prep.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, prep.getPersonUuid());
                jsonGenerator.writeStringField(HOSPITAL_NUMBER, prep.getHospitalNumber());
                jsonGenerator.writeStringField(SURNAME, prep.getSurname());
                jsonGenerator.writeStringField(FIRST_NAME, prep.getFirstName());
                jsonGenerator.writeStringField(HIV_ENROLLMENT_DATE, dateUtility.ConvertDateToString(prep.getHivEnrollmentDate()));
                jsonGenerator.writeStringField(AGE, String.valueOf(prep.getAge()));
                jsonGenerator.writeStringField(OTHER_NAME, prep.getOtherName());
                jsonGenerator.writeStringField(SEX, prep.getSex());
                jsonGenerator.writeStringField(DATE_OF_BIRTH, dateUtility.ConvertDateToString(prep.getDateOfBirth()));
                jsonGenerator.writeStringField(DATE_OF_REGISTRATION, dateUtility.ConvertDateToString(prep.getDateOfRegistration()));
                jsonGenerator.writeStringField(MARITAL_STATUS, prep.getMaritalStatus());
                jsonGenerator.writeStringField(EDUCATION, prep.getEducation());
                jsonGenerator.writeStringField(OCCUPATION, prep.getOccupation());
                jsonGenerator.writeStringField(RESIDENTIAL_STATE, prep.getResidentialState());
                jsonGenerator.writeStringField(RESIDENTIAL_LGA, prep.getResidentialLga());
                //jsonGenerator.writeStringField(ADDRESS, prep.getAddress());
                jsonGenerator.writeStringField(PHONE, prep.getPhone());
                jsonGenerator.writeStringField(BASELINE_REGIMEN, prep.getBaseLineRegimen());
                jsonGenerator.writeStringField(BASELINE_SYSTOLIC_BP, String.valueOf(prep.getBaselineSystolicBp()));
                jsonGenerator.writeStringField(BASELINE_DIASTOLIC_BP, String.valueOf(prep.getBaselineDiastolicBp()));
                jsonGenerator.writeStringField(BASELINE_T_WEIGHT, prep.getBaselineWeight());
                jsonGenerator.writeStringField(BASELINE_HEIGHT, prep.getBaselineHeight());
                jsonGenerator.writeStringField(TARGET_GROUP, prep.getTargetGroup());
                jsonGenerator.writeStringField(PREP_COMMENCEMENT_DATE, dateUtility.ConvertDateToString(prep.getPrepCommencementDate()));
                jsonGenerator.writeStringField(BASELINE_URINALYSIS, prep.getBaseLineUrinalysis());
                jsonGenerator.writeStringField(BASELINE_URINALYSIS_DATE, dateUtility.ConvertDateToString(prep.getBaseLineUrinalysisDate()));
                jsonGenerator.writeStringField(BASELINE_CREATININE, prep.getBaseLineCreatinine());
                jsonGenerator.writeStringField(BASELINE_HEPATITIS_B, prep.getBaseLineHepatitisB());
                jsonGenerator.writeStringField(BASELINE_HEPATITIS_C, prep.getBaseLineHepatitisC());
                jsonGenerator.writeStringField(INTERRUPTION_REASON, prep.getInterruptionReason());
                jsonGenerator.writeStringField(INTERRUPTION_DATE, dateUtility.ConvertDateToString(prep.getInterruptionDate()));
                jsonGenerator.writeStringField(HIV_STATUS_AT_PREP_INITIATION, prep.getHIVStatusAtPrepInitiation());
                jsonGenerator.writeStringField(INDICATION_FOR_PREP, prep.getIndicationForPrep());
                jsonGenerator.writeStringField(CURRENT_REGIMEN, prep.getCurrentRegimen());
                jsonGenerator.writeStringField(DATE_OF_LAST_PICKUP, prep.getDateOfLastPickup());
                jsonGenerator.writeStringField(CURRENT_SYSTOLIC_BP, prep.getCurrentSystolicBp());
                jsonGenerator.writeStringField(CURRENT_DIASTOLIC_BP, prep.getCurrentDiastolicBp());
                jsonGenerator.writeStringField(CURRENT_WEIGHT, prep.getCurrentWeight());
                jsonGenerator.writeStringField(CURRENT_HEIGHT, prep.getCurrentHeight());
                jsonGenerator.writeStringField(CURRENT_URINALYSIS, prep.getCurrentUrinalysis());
                jsonGenerator.writeStringField(CURRENT_URINALYSIS_DATE, dateUtility.ConvertDateToString(prep.getCurrentUrinalysisDate()));
                jsonGenerator.writeStringField(CURRENT_HIV_STATUS, prep.getCurrentStatus());
                jsonGenerator.writeStringField(DATE_OF_CURRENT_HIV_STATUS, prep.getDateOfLastPickup());
                jsonGenerator.writeStringField(PREGNANCY_STATUS, prep.getPregnancyStatus());
                jsonGenerator.writeStringField(CURRENT_STATUS, prep.getCurrentStatus());
                jsonGenerator.writeStringField(DATE_OF_CURRENT_STATUS, dateUtility.ConvertDateToString(prep.getDateOfCurrentStatus()));
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating PrEP JSON: {}", e.getMessage());
            }
        }

    }

    @Override
    public String getDatimId(Long facilityId)
    {
        String datimId = radetUploadTrackersRepository.getDatimCode(facilityId);
        return datimId;

    }

}
