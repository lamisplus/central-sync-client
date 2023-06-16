package org.lamisplus.modules.central.utility;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ConstantUtility {
    public static final String TEMP_BASE_DIR = System.getProperty("user.dir");
    public static final String TEMP_BATCH_DIR = TEMP_BASE_DIR + "/batch/temp/";
    public static final String TEMP_SERVER_DIR = TEMP_BASE_DIR + "/server/temp/";
    public static final String RADET_FILENAME = "radet.json";
    public static final String PREP_FILENAME = "prep.json";
    public static final String CLINIC_FILENAME = "clinic.json";
    public static final String PATIENT_FILENAME = "patient.json";
    public static final String LABORATORY_ORDER_FILENAME = "laboratoryOrder.json";
    public static final String LABORATORY_SAMPLE_FILENAME = "laboratorySample.json";
    public static final String LABORATORY_TEST_FILENAME = "laboratoryTest.json";
    public static final String LABORATORY_RESULT_FILENAME = "laboratoryResult.json";
    public static final String HTS_FILENAME = "hts.json";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String ATTACHMENT_FILENAME = "attachment; filename=";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    public static final String FIRST_NAME = "firstname";
    public static final String SURNAME = "surname";
    public static final String OTHER_NAME = "othername";
    public static final String SEX = "sex";
    public static final String AGE = "age";
    public static final String DATE_OF_BIRTH = "dateofbirth";
    public static final String PHONE_NUMBER = "phonenumber";
    public static final String MARITAL_STATUS = "maritalstatus";
    public static final String LGA_OF_RESIDENCE = "lgaofresidence";
    public static final String STATE_OF_RESIDENCE = "stateofresidence";
    public static final String EDUCATION = "education";
    public static final String OCCUPATION = "occupation";
    public static final String HTS_LATITUDE = "htslatitude";
    public static final String HTS_LONGITUDE = "htslongitude";
    public static final String CLIENT_ADDRESS = "clientaddress";
    public static final String DATE_VISIT = "datevisit";
    public static final String FIRST_TIME_VISIT = "firsttimevisit";
    public static final String NUMBER_OF_CHILDREN = "numberofchildren";
    public static final String NUMBER_OF_WIVES = "numberofwives";
    public static final String INDEX_CLIENT = "indexclient";
    public static final String PREP_OFFERED = "prepoffered";
    public static final String PREP_ACCEPTED = "prepaccepted";
    public static final String PREVIOUSLY_TESTED = "previouslytested";
    public static final String TARGET_GROUP = "targetgroup";
    public static final String REFERRED_FROM = "referredfrom";
    public static final String TESTING_SETTING = "testingsetting";
    public static final String COUNSELING_TYPE = "counselingtype";
    public static final String PREGNANCY_STATUS = "pregnacystatus";
    public static final String BREASTFEEDING = "breastfeeding";
    public static final String INDEX_TYPE = "indextype";
    public static final String IF_RECENCY_TESTING_OPT_IN = "ifrecencytestingoptin";
    public static final String RECENCY_ID = "recencyid";
    public static final String RECENCY_TEST_TYPE = "recencytesttype";
    public static final String RECENCY_TEST_DATE = "recencytestdate";
    public static final String RECENCY_INTERPRETATION = "recencyinterpretation";
    public static final String FINAL_RECENCY_RESULT = "finalrecencyresult";
    public static final String VIRAL_LOAD_RESULT = "viralloadresult";
    public static final String VIRAL_LOAD_SAMPLE_COLLECTION_DATE = "viralloadsamplecollectiondate";
    public static final String VIRAL_LOAD_CONFIRMATION_RESULT = "viralloadconfirmationresult";
    public static final String VIRAL_LOAD_CONFIRMATION_DATE = "viralloadconfirmationdate";
    public static final String ASSESSMENT_CODE = "assessmentcode";
    public static final String MODALITY = "modality";
    public static final String SYPHILIS_TEST_RESULT = "syphilistestresult";
    public static final String HEPATITIS_B_TEST_RESULT = "hepatitisbtestresult";
    public static final String HEPATITIS_C_TEST_RESULT = "hepatitisctestresult";
    public static final String CD4_TYPE = "cd4type";
    public static final String CD4_TEST_RESULT = "cd4testresult";
    public static final String HIV_TEST_RESULT = "hivtestresult";
    public static final String FINAL_HIV_TEST_RESULT = "finalhivtestresult";
    public static final String DATE_OF_HIV_TESTING = "dateofhivtesting";
    public static final String NUMBER_OF_CONDOMS_GIVEN = "numberofcondomsgiven";
    public static final String NUMBER_OF_LUBRICANTS_GIVEN = "numberoflubricantsgiven";

    //PREP variables
    public static final String HIV_ENROLLMENT_DATE = "hivenrollmentdate";
    public static final String DATE_OF_REGISTRATION = "dateofregistration";
    public static final String RESIDENTIAL_STATE = "residentialstate";
    public static final String RESIDENTIAL_LGA = "residentiallga";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";
    public static final String BASELINE_REGIMEN = "baselineregimen";
    public static final String BASELINE_SYSTOLIC_BP = "baselinesystolicbp";
    public static final String BASELINE_DIASTOLIC_BP = "baselinediastolicbp";
    public static final String BASELINE_T_WEIGHT = "baselinetweight";
    public static final String BASELINE_HEIGHT = "baselineheight";
    public static final String PREP_COMMENCEMENT_DATE = "prepcommencementdate";
    public static final String BASELINE_URINALYSIS = "baselineurinalysis";
    public static final String BASELINE_URINALYSIS_DATE = "baselineurinalysisdate";
    public static final String BASELINE_CREATININE = "baselinecreatinine";
    public static final String BASELINE_HEPATITIS_B = "baselinehepatitisb";
    public static final String BASELINE_HEPATITIS_C = "baselinehepatitisc";
    public static final String INTERRUPTION_REASON = "interruptionreason";
    public static final String INTERRUPTION_DATE = "interruptiondate";
    public static final String HIV_STATUS_AT_PREP_INITIATION = "hivstatusatprepinitiation";
    public static final String INDICATION_FOR_PREP = "indicationforprep";
    public static final String CURRENT_REGIMEN = "currentregimen";
    public static final String DATE_OF_LAST_PICKUP = "dateoflastpickup";
    public static final String CURRENT_SYSTOLIC_BP = "currentsystolicbp";
    public static final String CURRENT_DIASTOLIC_BP = "currentdiastolicbp";
    public static final String CURRENT_WEIGHT = "currentweight";
    public static final String CURRENT_HEIGHT = "currentheight";
    public static final String CURRENT_URINALYSIS = "currenturinalysis";
    public static final String CURRENT_URINALYSIS_DATE = "currenturinalysisdate";
    public static final String CURRENT_HIV_STATUS = "currenthivstatus";
    public static final String DATE_OF_CURRENT_HIV_STATUS = "dateofcurrenthivstatus";
    public static final String CURRENT_STATUS = "currentstatus";
    public static final String DATE_OF_CURRENT_STATUS = "dateofcurrentstatus";
    public static final String STATE = "state";
    public static final String LGA = "lga";
    public static final String FACILITY_NAME = "facilityName";
    public static final String FACILITY = "facility";
    public static final String DATIM_ID = "datimId";
    public static final String PERSON_UUID = "personUuid";
    public static final String HOSPITAL_NUMBER = "hospitalNumber";
    public static final String DATIM_CODE = "datimCode";
    public static final String CLIENT_CODE = "clientCode";
    public static final String PATIENT_ID = "patientId";

    //Clinic
    public static final String VISIT_DATE = "visitDate";
    public static final String CLINICAL_STAGE = "clinicalStage";
    public static final String FUNCTIONAL_STATUS = "functionalStatus";
    public static final String TB_STATUS = "tbStatus";
    public static final String SYSTOLIC = "systolic";
    public static final String DIASTOLIC = "diastolic";
    public static final String ARCHIVED = "archived";
    public static final String CLINIC_PREGNANCY_STATUS = "pregnacystatus";
    public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    public static final String NEXT_APPOINTMENT = "nextAppointment";
    public static final String LAST_MODIFIED_BY = "lastModifiedBy";
    public static final String BODY_WEIGHT = "bodyWeight";

    public static final String PATIENT_FIRST_NAME = "firstName";
    public static final String PATIENT_OTHER_NAME = "otherName";
    public static final String PATIENT_DATE_OF_BIRTH = "dateOfBirth";
    public static final String PATIENT_DATE_OF_REGISTRATION = "dateOfRegistration";
    public static final String PATIENT_MARITAL_STATUS = "maritalStatus";
    public static final String PATIENT_EDUCATION = "education";
    public static final String PATIENT_RESIDENTIAL_STATE = "residentialState";
    public static final String PATIENT_RESIDENTIAL_LGA = "residentialLga";

    public static final String TOWN = "town";

    public static final String UUID = "uuid";
    public static final String ORDER_DATE = "orderDate";

    public static final String DATE_SAMPLE_COLLECTED = "dateSampleCollected";

    public static final String TEST_ID = "testId";

    public static final String LAB_TEST_NAME = "labTestName";
    public static final String GROUP_NAME = "groupName";
    public static final String VIRAL_LOAD_INDICATION = "viralLoadIndication";
    public static final String UNIT_MEASUREMENT = "unitMeasurement";

    public static final String DATE_ASSAYED = "dateAssayed";
    public static final String DATE_RESULT_REPORTED = "dateResultReported";
    public static final String RESULT_REPORTED = "resultReported";
}
