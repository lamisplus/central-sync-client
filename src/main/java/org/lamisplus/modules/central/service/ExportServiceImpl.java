package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.service.UserService;
import org.lamisplus.modules.central.domain.dto.*;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.repository.RadetUploadTrackersRepository;
import org.lamisplus.modules.central.repository.ReportRepository;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.lamisplus.modules.central.utility.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Override
    public String bulkExport(Long facilityId) {
        if(!ERROR_LOG.isEmpty()) ERROR_LOG.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
        LocalDate reportStartDate = LocalDate.parse("1980-01-01", formatter);
        LocalDate reportEndDate = LocalDate.now().plusDays(ONE_DAY);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime reportStartTime = LocalDateTime.parse("1985-01-01 00:00:00", timeFormatter);
        LocalDateTime reportEndTime = LocalDateTime.parse(LocalDateTime.now().format(timeFormatter), timeFormatter).plusDays(ONE_DAY);

        SyncHistory history = syncHistoryRepository.getDateLastSync(facilityId).orElse(null);

        if(history != null){
            LocalDateTime lastSync = history.getDateLastSync();
            reportStartTime=LocalDateTime.parse(dateUtility.ConvertDateTimeToString(lastSync), timeFormatter);;
        }

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
            log.info("Extracting Clinic data to JSON");
            boolean clinicIsProcessed = clinicExport(facilityId, reportStartTime, reportEndTime);
            log.info("Extracting Patient data to JSON");
            boolean patientIsProcessed = patientExport(facilityId, reportStartTime, reportEndTime);
            log.info("Extracting laboratoryOrder data to JSON");
            boolean laboratoryOrderIsProcessed = laboratoryOrderExport(facilityId, reportStartTime, reportEndTime);
            log.info("Extracting laboratorySample data to JSON");
            boolean laboratorySampleIsProcessed = laboratorySampleExport(facilityId, reportStartTime, reportEndTime);
            log.info("Extracting laboratoryTest data to JSON");
            boolean laboratoryTestIsProcessed = laboratoryTestExport(facilityId, reportStartTime, reportEndTime);
            log.info("Extracting laboratoryResult data to JSON");
            boolean laboratoryResultIsProcessed = laboratoryResultExport(facilityId, reportStartTime, reportEndTime);
            log.info("Extracting Pharmacy data to JSON");
            boolean pharmacyIsProcessed = pharmacyExport(facilityId, reportStartTime, reportEndTime);
            log.info("Extracting biometric data to JSON");
            boolean biometricIsProcessed = biometricExport(facilityId, reportStartTime, reportEndTime);


            if (radetIsProcessed || htsIsProcessed || prepIsProcessed
                    || clinicIsProcessed || patientIsProcessed || laboratoryOrderIsProcessed
                    || laboratorySampleIsProcessed || laboratoryTestIsProcessed
                    || laboratoryResultIsProcessed || pharmacyIsProcessed || biometricIsProcessed) {
                log.info("Writing all exports to a zip file");
                Date date1 = new Date();
                String datimCode = getDatimId(facilityId);
                zipFileName = datimCode+"_" + ConstantUtility.DATE_FORMAT.format(date1) + ".zip";
                File file = new File(ConstantUtility.TEMP_BATCH_DIR);
                fileUtility.zipDirectory(file, ConstantUtility.TEMP_BATCH_DIR + zipFileName);

                //update synchistory
                int fileSize = (int) fileUtility.getFileSize(ConstantUtility.TEMP_BATCH_DIR + zipFileName);
                SyncHistoryRequest request = new SyncHistoryRequest(facilityId, zipFileName, fileSize, (ERROR_LOG.isEmpty()) ? null : ERROR_LOG);
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
                    buildJson.buildRadetJson(jsonGenerator, radetList);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (IOException e) {
                    addError("RADET", e.getMessage(), null);
                    isProcessed = false;
                    log.error("Error writing RADET to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("RADET", e.getMessage(), null);
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
                    buildJson.buildHtsJson(jsonGenerator, htsList);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (IOException e) {
                    addError("HTS", e.getMessage(), null);
                    isProcessed = false;
                    log.error("Error writing HTS to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("HTS", e.getMessage(), null);
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
                    buildJson.buildPrepJson(jsonGenerator, prepList);
                    jsonGenerator.writeEndArray();
                    isProcessed = true;
                } catch (IOException e) {
                    addError("PrEP", e.getMessage(), null);
                    isProcessed = false;
                    log.error("Error writing Prep to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("PrEP", e.getMessage(), null);
            log.error("Error mapping PrEP: {}", e.getMessage());
        }

        return isProcessed;
    }

    @Override
    public boolean patientExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = ConstantUtility.TEMP_BATCH_DIR + ConstantUtility.PATIENT_FILENAME;
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
                    addError("PATIENT", e.getMessage(), null);
                    isProcessed = false;
                    log.error("Error writing Patient to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("PATIENT", e.getMessage(), null);
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
    public boolean clinicExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = ConstantUtility.TEMP_BATCH_DIR + ConstantUtility.CLINIC_FILENAME;
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
                    addError("CLINIC", e.getMessage(), null);
                    isProcessed = false;
                    log.error("Error writing Clinic to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("CLINIC", e.getMessage(), null);
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
    public boolean laboratoryOrderExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = ConstantUtility.TEMP_BATCH_DIR + LABORATORY_ORDER_FILENAME;
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
                    addError("LABORATORY ORDER", e.getMessage(), null);
                    isProcessed = false;
                    log.error("Error writing laboratoryOrder to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("LABORATORY ORDER", e.getMessage(), null);
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
    public boolean laboratorySampleExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = ConstantUtility.TEMP_BATCH_DIR + LABORATORY_SAMPLE_FILENAME;
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
                    addError("LABORATORY SAMPLE", e.getMessage(), null);
                    isProcessed = false;
                    log.error("Error writing laboratorySample to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("LABORATORY SAMPLE", e.getMessage(), null);
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
    public boolean laboratoryTestExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate) {
        boolean isProcessed = false;
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = ConstantUtility.TEMP_BATCH_DIR + LABORATORY_TEST_FILENAME;
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
                    addError("LABORATORY TEST", e.getMessage(), null);
                    isProcessed = false;
                    log.error("Error writing laboratoryTest to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("LABORATORY TEST", e.getMessage(), null);
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
    public boolean laboratoryResultExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = ConstantUtility.TEMP_BATCH_DIR + LABORATORY_RESULT_FILENAME;
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
                    addError("LABORATORY RESULT", e.getMessage(), null);
                    isProcessed = false;
                    log.error("Error writing laboratoryResult to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("LABORATORY RESULT", e.getMessage(), null);
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
    public boolean pharmacyExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = ConstantUtility.TEMP_BATCH_DIR + PHARMACY_FILENAME;
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
                    addError("PHARMACY", e.getMessage(), null);
                    isProcessed = false;
                    log.error("Error writing pharmacy to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("PHARMACY", e.getMessage(), null);
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
    public boolean biometricExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate) {
        boolean isProcessed = false;
         
        try {
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            String tempFile = ConstantUtility.TEMP_BATCH_DIR + BIOMETRIC_FILENAME;
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
                    addError("BIOMETRIC", e.getMessage(), null);
                    isProcessed = false;
                    log.error("Error writing biometric to a JSON file: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            addError("BIOMETRIC", e.getMessage(), null);
            log.error("Error mapping biometric: {}", e.getMessage());
        }

        return isProcessed;
    }

    @Override
    public String getDatimId(Long facilityId)
    {
        String datimId = radetUploadTrackersRepository.getDatimCode(facilityId);
        return datimId;

    }

    private void addError(String name, String error, String others){
        ERROR_LOG.add(new ErrorLog(name, error, others));
    }

}
