package org.lamisplus.modules.central.utility;

import java.text.SimpleDateFormat;

public class ConstantUtility {
    public static final String TEMP_BASE_DIR = System.getProperty("user.dir");
    public static final String TEMP_BATCH_DIR = TEMP_BASE_DIR + "/batch/temp/";
    public static final String TEMP_SERVER_DIR = TEMP_BASE_DIR + "/server/temp/";
    public static final String RADET_FILENAME = "radet.json";
    public static final String PREP_FILENAME = "prep.json";
    public static final String HTS_FILENAME = "hts.json";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String ATTACHMENT_FILENAME = "attachment; filename=";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");




}
