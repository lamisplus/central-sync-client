package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.*;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.repository.RadetUploadTrackersRepository;
import org.lamisplus.modules.central.repository.ReportRepository;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.lamisplus.modules.central.utility.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.lamisplus.modules.central.utility.ConstantUtility.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {
    private final ReportRepository repository;
    private final QuarterUtility quarterUtility;
    private final FileUtility fileUtility;
    private final SyncHistoryService syncHistoryService;
    private final RadetUploadTrackersRepository radetUploadTrackersRepository;
    private final SyncHistoryRepository syncHistoryRepository;
    private final BuildJson buildJson;
    private final SendWebsocketService sendSyncWebsocketService;
    private static String SYNC_ENDPOINT = "topic/sync";
    private static Integer stat;
    private static Long ONE_DAY=1L;
    private static ArrayList ERROR_LOG= new ArrayList<>();
    private final DateUtility dateUtility;
    private final QuarterService quarterService;

    @Override
    public String bulkExport(Long facilityId, Boolean current) {
        if(!ERROR_LOG.isEmpty()) ERROR_LOG.clear();
        Path path = Paths.get(TEMP_BATCH_DIR);
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
        LocalDate reportStartDate = LocalDate.parse("1980-01-01", formatter);
        //LocalDate reportEndDate = LocalDate.now().plusDays(ONE_DAY);

        LocalDate reportEndDate = LocalDate.parse("2023-09-30", formatter);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime reportStartTime = LocalDateTime.parse("1985-01-01 00:00:00", timeFormatter);
        LocalDateTime reportEndTime = LocalDateTime.parse(LocalDateTime.now().format(timeFormatter), timeFormatter).plusDays(ONE_DAY);

        SyncHistory history = syncHistoryRepository.getDateLastSync(facilityId).orElse(null);

        if(!current)history=null;
        if(history != null){
            LocalDateTime lastSync = history.getDateLastSync();
            reportStartTime=LocalDateTime.parse(dateUtility.ConvertDateTimeToString(lastSync), timeFormatter);;
        }

        final String PERIOD = reportEndDate.getYear()+quarterService.getCurrentQuarter(reportEndDate).getName();
        log.info("PERIOD IS {}", PERIOD);

        Date date1 = new Date();
        String zipFileName = "None";
        String fileFolder = DATE_FORMAT.format(date1);
        String folder = TEMP_BATCH_DIR + fileFolder + File.separator;
        Path folderPath = Paths.get(folder);
        Path createdFile = folderPath;
        try {
            createdFile = Files.createDirectories(folderPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            log.info("Initializing data export");
            log.info("Extracting data to JSON");
            syncData(fileFolder, current);
            log.info("Extracting Extract data to JSON");
            boolean radetIsProcessed = extractExport(facilityId, reportStartDate, reportEndDate, PERIOD, fileFolder);
            log.info("Extracting HTS data to JSON");
            boolean htsIsProcessed = htsExport(facilityId, reportStartDate, reportEndDate, PERIOD, fileFolder);
            log.info("Extracting PrEP data to JSON");
            boolean prepIsProcessed = prepExport(facilityId, reportStartDate, reportEndDate, PERIOD, fileFolder);
            log.info("Extracting Clinic data to JSON");
            boolean clinicIsProcessed = clinicExport(facilityId, reportStartTime, reportEndTime, fileFolder);
            log.info("Extracting Patient data to JSON");
            boolean patientIsProcessed = patientExport(facilityId, reportStartTime, reportEndTime, fileFolder);
            log.info("Extracting laboratoryOrder data to JSON");
            boolean laboratoryOrderIsProcessed = laboratoryOrderExport(facilityId, reportStartTime, reportEndTime, fileFolder);
            log.info("Extracting laboratorySample data to JSON");
            boolean laboratorySampleIsProcessed = laboratorySampleExport(facilityId, reportStartTime, reportEndTime, fileFolder);
            log.info("Extracting laboratoryTest data to JSON");
            boolean laboratoryTestIsProcessed = laboratoryTestExport(facilityId, reportStartTime, reportEndTime, fileFolder);
            log.info("Extracting laboratoryResult data to JSON");
            boolean laboratoryResultIsProcessed = laboratoryResultExport(facilityId, reportStartTime, reportEndTime, fileFolder);
            log.info("Extracting Pharmacy data to JSON");
            boolean pharmacyIsProcessed = pharmacyExport(facilityId, reportStartTime, reportEndTime, fileFolder);
            log.info("Extracting biometric data to JSON");
            boolean biometricIsProcessed = biometricExport(facilityId, reportStartTime, reportEndTime, fileFolder);
            log.info("Extracting enrollment data to JSON");
            boolean enrollmentIsProcessed = enrollmentExport(facilityId, reportStartTime, reportEndTime, fileFolder);
            log.info("Extracting observation data to JSON");
            boolean observationIsProcessed = observationExport(facilityId, reportStartTime, reportEndTime, fileFolder);
            log.info("Extracting status tracker data to JSON");
            boolean statusTrackerIsProcessed = statusTrackerExport(facilityId, reportStartTime, reportEndTime, fileFolder);
            log.info("Extracting eac data to JSON");
            boolean eacIsProcessed = eacExport(facilityId, reportStartTime, reportEndTime, fileFolder);

            if (radetIsProcessed || htsIsProcessed || prepIsProcessed
                    || clinicIsProcessed || patientIsProcessed || laboratoryOrderIsProcessed
                    || laboratorySampleIsProcessed || laboratoryTestIsProcessed || eacIsProcessed
                    || laboratoryResultIsProcessed || pharmacyIsProcessed || biometricIsProcessed
                    || enrollmentIsProcessed || observationIsProcessed || statusTrackerIsProcessed) {
                //log.info("Writing all exports to a zip file");
                String datimCode = getDatimId(facilityId);
                //log.info("datimCode {}", datimCode);
                zipFileName = datimCode+"_" + fileFolder + ".zip";
                //log.info("zipFileName {}", zipFileName);

                String fullPath = createdFile + File.separator + zipFileName;
                //log.info("fullPath {}", fullPath);
                File dir = new File(createdFile + File.separator);
                //log.info("dir {}", dir.getPath());
                fileUtility.zipDirectory(dir, fullPath, fileFolder);
                //log.info("zipFileName {}", zipFileName);
                //update synchistory
                int fileSize = (int) fileUtility.getFileSize(fullPath);
                SyncHistoryRequest request = new SyncHistoryRequest(facilityId, zipFileName, fileSize, (ERROR_LOG.isEmpty()) ? null : ERROR_LOG, folder);
                SyncHistoryResponse syncResponse = syncHistoryService.saveSyncHistory(request);

                //cleanDirectory(fileList);
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
                   String strFile = TEMP_BATCH_DIR + fileName;
                   Files.deleteIfExists(Paths.get(strFile));
               }
           }
       } catch (Exception e) {
           log.info(e.getMessage());
       }
    }

    public boolean syncData(String fileLocation, Boolean current) {
        boolean isProcessed = false;
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator +  "data.json";
            try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                jsonGenerator.setCodec(objectMapper);
                jsonGenerator.useDefaultPrettyPrinter();
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("v", "216");
                if(current) {
                    jsonGenerator.writeStringField("init", "false");
                }else {
                    jsonGenerator.writeStringField("init", "true");
                }
                jsonGenerator.writeEndObject();
                isProcessed = true;
            } catch (IOException e) {
                addError("data", e.getMessage(), getPrintStackError(e));
                isProcessed = false;
                log.error("Error writing data to a JSON file: {}", e.getMessage());
            }
        } catch (Exception e) {
            addError("extract", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping data: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Handles Extract data export on client.
     * @param facilityId
     * @param reportStartDate
     * @param reportEndDate
     * @param fileLocation
     * @return boolean - true | false
     */
    @Override
    public boolean extractExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate, String period, String fileLocation) {
        boolean isProcessed = false;
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            //String tempFile = ConstantUtility.TEMP_BATCH_DIR + ConstantUtility.RADET_FILENAME;
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator +  EXTRACT_FILENAME;
            LocalDate previousQuarterEnd = quarterUtility.getPreviousQuarter(reportEndDate).getEndDate();
            LocalDate previousPreviousQuarterEnd = quarterUtility.getPreviousQuarter(previousQuarterEnd).getEndDate();
            LocalDate viralLoadGracePeriod = reportEndDate.plusMonths(1);
            LocalDate currentQterStartDate = quarterService.getCurrentQuarter(reportEndDate).getStartDate();
            log.info("end date {}", reportEndDate);
            log.info("viralLoadGracePeriod {}", viralLoadGracePeriod);
            List<RadetReportDto> radetList = repository.getRadetData(facilityId, reportStartDate, reportEndDate.plusDays(1),
                    previousQuarterEnd, previousPreviousQuarterEnd, currentQterStartDate);
            System.out.println("Total Extract Generated "+ radetList.size());
            if (!radetList.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildRadetJson(jsonGenerator, radetList, period);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (IOException e) {
                    //e.printStackTrace();
                    addError("extract", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing extract to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            addError("extract", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping extract: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Handles HTS data export on client.
     * @param facilityId
     * @param reportStartDate
     * @param reportEndDate
     * @param fileLocation
     * @return boolean - true | false
     */
    @Override
    public boolean htsExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate, String period, String fileLocation) {
        boolean isProcessed = false;
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation +File.separator +  HTS_FILENAME;
            LocalDate previousQuarterEnd = quarterUtility.getPreviousQuarter(reportEndDate).getEndDate();
            //LocalDate previousPreviousQuarterEnd = quarterUtility.getPreviousQuarter(previousQuarterEnd).getEndDate();
            List<HtsReportDto> htsList = repository.getHtsReport(facilityId, reportStartDate, reportEndDate);
            System.out.println("Total HTS Generated "+ htsList.size());
            if (!htsList.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildHtsJson(jsonGenerator, htsList, period);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (IOException e) {
                    addError("HTS", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing HTS to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("HTS", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping HTS: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles PrEP data export on client.
     * @param facilityId
     * @param reportStartDate
     * @param reportEndDate
     * @param fileLocation
     * @return boolean - true | false
     */
    @Override
    public boolean prepExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate, String period, String fileLocation) {
        boolean isProcessed = false;
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + PREP_FILENAME;
            List<PrepReportDto> prepList = repository.getPrepReport(facilityId, reportStartDate, reportEndDate);
            System.out.println("Total Prep Generated "+ prepList.size());
            if (!prepList.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildPrepJson(jsonGenerator, prepList, period);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (IOException e) {
                    addError("PrEP", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing Prep to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("PrEP", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping PrEP: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles Patient data export on client.
     * @param facilityId
     * @param reportStartDate
     * @param reportEndDate
     * @param fileLocation
     * @return boolean - true | false
     */
    @Override
    public boolean patientExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate, String fileLocation) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + PATIENT_FILENAME;
            List<PatientDto> patients = repository.getPatientData(facilityId, reportStartDate, reportEndDate);
            System.out.println("Total Patient Generated "+ patients.size());
            if (!patients.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildPatientJson(jsonGenerator, patients);
                    jsonGenerator.writeEndArray();
                    log.info("Patient successfully written");
                    isProcessed = true;
                } catch (Exception e) {
                    addError("PATIENT", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing Patient to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("PATIENT", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping Patient: {}", e.getMessage());
        }

        return isProcessed;
    }


    /**
     * Handles clinic data export on client.
     * @param facilityId
     * @param startDate
     * @param endDate
     * @return boolean - true | false
     */
    @Override
    public boolean clinicExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + CLINIC_FILENAME;
            List<ClinicDataDto> clinics = repository.getClinicData(facilityId, startDate, endDate);
            System.out.println("Total Clinic Generated "+ clinics.size());
            if (!clinics.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildClinicJson(jsonGenerator, clinics);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (IOException e) {
                    addError("CLINIC", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing Clinic to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("CLINIC", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping Clinic: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles laboratory order data export on client.
     * @param facilityId
     * @param startDate
     * @param endDate
     * @return boolean - true | false
     */
    @Override
    public boolean laboratoryOrderExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + LABORATORY_ORDER_FILENAME;
            List<LaboratoryOrderDto> laboratoryOrders = repository.getLaboratoryOrder(facilityId, startDate, endDate);
            System.out.println("Total laboratoryOrder Generated "+ laboratoryOrders.size());
            if (!laboratoryOrders.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildLaboratoryOrderJson(jsonGenerator, laboratoryOrders);
                    jsonGenerator.writeEndArray();
                    log.info("laboratoryOrder successfully written");
                    isProcessed = true;
                } catch (Exception e) {
                    addError("LABORATORY ORDER", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing laboratoryOrder to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("LABORATORY ORDER", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping laboratoryOrder: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles laboratory sample data export on client.
     * @param facilityId
     * @param startDate
     * @param endDate
     * @return boolean - true | false
     */
    @Override
    public boolean laboratorySampleExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + LABORATORY_SAMPLE_FILENAME;
            List<LaboratorySampleDto> laboratorySample = repository.getLaboratorySample(facilityId, startDate, endDate);
            System.out.println("Total laboratorySample Generated "+ laboratorySample.size());
            if (!laboratorySample.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildLaboratorySampleJson(jsonGenerator, laboratorySample);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (Exception e) {
                    addError("LABORATORY SAMPLE", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing laboratorySample to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("LABORATORY SAMPLE", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping laboratorySample: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles laboratory Test data export on client.
     * @param facilityId
     * @param startDate
     * @param endDate
     * @return boolean - true | false
     */
    @Override
    public boolean laboratoryTestExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation) {
        boolean isProcessed = false;
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + LABORATORY_TEST_FILENAME;
            List<LaboratoryTestDto> laboratoryTest = repository.getLaboratoryTest(facilityId, startDate, endDate);
            System.out.println("Total laboratoryTest Generated "+ laboratoryTest.size());
            if (!laboratoryTest.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildLaboratoryTestJson(jsonGenerator, laboratoryTest);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (Exception e) {
                    addError("LABORATORY TEST", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing laboratoryTest to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("LABORATORY TEST", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping laboratoryTest: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles laboratory Result data export on client.
     * @param facilityId
     * @param startDate
     * @param endDate
     * @return boolean - true | false
     */
    @Override
    public boolean laboratoryResultExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + LABORATORY_RESULT_FILENAME;
            List<LaboratoryResultDto> laboratoryResult = repository.getLaboratoryResult(facilityId, startDate, endDate);
            System.out.println("Total laboratoryResult Generated "+ laboratoryResult.size());
            if (!laboratoryResult.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildLaboratoryResultJson(jsonGenerator, laboratoryResult);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (Exception e) {
                    addError("LABORATORY RESULT", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing laboratoryResult to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("LABORATORY RESULT", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping laboratoryResult: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles Pharmacy data export on client.
     * @param facilityId
     * @param startDate
     * @param endDate
     * @return boolean - true | false
     */
    @Override
    public boolean pharmacyExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + PHARMACY_FILENAME;
            List<PharmacyDto> pharmacy = repository.getPharmacy(facilityId, startDate, endDate);
            System.out.println("Total pharmacy Generated "+ pharmacy.size());
            if (!pharmacy.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildPharmacyJson(jsonGenerator, pharmacy);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (Exception e) {
                    addError("PHARMACY", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing pharmacy to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("PHARMACY", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping pharmacy: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles biometric data export on client.
     * @param facilityId
     * @param startDate
     * @param endDate
     * @return boolean - true | false
     */
    @Override
    public boolean biometricExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + BIOMETRIC_FILENAME;
            List<BiometricDto> biometric = repository.getBiometric(facilityId, startDate, endDate);
            System.out.println("Total biometric Generated "+ biometric.size());
            if (!biometric.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildBiometricJson(jsonGenerator, biometric);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (Exception e) {
                    addError("BIOMETRIC", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing biometric to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("BIOMETRIC", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping biometric: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles hiv enrollment data export on client.
     * @param facilityId
     * @param startDate
     * @param endDate
     * @return boolean - true | false
     */
    @Override
    public boolean enrollmentExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation) {
        boolean isProcessed = false;

        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + ENROLLMENT_FILENAME;
            List<EnrollmentDto> enrollments = repository.getEnrollmentData(facilityId, startDate, endDate);
            System.out.println("Total enrollments Generated "+ enrollments.size());
            if (!enrollments.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildEnrollmentJson(jsonGenerator, enrollments);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (Exception e) {
                    addError("ENROLLMENT", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing enrollment to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("ENROLLMENT", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping enrollment: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles observation data export on client.
     * @param facilityId
     * @param startDate
     * @param endDate
     * @return boolean - true | false
     */
    @Override
    public boolean observationExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation) {
        boolean isProcessed = false;

        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + OBSERVATION_FILENAME;
            List<ObservationDto> observations = repository.getObservationData(facilityId, startDate, endDate);
            System.out.println("Total observations Generated "+ observations.size());
            if (!observations.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildObservationJson(jsonGenerator, observations);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (Exception e) {
                    addError("OBSERVATION", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing observations to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("OBSERVATION", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping observations: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles status tracker data export on client.
     * @param facilityId
     * @param startDate
     * @param endDate
     * @return boolean - true | false
     */
    @Override
    public boolean statusTrackerExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation) {
        boolean isProcessed = false;

        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + STATUS_TRACKER_FILENAME;
            List<StatusTrackerDto> statusTrackers = repository.getStatusTrackerData(facilityId, startDate, endDate);
            System.out.println("Total status tracker Generated "+ statusTrackers.size());
            if (!statusTrackers.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildStatusTrackerJson(jsonGenerator, statusTrackers);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (Exception e) {
                    addError("STATUS TRACKER", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing status tracker to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("STATUS TRACKER", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping status tracker: {}", e.getMessage());
        }

        return isProcessed;
    }

    /**
     * Handles EAC data export on client.
     * @param facilityId
     * @param startDate
     * @param endDate
     * @return boolean - true | false
     */
    @Override
    public boolean eacExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation) {
        boolean isProcessed = false;

        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + EAC_FILENAME;
            List<EacDto> eacs = repository.getEacData(facilityId, startDate, endDate);
            System.out.println("Total eac Generated "+ eacs.size());
            if (!eacs.isEmpty()) {
                try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                    jsonGenerator.setCodec(objectMapper);
                    jsonGenerator.useDefaultPrettyPrinter();
                    jsonGenerator.writeStartArray();
                    buildJson.buildEacJson(jsonGenerator, eacs);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (Exception e) {
                    addError("EAC", e.getMessage(), getPrintStackError(e));
                    isProcessed = false;
                    log.error("Error writing eac to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("EAC", e.getMessage(), getPrintStackError(e));
            log.error("Error mapping eac: {}", e.getMessage());
        }

        return isProcessed;
    }


    /**
     * get Datim ID from facility ID.
     * @param facilityId
     * @return String - the datimId
     */
    @Override
    public String getDatimId(Long facilityId)
    {
        //log.info("facilityId {}", facilityId);
        String datimId = radetUploadTrackersRepository.getDatimCode(facilityId);
        return datimId;

    }

    private void addError(String name, String error, String others){
        ERROR_LOG.add(new ErrorLog(name, error, others));
    }

    /**
     * using a StringWriter, to print the stack trace into a String
     * @param exception
     * @return String - the exception stack message
     */
    private String getPrintStackError(Exception exception){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return sw.toString();
    }

}
