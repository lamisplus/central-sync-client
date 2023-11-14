package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.audit4j.core.util.Log;
import org.json.JSONArray;
import org.lamisplus.modules.central.config.DomainConfiguration;
import org.lamisplus.modules.central.domain.entity.ConfigTable;
import org.lamisplus.modules.central.domain.mapper.ResultSetToJsonMapper;
import org.lamisplus.modules.central.domain.dto.*;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.repository.ReportRepository;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.lamisplus.modules.central.utility.*;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.lamisplus.modules.central.utility.ConstantUtility.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {
    public static final int FETCH_SIZE = 10000;
    private final ReportRepository repository;
    private final QuarterUtility quarterUtility;
    private final FileUtility fileUtility;
    private final SyncHistoryService syncHistoryService;
    private final SyncHistoryRepository syncHistoryRepository;
    private final BuildJson buildJson;
    private final SendWebsocketService sendSyncWebsocketService;
    private static final String SYNC_ENDPOINT = "topic/sync";
    private static Integer stat;
    private static final Long ONE_DAY=1L;
    private static final ArrayList ERROR_LOG= new ArrayList<>();
    private final DateUtility dateUtility;
    private final QuarterService quarterService;
    private final DomainConfiguration domainConfiguration;
    private final DataSource dataSource;
    private final ConfigTableService configTableService;


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
        LocalDateTime reportStartTime = LocalDateTime.parse("1985-01-01 01:01:01", timeFormatter);

        String start = "1985-01-01 01:01:01";
        String end = LocalDateTime.now().format(timeFormatter);

        LocalDateTime reportEndTime = LocalDateTime.parse(end, timeFormatter);

        SyncHistory history = syncHistoryRepository.getDateLastSync(facilityId).orElse(null);

        if(!current)history=null;
        if(history != null){
            LocalDateTime lastSync = history.getDateLastSync();
            end = dateUtility.ConvertDateTimeToString(lastSync);
            reportStartTime=LocalDateTime.parse(dateUtility.ConvertDateTimeToString(lastSync), timeFormatter);
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
            log.info("Initializing data export...");
            log.info("Extracting data to JSON...");
            syncData(fileFolder, current);
            log.info("Extracting Extract data to JSON...");
            boolean anyTable = false;

            List<ConfigTable> configTables = configTableService.getTablesForSyncing();
            for(ConfigTable configTable : configTables){
                facilityId = configTable.getUpdateColumn() == null ? null : facilityId;
                anyTable = exportAnyTable(configTable.getTableName(), facilityId, configTable.getUpdateColumn(), start, configTable.getUpdateColumn(), end, fileFolder);
            }
            if (anyTable) {
                String datimCode = getDatimId(facilityId);
                zipFileName = datimCode+"_" + fileFolder + ".zip";

                String fullPath = createdFile + File.separator + zipFileName;
                File dir = new File(createdFile + File.separator);
                fileUtility.zipDirectory(dir, fullPath, fileFolder);
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
            e.printStackTrace();
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
     * Handles table data export on client.
     * @param tableName
     * @param facilityId
     * @param startName
     * @param startDate
     * @param endName
     * @param endDate
     * @param fileLocation
     * @return boolean - true | false
     */
    @Override
    public boolean exportAnyTable(String tableName, Long facilityId, String startName, String startDate, String endName, String endDate, String fileLocation) {
        Log.info("Started generating... " + tableName);
        Long level = 0L;
        String query = null;
        JSONArray jsonArray = new JSONArray();
        Connection conn = null;
        Long size = countTableRow(tableName, facilityId);
        log.info("table count is {}", size);
        Long split = size/FETCH_SIZE;
        log.info("split is {}", split);
        size = split > 1 ? split : FETCH_SIZE;

        if(facilityId == null){
            query = "SELECT * FROM %s ORDER BY id ASC";
            query = String.format(query, tableName);
        } else
            //where no update or audit column
        if(startDate == null){
            query = "SELECT * FROM %s WHERE facility_id=%d";
            query = String.format(query, tableName, facilityId);
        } else {
            query = "SELECT * FROM %s WHERE facility_id=%d AND %s >= CAST('%s' AS TIMESTAMP WITHOUT TIME ZONE) AND %s <= CAST('%s' AS TIMESTAMP WITHOUT TIME ZONE)";
            query = String.format(query, tableName, facilityId, startName, startDate, endName, endDate);
        }

        try {
            conn = dataSource.getConnection();
            //Statement stmt = conn.createStatement();
            Statement stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            stmt.setFetchSize(FETCH_SIZE);
            //recursive fetch to carter for pagination
            do {
                ResultSet rs = stmt.executeQuery(query);
                jsonArray = ResultSetToJsonMapper.mapResultSet(rs);
                List list = jsonArray.toList();

                ObjectMapper objectMapper = new ObjectMapper();
                ConfigureObjectMapper(objectMapper);
                String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + tableName +"_"+ level + ".json";
                Log.info("Total " + tableName + " Generated... " + list.size());

                objectMapper.writeValue(new File(tempFile), list);

                level = split > 1 ? ++level : FETCH_SIZE;
            }while (level < size);

        } catch (Exception e) {
            e.printStackTrace();
            closeDBConnection(conn);
            return false;
        }finally {
            closeDBConnection(conn);
        }
        return true;
    }

    /**
     * Closes an open db connection.
     * @param connection
     * @return void
     */
    private void closeDBConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Long countTableRow(String tableName, Long facilityId){
        log.info("counting table row started... {}", tableName);
        Connection conn = null;
        String query;
        Long count=0L;
        try {
            if(facilityId == null){
                query = "SELECT COUNT(*) FROM %s";
                query = String.format(query, tableName);
            } else{
                    query = "SELECT COUNT(*) FROM %s WHERE facility_id=%d GROUP BY facility_id";
                    query = String.format(query, tableName, facilityId);
                }

            conn = dataSource.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()){
                count = rs.getLong(1);
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;

    }

    /**
     * Configures ObjectMapper
     * @param objectMapper
     * @return void
     */
    private void ConfigureObjectMapper(ObjectMapper objectMapper) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        JsonDeserializer<LocalDateTime> deserializer = new LocalDateTimeDeserializer(formatter);
        javaTimeModule.addDeserializer(LocalDateTime.class, deserializer);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //mapper.configure(DeserializationFeature.)
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.writer(new DefaultPrettyPrinter());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
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
        String datimId = syncHistoryRepository.getDatimCode(facilityId);
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
