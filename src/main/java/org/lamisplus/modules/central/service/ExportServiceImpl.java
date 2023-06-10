package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.lamisplus.modules.central.domain.dto.*;
import org.lamisplus.modules.central.repository.ReportRepository;
import org.lamisplus.modules.central.utility.ConstantUtility;
import org.lamisplus.modules.central.utility.FileUtility;
import org.lamisplus.modules.central.utility.JsonUtility;
import org.lamisplus.modules.central.utility.QuarterUtility;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


    //    @Override
//    public String bulkExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate) {
//        String zipFileName = "None";
//        try {
//            log.info("Initializing data export");
//            FileUtils.cleanDirectory(new File(ConstantUtility.TEMP_BATCH_DIR));
//            //export files
//            log.info("Extracting RADET data to JSON");
//            boolean radetIsProcessed = radetExport(facilityId, reportStartDate, reportEndDate);
//            log.info("Extracting HTS data to JSON");
//            boolean htsIsProcessed = htsExport(facilityId, reportStartDate, reportEndDate);
//            log.info("Extracting PrEP data to JSON");
//            boolean prepIsProcessed = prepExport(facilityId, reportStartDate, reportEndDate);
//
//            if (radetIsProcessed || htsIsProcessed || prepIsProcessed) {
//                log.info("Writing all exports to a zip file");
//                Date date1 = new Date();
//                zipFileName = "export_" + ConstantUtility.DATE_FORMAT.format(date1) + ".zip";
//                File file = new File(ConstantUtility.TEMP_BATCH_DIR);
//                fileUtility.zipDirectory(file, ConstantUtility.TEMP_BATCH_DIR + zipFileName);
//
//                Set<String> fileList = fileUtility.listFilesUsingDirectoryStream(ConstantUtility.TEMP_BATCH_DIR);
//                for (String fileName : fileList) {
//                    if (!fileName.contains(".zip")) {
//                        String strFile = ConstantUtility.TEMP_BATCH_DIR + fileName;
//                        Files.deleteIfExists(Paths.get(strFile));
//                    }
//                }
//                log.info("Data export completed");
//            }
//
//        } catch (Exception e) {
//            log.debug("Something went wrong. Error: {}", e.getMessage());
//        }
//
//        return zipFileName;
//    }
    @Override
    public String bulkExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate) {
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
                zipFileName = "export_" + ConstantUtility.DATE_FORMAT.format(date1) + ".zip";
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
        for (RadetReportDto radet : radetList) {
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
                if (radet.getDateOfCurrentRegimen() != null)
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

    private void buildHtsJson(JsonGenerator jsonGenerator, List<HtsReportDto> htsList) throws IOException {
        for (HtsReportDto hts : htsList) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(STATE, hts.getState());
                jsonGenerator.writeStringField(LGA, hts.getLga());
                jsonGenerator.writeStringField(FACILITY, hts.getFacility());
                jsonGenerator.writeStringField(DATIM_CODE, hts.getDatimCode());
                jsonGenerator.writeStringField(PATIENT_ID, hts.getPatientId());
                jsonGenerator.writeStringField(CLIENT_CODE, hts.getClientCode());
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating HTS JSON: {}", e.getMessage());
            }
        }

    }

    private void buildPrepJson(JsonGenerator jsonGenerator, List<PrepReportDto> prepList) throws IOException {
        for (PrepReportDto prep : prepList) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(STATE, prep.getState());
                jsonGenerator.writeStringField(LGA, prep.getLga());
                jsonGenerator.writeStringField(FACILITY_NAME, prep.getFacilityName());
                jsonGenerator.writeStringField(DATIM_ID, prep.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, prep.getPersonUuid());
                jsonGenerator.writeStringField(HOSPITAL_NUMBER, prep.getHospitalNumber());
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating PrEP JSON: {}", e.getMessage());
            }
        }

    }

}
