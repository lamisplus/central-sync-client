package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.controller.vm.LoginVM;
import org.lamisplus.modules.central.domain.entity.Config;
import org.lamisplus.modules.central.domain.entity.ConfigTable;
import org.lamisplus.modules.central.domain.entity.SyncHistoryTracker;
import org.lamisplus.modules.central.domain.mapper.ResultSetToJsonMapper;
import org.lamisplus.modules.central.domain.dto.*;
import org.lamisplus.modules.central.domain.entity.SyncHistory;
import org.lamisplus.modules.central.repository.SyncHistoryRepository;
import org.lamisplus.modules.central.repository.SyncHistoryTrackerRepository;
import org.lamisplus.modules.central.utility.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import static org.lamisplus.modules.central.utility.ConstantUtility.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {
    public static final int FETCH_SIZE = 3000;
    public static final String SYNC_TRACKER_STATUS = "Generated";
    public static final int UN_ARCHIVED = 0;
    public static final String START_DATE = "1985-01-01 01:01:01";
    public static final String GENERATED_SUCCESSFULLY = "Generated successfully...";
    public static final String GENERATING_DATA_JSON = "Generating data json";
    public static final String MODULE_CHECK = "Module check";
    public static final String GENERATING = "File Generation";
    public static final String DATA_JSON = "meta_data";
    public static final String INIT = "init";
    public static final String UNDER_SCORE = "_";
    public static final String NOT_AVAILABLE = "N/A";
    public static Long FILE_FACILITY_ID = null;
    private final FileUtility fileUtility;
    private final SyncHistoryService syncHistoryService;
    private final SyncHistoryRepository syncHistoryRepository;
    private final SyncHistoryTrackerRepository syncHistoryTrackerRepository;
    private static final ArrayList MESSAGE_LOG = new ArrayList<>();
    private final DateUtility dateUtility;
    private final DataSource dataSource;
    private final ConfigTableService configTableService;
    private final FacilityAppKeyService facilityAppKeyService;
    private final RSAUtils rsaUtils;
    private final ConfigModuleService configModuleService;
    HashMap<String, String> fileNames = new HashMap<>();
    private final ConfigService configService;


    /**
     * generate files for syncing.
     * @param facilityId
     * @param current
     * @return String
     */
    @Override
    public String generateFilesForSyncing(Long facilityId, Boolean current) {
        FILE_FACILITY_ID = facilityId;
        boolean anyTable = false;
        SyncHistoryResponse syncResponse = null;
        String appKey = facilityAppKeyService.FindByFacilityId(Integer.valueOf(String.valueOf(facilityId))).getAppKey();


        List<SyncHistoryTracker> saveTrackers = null;
        if(!MESSAGE_LOG.isEmpty()) MESSAGE_LOG.clear();
        //do a module check on log files to message log
        moduleCheckAndMsgLog();

        //Generate uuid for the key
        String uuid = java.util.UUID.randomUUID().toString();
        Path path = Paths.get(TEMP_BATCH_DIR);
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String start = START_DATE;
        String end = dateUtility.ConvertDateTimeToString(LocalDateTime.now());

        SyncHistory history = syncHistoryRepository.getDateLastSync(facilityId).orElse(null);

        //if current and there is sync history for the facility
        if(current && history != null){
                LocalDateTime lastSync = history.getDateLastSync();
                start = dateUtility.ConvertDateTimeToString(lastSync);
        }

        String zipFileName = "None";
        String fileFolder = DATE_FORMAT.format(new Date());
        String folder = TEMP_BATCH_DIR + fileFolder + File.separator;
        Path folderPath = Paths.get(folder);
        Path createdFile = folderPath;
        try {
            createdFile = Files.createDirectories(folderPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if(!fileNames.isEmpty())fileNames.clear();
            List<SyncHistoryTracker> syncHistoryTrackers = new ArrayList<>();
            List<ConfigTable> configTables = configTableService.getTablesForSyncing();
            //Generate AES key...
            uuid = AESUtil.generateAESKey(uuid);

            for(ConfigTable configTable : configTables){
                List<SyncHistoryTracker> trackers;
                Long facility = configTable.getHasFacilityId() == null || !configTable.getHasFacilityId() ? null : facilityId;
                trackers = exportAnyTable(configTable.getTableName(), facility, configTable.getUpdateColumn(), start,
                        configTable.getUpdateColumn(), end, fileFolder, uuid, configTable.getExcludeColumns());
                if(!trackers.isEmpty()) {
                    anyTable = true;
                    syncHistoryTrackers.addAll(trackers);
                }
            }

            if (anyTable) {
                String datimCode = getDatimId(facilityId);
                zipFileName = datimCode+"_" + fileFolder + ".zip";

                String fullPath = createdFile + File.separator + zipFileName;
                File dir = new File(createdFile + File.separator);

                //encrypted key
                String key = manageKey(uuid, appKey);

                SyncHistoryRequest request = new SyncHistoryRequest(facilityId, zipFileName, 0, (MESSAGE_LOG.isEmpty()) ? null : MESSAGE_LOG, folder, key);
                syncResponse = syncHistoryService.saveSyncHistory(request);

                if (syncResponse != null && !syncHistoryTrackers.isEmpty()) {
                    saveTrackers = syncHistoryTrackerRepository.saveAll(getSyncHistoryTrackers(syncHistoryTrackers, syncResponse));
                }
                //set file details
                FileDetail fileDetail = setFileDetails(appKey, datimCode, current, syncResponse, saveTrackers);
                //create meta data
                syncData(fileFolder, fileDetail);

                //zip json files
                fileUtility.zipDirectory(dir, fullPath, fileFolder);
                //get file size
                int fileSize = (int) fileUtility.getFileSize(fullPath);
                SyncHistory syncHistory = syncHistoryRepository.findByGenKey(key).orElse(null);
                //update history with file size
                if(syncHistory != null){
                    syncHistory.setUploadSize(fileSize);
                    syncHistoryRepository.save(syncHistory);
                }
                log.info("Data export completed");
            } else {
                zipFileName = "NO_RECORD";
            }
        } catch (Exception e) {
            log.debug("Something went wrong. Error: {}", e.getMessage());
            e.printStackTrace();
        }
        log.info("Initializing successful generated file...");
        return zipFileName;
    }

    /**
     * set file details
     * @param appKey
     * @param datimId
     * @Param current
     * @Param syncResponse
     * @Param saveTrackers
     * @return FileDetail
     */
    private FileDetail setFileDetails(String appKey, String datimId, Boolean current, @NotNull  SyncHistoryResponse syncResponse, List<SyncHistoryTracker> saveTrackers) {
        FileDetail fileDetail = new FileDetail();
        //Set file details
        if(syncResponse != null && !saveTrackers.isEmpty()) {
            fileDetail.setKey(syncResponse.getGenKey());
            fileDetail.setHistory(syncResponse.getUuid());
            fileDetail.setInit(current);
            fileDetail.setDatimId(datimId);
            fileDetail.setAppKey(appKey);
            Optional<String> version = syncHistoryRepository.getClientSyncModuleVersion();

            if (version.isPresent())
                fileDetail.setVersion(version.get());
            else
                fileDetail.setVersion(NOT_AVAILABLE);

            fileDetail.setFileTracker(saveTrackers.stream()
                    .map(syncHistoryTracker -> new FileTrackerDTO(syncHistoryTracker
                            .getFileName(), syncHistoryTracker.getUuid()))
                    .collect(Collectors.toList())
            );
        }
        return fileDetail;
    }

    private List extracted(List<SyncHistoryTracker> trackers) {
        HashMap<String, String> names = new HashMap<>();
        if(trackers != null && !trackers.isEmpty()){
            return trackers.stream()
                    .map(syncHistoryTracker -> {
                        names.put(syncHistoryTracker.getFileName(), syncHistoryTracker.getUuid().toString());
                        return names;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

    /**
     * get Sync History Trackers.
     * @param syncHistoryTrackers
     * @param syncResponse
     * @return List<SyncHistoryTracker>
     */
    private static List<SyncHistoryTracker> getSyncHistoryTrackers(List<SyncHistoryTracker> syncHistoryTrackers, SyncHistoryResponse syncResponse) {
        return syncHistoryTrackers
                .stream()
                .map(syncHistoryTracker -> {
                    syncHistoryTracker.setSyncHistoryId(syncResponse.getId());
                    syncHistoryTracker.setUuid(java.util.UUID.randomUUID());
                    syncHistoryTracker.setSyncHistoryUuid(syncResponse.getUuid());
                    return syncHistoryTracker;
                })
                .collect(Collectors.toList());
    }

    /**
     * manage keys.
     * @param uuid
     * @param appKey
     * @return String
     */
    private String manageKey(String uuid, String appKey) {
        log.info("manage key {}", uuid);
        try {
            byte[] keyBytes = DatatypeConverter.parseBase64Binary(uuid);

            //encrypt aes key
            byte[] encryptedKey = this.rsaUtils.encrypt(uuid.getBytes(StandardCharsets.UTF_8), appKey);

            //return as string
            return DatatypeConverter.printBase64Binary(encryptedKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * sync data
     * @param fileLocation
     * @param fileDetail
     * @return boolean
     */
    public boolean syncData(String fileLocation, FileDetail fileDetail) {
        try {
            String configVersion = configService
                    .getActiveConfig()
                    .orElseThrow(()-> new EntityNotFoundException(Config.class, "Config", "is null"));
            ObjectMapper objectMapper = JsonUtility.getObjectMapperWriter();
            JsonFactory jsonFactory = new JsonFactory();
            JSONArray jArray = new JSONArray();
            String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + DATA_JSON + "_" + fileLocation + ".json";
            try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(new FileWriter(tempFile))) {
                jsonGenerator.setCodec(objectMapper);
                jsonGenerator.useDefaultPrettyPrinter();
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("version", fileDetail.getVersion());
                jsonGenerator.writeStringField(INIT, String.valueOf(fileDetail.getInit()));
                jsonGenerator.writeStringField("history", String.valueOf(fileDetail.getHistory()));
                jsonGenerator.writeStringField("key", fileDetail.getKey());
                jsonGenerator.writeStringField("datimId", fileDetail.getDatimId());
                jsonGenerator.writeStringField("appKey", fileDetail.getAppKey());
                jsonGenerator.writeStringField("configVersion", configVersion);
                for (FileTrackerDTO fileTrackerDTO : fileDetail.getFileTracker()) {
                    JSONObject trackerJsonObject = new JSONObject();
                    trackerJsonObject.put("fileName", fileTrackerDTO.getFileName());
                    trackerJsonObject.put("uuid", fileTrackerDTO.getUuid());
                    jArray.put(trackerJsonObject);
                }
                jsonGenerator.writeStringField("fileTracker", jArray.toString());


                //configureObjectMapper(objectMapper);
                jsonGenerator.writeEndObject();
                addMessageLog(DATA_JSON, SYNC_TRACKER_STATUS, DATA_JSON, GENERATED_SUCCESSFULLY + DATA_JSON, MessageType.SUCCESS);
                return true;
            } catch (IOException e) {
                addMessageLog("data", e.getMessage(), getPrintStackError(e), GENERATING_DATA_JSON, MessageType.ERROR);
                log.error("Error writing data to a JSON file: {}", e.getMessage());
            }
        } catch (Exception e) {
            addMessageLog("extract", e.getMessage(), getPrintStackError(e), GENERATING_DATA_JSON,  MessageType.ERROR);
            log.error("Error mapping data: {}", e.getMessage());
        }
        return false;
    }

    /**
     * module check and get message log
     * @return void
     */
    public void moduleCheckAndMsgLog(){
        configModuleService.moduleCheck()
                .stream()
                .map(moduleStatus -> {
                    addMessageLog(moduleStatus.getName(),
                            "Required version is from " + moduleStatus.getMinimumVersion() + " - " + moduleStatus.getMaximumVersion(),
                        "Installed version is " + moduleStatus.getAvailableVersion(), MODULE_CHECK,
                        moduleStatus.getMessage());
                    return moduleStatus;
                }).collect(Collectors.toList());
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
     * @param uuid
     * @param excludeColumn
     * @return boolean - true | false
     */
    @Override
    public List<SyncHistoryTracker> exportAnyTable(String tableName, Long facilityId, String startName, String startDate, String endName, String endDate, String fileLocation, String uuid, String excludeColumn) {
        log.info("Started generating... " + tableName);
        List<SyncHistoryTracker> trackers = new ArrayList<>();
        Long level = 0L;
        String query = null;
        JSONArray jsonArray = new JSONArray();
        Connection conn = null;
        //List list = null;
        SyncHistoryTracker tracker = null;

        if (facilityId == null) {
            query = "SELECT * FROM %s";
            query = String.format(query, tableName);
        } else
            //where no update or audit column
            if (startDate == null) {
                query = "SELECT * FROM %s WHERE facility_id=%d";
                query = String.format(query, tableName, facilityId);
            } else {
                query = "SELECT * FROM %s WHERE facility_id=%d AND %s BETWEEN CAST('%s' AS TIMESTAMP WITHOUT TIME ZONE) AND CAST('%s' AS TIMESTAMP WITHOUT TIME ZONE)";
                query = String.format(query, tableName, facilityId, startName, startDate, endDate);
            }
            log.info("query is {}", query);

        try {
            conn = dataSource.getConnection();
            //Statement stmt = conn.createStatement();
            Statement stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            stmt.setFetchSize(FETCH_SIZE);
            //recursive fetch to carter for pagination
            ResultSet rs = stmt.executeQuery(query);
            jsonArray = ResultSetToJsonMapper.mapResultSet(rs, excludeColumn);
            List queryList = jsonArray.toList();
            log.info("Total " + tableName + " queried for db... " + queryList.size());


            for (List list : ResultSetToJsonMapper.getPages(queryList, FETCH_SIZE)) {
                if(list.size() > 0) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    configureObjectMapper(objectMapper);
                    String fileName = tableName + UNDER_SCORE + level + UNDER_SCORE + fileLocation + ".json";
                    String tempFile = TEMP_BATCH_DIR + fileLocation + File.separator + fileName;
                    Integer fileSize = list.size();
                    log.info("Total " + tableName + " Generated... " + list.size());
                    //Get the byte
                    byte[] bytes = objectMapper.writeValueAsBytes(list);
                    //Get a secret from the uuid generated
                    SecretKey secretKey = AESUtil.getPrivateAESKeyFromDB(uuid);
                    //Encrypt the byte
                    bytes = AESUtil.encrypt(bytes, secretKey);
                    FileUtils.writeByteArrayToFile(new File(tempFile), bytes);
                    tracker = new SyncHistoryTracker(null, null, fileName, fileSize, SYNC_TRACKER_STATUS,
                            LocalDateTime.now(), UN_ARCHIVED, (FILE_FACILITY_ID != null ? FILE_FACILITY_ID : facilityId), null, null);
                    addMessageLog(tableName, SYNC_TRACKER_STATUS, fileName, GENERATING, MessageType.SUCCESS);
                    //success log
                    trackers.add(tracker);
                    ++level;
                }
            }
        } catch (Exception e) {
            addMessageLog(tableName, e.getMessage(), getPrintStackError(e), GENERATING, MessageType.ERROR);
            e.printStackTrace();
            closeDBConnection(conn);
            return trackers;
        } finally {
            closeDBConnection(conn);
        }
    return trackers;
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

    /*private Long countTableRow(String tableName, Long facilityId){
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

    }*/

    /**
     * Configures ObjectMapper
     * @param objectMapper
     * @return void
     */
    private void configureObjectMapper(ObjectMapper objectMapper) {
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
     * get Datim ID from facility ID.
     * @param facilityId
     * @return String - the datimId
     */
    @Override
    public String getDatimId(Long facilityId) {
        return syncHistoryRepository.getDatimCode(facilityId);
    }

    /**
     * add message log
     * @param name
     * @param msg
     * @param others
     * @param activity
     * @param category
     * @return void
     */
    private void addMessageLog(String name, String msg, String others, String activity, MessageType category){
        MESSAGE_LOG.add(new MessageLog(name, msg, others, activity, category, LocalDateTime.now().toString()));
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

    /**
     * get byte from a file
     * @param path
     * @return byte[]
     */
    private byte[] getByte(String path) {
        byte[] getBytes = {};
        try {
            File file = new File(path);
            getBytes = new byte[(int) file.length()];
            InputStream is = new FileInputStream(file);
            is.read(getBytes);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getBytes;
    }

    /**
     * module encrypt Credentials
     * @param login
     * @param appKey
     * @param history
     * @param tracker
     * @return String
     */
    public String encryptCredentials(LoginVM login, String appKey, String history, String tracker, String fileName){
        CredentialDto credential = new CredentialDto(login.getUsername(), login.getPassword());
        try {
            byte[] credentialBytes = credential.toString().getBytes();
            //encrypt rsa key
            byte[] encryptedCredential = this.rsaUtils.encrypt(credentialBytes, appKey);
            return Base64.getEncoder().encodeToString(encryptedCredential);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Encrypt any String message
     * @param message
     * @param appKey
     * @return String
     */
    public String encryptMessage(String message, String appKey){
        try {
            byte[] credentialBytes = message.getBytes();
            //encrypt rsa key
            byte[] encryptedCredential = this.rsaUtils.encrypt(credentialBytes, appKey);
            return Base64.getEncoder().encodeToString(encryptedCredential);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
