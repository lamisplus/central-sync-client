package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.domain.dto.*;
import org.lamisplus.modules.central.utility.ConstantUtility;
import org.lamisplus.modules.central.utility.DateUtility;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.lamisplus.modules.central.utility.ConstantUtility.*;
import static org.lamisplus.modules.central.utility.ConstantUtility.NUMBER_OF_LUBRICANTS_GIVEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class BuildJson {
    private final DateUtility dateUtility;
    public void buildHtsJson(JsonGenerator jsonGenerator, List<HtsReportDto> htsList, String period) {
        for (HtsReportDto hts : htsList) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(STATE, hts.getStateOfResidence());
                jsonGenerator.writeStringField(LGA, hts.getLgaOfResidence());
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
                jsonGenerator.writeStringField(RECENCY_TEST_DATE, dateUtility.ConvertDateToString(hts.getRecencyTestDate()));
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
                jsonGenerator.writeStringField(UUID, hts.getUuid());
                jsonGenerator.writeStringField(PERIOD, period);

                jsonGenerator.writeStringField(NO_OF_PARTNERS_ELICITED, String.valueOf(hts.getNoOfPartnersElicited()));
                jsonGenerator.writeStringField(OFFERED_INS, hts.getOfferedIns());
                jsonGenerator.writeStringField(ACCEPTED_INS, hts.getAcceptedIns());
                jsonGenerator.writeStringField(ENTRY_POINT, hts.getEntryPoint());

                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating HTS JSON: {}", e.getMessage());
                log.error("HTS JSON: {}", hts);
            }
        }

    }
    public void buildRadetJson(JsonGenerator jsonGenerator, List<RadetReportDto> radetList, String period) {
        for (RadetReportDto radet : radetList)
        {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(ConstantUtility.STATE, radet.getState());
                jsonGenerator.writeStringField(ConstantUtility.LGA, radet.getLga());
                jsonGenerator.writeStringField(ConstantUtility.FACILITY_NAME, radet.getFacilityName());
                jsonGenerator.writeStringField(ConstantUtility.DATIM_ID, radet.getDatimId());
                jsonGenerator.writeStringField(ConstantUtility.PERSON_UUID, radet.getPersonUuid());
                jsonGenerator.writeStringField(UNIQUE_ID, radet.getUniqueId());
                jsonGenerator.writeStringField(LGA_OF_RESIDENCE, radet.getLgaOfResidence());
                jsonGenerator.writeStringField(ConstantUtility.HOSPITAL_NUMBER, radet.getHospitalNumber());
                jsonGenerator.writeStringField(RADET_DATE_OF_BIRTH1, radet.getDateOfBirth().toString());
                jsonGenerator.writeStringField(RADET_AGE, radet.getAge().toString());
                jsonGenerator.writeStringField(RADET_GENDER, radet.getGender());
                jsonGenerator.writeStringField(RADET_TARGET_GROUP1, radet.getTargetGroup());
                jsonGenerator.writeStringField(ENROLLMENT_SETTING, radet.getEnrollmentSetting());
                jsonGenerator.writeStringField(ART_START_DATE, radet.getArtStartDate().toString());
                jsonGenerator.writeStringField(REGIMEN_AT_START, radet.getRegimenAtStart());
                jsonGenerator.writeStringField(REGIMEN_LINE_AT_START, radet.getRegimenLineAtStart());
                jsonGenerator.writeStringField(PREGNANCY_STATUS1, radet.getPregnancyStatus());
                jsonGenerator.writeStringField(CURRENT_CLINICAL_STAGE, radet.getCurrentClinicalStage());
                jsonGenerator.writeStringField(CURRENT_WEIGHT1, String.valueOf(radet.getCurrentWeight()));
                jsonGenerator.writeStringField(VIRAL_LOAD_INDICATION1, radet.getViralLoadIndication());
                jsonGenerator.writeStringField(DATE_OF_VIRAL_LOAD_SAMPLE_COLLECTION, dateUtility.ConvertDateToString(radet.getDateOfViralLoadSampleCollection()));
                jsonGenerator.writeStringField(CURRENT_VIRAL_LOAD, radet.getCurrentViralLoad());
                jsonGenerator.writeStringField(DATE_OF_CURRENT_VIRAL_LOAD, dateUtility.ConvertDateToString(radet.getDateOfCurrentViralLoad()));
                jsonGenerator.writeStringField(DATE_OF_CURRENT_VIRAL_LOAD_SAMPLE, dateUtility.ConvertDateToString(radet.getDateOfCurrentViralLoadSample()));
                jsonGenerator.writeStringField(LAST_CD_4_COUNT, radet.getLastCd4Count());
                jsonGenerator.writeStringField(DATE_OF_LAST_CD_4_COUNT, dateUtility.ConvertDateToString(radet.getDateOfLastCd4Count()));
                jsonGenerator.writeStringField(CURRENT_REGIMEN_LINE, radet.getCurrentRegimenLine());
                jsonGenerator.writeStringField(CURRENT_ART_REGIMEN, radet.getCurrentARTRegimen());
                jsonGenerator.writeStringField(MONTHS_OF_ARV_REFILL, String.valueOf(radet.getMonthsOfARVRefill()));
                jsonGenerator.writeStringField(LAST_PICKUP_DATE, dateUtility.ConvertDateToString(radet.getLastPickupDate()));
                jsonGenerator.writeStringField(NEXT_PICKUP_DATE, String.valueOf(radet.getNextPickupDate()));
                jsonGenerator.writeStringField(CURRENT_STATUS_DATE, dateUtility.ConvertDateToString(radet.getCurrentStatusDate()));
                jsonGenerator.writeStringField(CURRENT_STATUS1, radet.getCurrentStatus());
                jsonGenerator.writeStringField(PREVIOUS_STATUS_DATE, dateUtility.ConvertDateToString(radet.getPreviousStatusDate()));
                jsonGenerator.writeStringField(PREVIOUS_STATUS, radet.getPreviousStatus());
                jsonGenerator.writeStringField(DATE_BIOMETRICS_ENROLLED, dateUtility.ConvertDateToString(radet.getDateBiometricsEnrolled()));
                jsonGenerator.writeStringField(NUMBER_OF_FINGERS_CAPTURED, String.valueOf(radet.getNumberOfFingersCaptured()));
                jsonGenerator.writeStringField(DATE_OF_COMMENCEMENT_OF_EAC, dateUtility.ConvertDateToString(radet.getDateOfCommencementOfEAC()));
                jsonGenerator.writeStringField(NUMBER_OF_EAC_SESSION_COMPLETED, String.valueOf(radet.getNumberOfEACSessionCompleted()));
                jsonGenerator.writeStringField(DATE_OF_LAST_EAC_SESSION_COMPLETED, dateUtility.ConvertDateToString(radet.getDateOfLastEACSessionCompleted()));
                jsonGenerator.writeStringField(DATE_OF_EXTEND_EAC_COMPLETION, dateUtility.ConvertDateToString(radet.getDateOfExtendEACCompletion()));
                jsonGenerator.writeStringField(DATE_OF_REPEAT_VIRAL_LOAD_RESULT, dateUtility.ConvertDateTimeToString(radet.getDateOfRepeatViralLoadResult()));
                jsonGenerator.writeStringField(DATE_OF_REPEAT_VIRAL_LOAD_EAC_SAMPLE_COLLECTION, dateUtility.ConvertDateTimeToString(radet.getDateOfRepeatViralLoadEACSampleCollection()));
                jsonGenerator.writeStringField(REPEAT_VIRAL_LOAD_RESULT, radet.getRepeatViralLoadResult());
                jsonGenerator.writeStringField(TB_STATUS1, radet.getTbStatus());
                jsonGenerator.writeStringField(DATE_OF_TB_SCREENED, dateUtility.ConvertDateToString(radet.getDateOfTbScreened()));
                jsonGenerator.writeStringField(DATE_OF_CURRENT_REGIMEN, dateUtility.ConvertDateToString(radet.getDateOfCurrentRegimen()));
                jsonGenerator.writeStringField(DATE_OF_IPT_START, dateUtility.ConvertDateToString(radet.getDateOfIptStart()));
                jsonGenerator.writeStringField(IPT_COMPLETION_DATE, dateUtility.ConvertDateToString(radet.getIptCompletionDate()));
                jsonGenerator.writeStringField(IPT_TYPE, radet.getIptType());
                jsonGenerator.writeStringField(RESULT_OF_CERVICAL_CANCER_SCREENING, radet.getResultOfCervicalCancerScreening());
                jsonGenerator.writeStringField(CERVICAL_CANCER_SCREENING_TYPE, radet.getCervicalCancerScreeningType());
                jsonGenerator.writeStringField(CERVICAL_CANCER_SCREENING_METHOD, radet.getCervicalCancerScreeningMethod());
                jsonGenerator.writeStringField(CERVICAL_CANCER_TREATMENT_SCREENED, radet.getCervicalCancerTreatmentScreened());
                jsonGenerator.writeStringField(DATE_OF_CERVICAL_CANCER_SCREENING, dateUtility.ConvertDateToString(radet.getDateOfCervicalCancerScreening()));
                jsonGenerator.writeStringField(OVC_NUMBER, radet.getOvcNumber());
                jsonGenerator.writeStringField(HOUSEHOLD_NUMBER, radet.getHouseholdNumber());
                jsonGenerator.writeStringField(CARE_ENTRY, radet.getCareEntry());
                jsonGenerator.writeStringField(CAUSE_OF_DEATH, radet.getCauseOfDeath());
                jsonGenerator.writeStringField(VL_ELIGIBILITY_STATUS, radet.getVlEligibilityStatus() + "");
                jsonGenerator.writeStringField(DATE_OF_VL_ELIGIBILITY_STATUS, dateUtility.ConvertDateToString(radet.getDateOfVlEligibilityStatus()));
                jsonGenerator.writeStringField(TB_DIAGNOSTIC_TEST_TYPE, radet.getTbDiagnosticTestType());
                jsonGenerator.writeStringField(DATE_OF_TB_SAMPLE_COLLECTION, dateUtility.ConvertDateToString(radet.getDateOfTbSampleCollection()));
                jsonGenerator.writeStringField(TB_DIAGNOSTIC_RESULT, radet.getTbDiagnosticResult());
                jsonGenerator.writeStringField(DSD_MODEL, radet.getDsdModel());
                jsonGenerator.writeStringField(DATE_OF_TB_DIAGNOSTIC_RESULT_RECEIVED, dateUtility.ConvertDateToString(radet.getDateofTbDiagnosticResultReceived()));
                jsonGenerator.writeStringField(TB_TREATEMENT_TYPE, radet.getTbTreatementType());
                jsonGenerator.writeStringField(TB_TREATMENT_OUTCOME, radet.getTbTreatmentOutcome());
                jsonGenerator.writeStringField(TB_TREATMENT_START_DATE, dateUtility.ConvertDateToString(radet.getTbTreatmentStartDate()));
                jsonGenerator.writeStringField(TB_COMPLETION_DATE, dateUtility.ConvertDateToString(radet.getTbCompletionDate()));
                jsonGenerator.writeStringField(IPT_COMPLETION_STATUS, radet.getIptCompletionStatus());
                jsonGenerator.writeStringField(DATE_OF_LAST_CRYTOCOCAL_ANTIGEN, dateUtility.ConvertDateTimeToString(radet.getDateOfLastCrytococalAntigen()));
                jsonGenerator.writeStringField(LAST_CRYTOCOCAL_ANTIGEN, radet.getLastCrytococalAntigen());
                jsonGenerator.writeStringField(CASE_MANAGER, radet.getCaseManager());
                jsonGenerator.writeStringField(VA_CAUSE_OF_DEATH, radet.getVaCauseOfDeath());
                jsonGenerator.writeStringField(TREATMENT_METHOD_DATE, radet.getTreatmentMethodDate());
                jsonGenerator.writeStringField(DATE_OF_REGISTRATION, dateUtility.ConvertDateToString(radet.getDateOfRegistration()));
                jsonGenerator.writeStringField(DATE_OF_ENROLLMENT, dateUtility.ConvertDateToString(radet.getDateOfEnrollment()));
                jsonGenerator.writeStringField(PERIOD, period);
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating radet JSON: {}", e.getMessage());
                log.error("radet JSON: {}", radet);
            }
        }

    }
    public void buildPrepJson(JsonGenerator jsonGenerator,  List<PrepReportDto> prepList, String period) {
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
                jsonGenerator.writeStringField(BASELINE_T_WEIGHT, String.valueOf(prep.getBaselineWeight()));
                jsonGenerator.writeStringField(BASELINE_HEIGHT, String.valueOf(prep.getBaselineHeight()));
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
                jsonGenerator.writeStringField(PERIOD, period);
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating PrEP JSON: {}", e.getMessage());
                log.error("prep JSON: {}", prep);
            }
        }

    }
    public void buildClinicJson(JsonGenerator jsonGenerator,  List<ClinicDataDto> clinicDataDtos) {
        for (ClinicDataDto clinic : clinicDataDtos) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(STATE, clinic.getState());
                jsonGenerator.writeStringField(LGA, clinic.getLga());
                jsonGenerator.writeStringField(FACILITY_NAME, clinic.getFacilityName());
                jsonGenerator.writeStringField(DATIM_ID, clinic.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, clinic.getPersonUuid());
                jsonGenerator.writeStringField(HOSPITAL_NUMBER, clinic.getHospitalNumber());
                //jsonGenerator.writeNullField();
                jsonGenerator.writeStringField(VISIT_DATE, dateUtility.ConvertDateToString(clinic.getVisitDate()));
                jsonGenerator.writeStringField(CLINICAL_STAGE, clinic.getClinicalStage());
                jsonGenerator.writeStringField(FUNCTIONAL_STATUS, clinic.getFunctionalStatus());
                jsonGenerator.writeStringField(TB_STATUS, clinic.getTbStatus());
                //jsonGenerator.writeStringField(DATE_OF_BIRTH, dateUtility.ConvertDateToString(clinic.getDateOfBirth()));
                //jsonGenerator.writeStringField(AGE, String.valueOf(clinic.getAge()));
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(clinic.getArchived()));
                jsonGenerator.writeStringField(SYSTOLIC, String.valueOf(clinic.getSystolic()));
                jsonGenerator.writeStringField(DIASTOLIC, String.valueOf(clinic.getDiastolic()));
                jsonGenerator.writeStringField(CLINIC_PREGNANCY_STATUS, clinic.getPregnancyStatus());
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(clinic.getLastModifiedDate()));
                jsonGenerator.writeStringField(NEXT_APPOINTMENT, dateUtility.ConvertDateToString(clinic.getNextAppointment()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, clinic.getLastModifiedBy());
                jsonGenerator.writeStringField(UUID, clinic.getUuid());
                jsonGenerator.writeStringField(HEPATITIS_SCREENING_RESULT, clinic.getHepatitisScreeningResult());
                jsonGenerator.writeStringField(BODY_WEIGHT, String.valueOf(clinic.getBodyWeight()));

                jsonGenerator.writeStringField(REGIMEN_TYPE, clinic.getRegimenType());
                jsonGenerator.writeStringField(REGIMEN, clinic.getRegimen());
                jsonGenerator.writeStringField(CD4_COUNT, clinic.getCd4Count());
                jsonGenerator.writeStringField(CD4_SEMI_QUANTITATIVE, clinic.getCd4SemiQuantitative());
                jsonGenerator.writeStringField(CD4_FLOW_CYTOMETRY, String.valueOf(clinic.getC4FlowCytometry()));
                jsonGenerator.writeStringField(CLINIC_CD4_TYPE, clinic.getCd4Type());
                jsonGenerator.writeStringField(IS_COMMENCEMENT, String.valueOf(clinic.getIsCommencement()));
                jsonGenerator.writeStringField(CD4, String.valueOf(clinic.getCd4()));
                jsonGenerator.writeStringField(CD4_PERCENTAGE, String.valueOf(clinic.getCd4Percentage()));
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating Clinic JSON: {}", e.getMessage());
                log.error("Clinic JSON: {}", clinic);
            }
        }

    }
    public void buildPatientJson(JsonGenerator jsonGenerator,  List<PatientDto> patientDtos) {
        for (PatientDto patient : patientDtos) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(STATE, patient.getState());
                jsonGenerator.writeStringField(LGA, patient.getLga());
                jsonGenerator.writeStringField(FACILITY_NAME, patient.getFacilityName());
                jsonGenerator.writeStringField(DATIM_ID, patient.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, patient.getPersonUuid());
                jsonGenerator.writeStringField(HOSPITAL_NUMBER, patient.getHospitalNumber());
                jsonGenerator.writeStringField(SURNAME, patient.getSurname());
                jsonGenerator.writeStringField(PATIENT_FIRST_NAME, patient.getFirstName());
                jsonGenerator.writeStringField(PATIENT_OTHER_NAME, patient.getOtherName());
                jsonGenerator.writeStringField(PATIENT_DATE_OF_BIRTH, dateUtility.ConvertDateToString(patient.getDateOfBirth()));
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(patient.getLastModifiedDate()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, patient.getLastModifiedBy());
                jsonGenerator.writeStringField(PATIENT_DATE_OF_REGISTRATION, dateUtility.ConvertDateToString(patient.getDateOfRegistration()));
                jsonGenerator.writeStringField(PATIENT_MARITAL_STATUS, patient.getMaritalStatus());
                jsonGenerator.writeStringField(PATIENT_EDUCATION, patient.getEducation());
                jsonGenerator.writeStringField(PATIENT_RESIDENTIAL_STATE, patient.getResidentialState());
                jsonGenerator.writeStringField(PATIENT_RESIDENTIAL_LGA, patient.getResidentialLga());
                jsonGenerator.writeStringField(TOWN, patient.getTown());
                jsonGenerator.writeStringField(SEX, patient.getSex());
                jsonGenerator.writeStringField(ADDRESS, patient.getAddress());
                jsonGenerator.writeStringField(OCCUPATION, patient.getOccupation());
                jsonGenerator.writeStringField(PHONE, patient.getPhone());
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(patient.getArchived()));
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                log.error("Error generating Patient JSON: {}", e.getMessage());
                log.error("Patient JSON: {}", patient);
            }
        }

    }
    public void buildLaboratoryOrderJson(JsonGenerator jsonGenerator,  List<LaboratoryOrderDto> laboratoryOrders) {
        for (LaboratoryOrderDto laboratoryOrder : laboratoryOrders) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(UUID, laboratoryOrder.getUuid());
                jsonGenerator.writeStringField(DATIM_ID, laboratoryOrder.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, laboratoryOrder.getPersonUuid());
                jsonGenerator.writeStringField(ORDER_DATE, dateUtility.ConvertDateTimeToString(laboratoryOrder.getOrderDate()));
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(laboratoryOrder.getDateModified()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, laboratoryOrder.getModifiedBy());
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(laboratoryOrder.getArchived()));
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                log.error("Error generating laboratoryOrder JSON: {}", e.getMessage());
                log.error("laboratoryOrder JSON: {}", laboratoryOrder);
            }
        }

    }
    public void buildLaboratorySampleJson(JsonGenerator jsonGenerator,  List<LaboratorySampleDto> laboratorySamples) {
        for (LaboratorySampleDto laboratorySample : laboratorySamples) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(UUID, laboratorySample.getUuid());
                jsonGenerator.writeStringField(TEST_ID, String.valueOf(laboratorySample.getTestId()));
                jsonGenerator.writeStringField(DATIM_ID, laboratorySample.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, laboratorySample.getPersonUuid());
                jsonGenerator.writeStringField(DATE_SAMPLE_COLLECTED, dateUtility.ConvertDateTimeToString(laboratorySample.getDateSampleCollected()));
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(laboratorySample.getDateModified()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, laboratorySample.getModifiedBy());
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(laboratorySample.getArchived()));
                jsonGenerator.writeStringField(SAMPLE_TYPE, laboratorySample.getSampleType());
                jsonGenerator.writeStringField(TEST_UUID, laboratorySample.getTestUuid());
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating laboratorySample JSON: {}", e.getMessage());
                log.error("laboratorySample JSON: {}", laboratorySample);
            }
        }

    }
    public void buildLaboratoryTestJson(JsonGenerator jsonGenerator,  List<LaboratoryTestDto> laboratoryTests) {
        for (LaboratoryTestDto laboratoryTest : laboratoryTests) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(UUID, laboratoryTest.getUuid());
                jsonGenerator.writeStringField(LAB_TEST_NAME, laboratoryTest.getLabTestName());
                jsonGenerator.writeStringField(GROUP_NAME, laboratoryTest.getGroupName());
                jsonGenerator.writeStringField(VIRAL_LOAD_INDICATION, laboratoryTest.getViralLoadIndication());
                jsonGenerator.writeStringField(UNIT_MEASUREMENT, laboratoryTest.getUnitMeasurement());
                jsonGenerator.writeStringField(DATIM_ID, laboratoryTest.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, laboratoryTest.getPersonUuid());
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(laboratoryTest.getDateModified()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, laboratoryTest.getModifiedBy());
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(laboratoryTest.getArchived()));
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating laboratoryTest JSON: {}", e.getMessage());
                log.error("laboratoryTest JSON: {}", laboratoryTest);
            }
        }
    }
    public void buildLaboratoryResultJson(JsonGenerator jsonGenerator,  List<LaboratoryResultDto> laboratoryResults) {
        for (LaboratoryResultDto laboratoryResult : laboratoryResults) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(UUID, laboratoryResult.getUuid());
                jsonGenerator.writeStringField(DATE_ASSAYED, dateUtility.ConvertDateTimeToString(laboratoryResult.getDateAssayed()));
                jsonGenerator.writeStringField(DATE_RESULT_REPORTED, dateUtility.ConvertDateTimeToString(laboratoryResult.getDateResultReported()));
                jsonGenerator.writeStringField(RESULT_REPORTED, laboratoryResult.getResultReported());
                jsonGenerator.writeStringField(DATIM_ID, laboratoryResult.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, laboratoryResult.getPersonUuid());
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(laboratoryResult.getDateModified()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, laboratoryResult.getModifiedBy());
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(laboratoryResult.getArchived()));
                jsonGenerator.writeStringField(TEST_ID, String.valueOf(laboratoryResult.getTestId()));
                jsonGenerator.writeStringField(LAB_TEST_NAME, laboratoryResult.getLabTestName());
                jsonGenerator.writeStringField(TEST_UUID, laboratoryResult.getTestUuid());

                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating laboratoryResult JSON: {}", e.getMessage());
                log.error("laboratoryResult JSON: {}", laboratoryResult);
            }
        }
    }
    public void buildPharmacyJson(JsonGenerator jsonGenerator,  List<PharmacyDto> pharmacies) {
        for (PharmacyDto pharmacy : pharmacies) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(UUID, pharmacy.getUuid());
                jsonGenerator.writeStringField(VISIT_DATE, dateUtility.ConvertDateToString(pharmacy.getVisitDate()));
                jsonGenerator.writeStringField(DATIM_ID, pharmacy.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, pharmacy.getPersonUuid());
                jsonGenerator.writeStringField(REGIME_NAME, pharmacy.getRegimenName());
                jsonGenerator.writeStringField(DURATION, pharmacy.getDuration());
                jsonGenerator.writeStringField(CODE_DESCRIPTION, pharmacy.getCodeDescription());
                jsonGenerator.writeStringField(VISIT_ID, pharmacy.getVisitId());
                jsonGenerator.writeStringField(MMD_TYPE, pharmacy.getMmdType());
                jsonGenerator.writeStringField(NEXT_APPOINTMENT, dateUtility.ConvertDateToString(pharmacy.getNextAppointment()));
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(pharmacy.getLastModifiedDate()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, pharmacy.getLastModifiedBy());
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(pharmacy.getArchived()));
                jsonGenerator.writeStringField(REGIMEN_LINE, pharmacy.getRegimenLine());

                jsonGenerator.writeStringField(DSD_MODEL_TYPE, pharmacy.getDsdModelType());
                jsonGenerator.writeStringField(PHARMACY_DSD_MODEL, pharmacy.getDsdModel());
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating pharmacy JSON: {}", e.getMessage());
                log.error("pharmacy JSON: {}", pharmacy);
            }
        }
    }
    public void buildBiometricJson(JsonGenerator jsonGenerator,  List<BiometricDto> biometrics) {
        //jsonGenerator.configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true);
        //jsonGenerator.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        for (BiometricDto biometric : biometrics) {
            try {

                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(ID, biometric.getUuid());
                jsonGenerator.writeStringField(DATIM_ID, biometric.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, biometric.getPersonUuid());
                jsonGenerator.writeStringField(DEVICE_NAME, biometric.getDeviceName());
                jsonGenerator.writeStringField(VERSION_ISO_20, String.valueOf(biometric.getVersionIso20()));
                jsonGenerator.writeStringField(IMAGE_QUALITY, String.valueOf(biometric.getImageQuality()));
                jsonGenerator.writeStringField(RECAPTURE, String.valueOf(biometric.getRecapture()));
                jsonGenerator.writeStringField(HASHED, "");
                jsonGenerator.writeStringField(COUNT, String.valueOf(biometric.getCount()));
                jsonGenerator.writeStringField(TEMPLATE, biometric.getTemplate());
                jsonGenerator.writeStringField(ISO, String.valueOf(biometric.getIso()));
                jsonGenerator.writeStringField(TEMPLATE_TYPE, biometric.getTemplateType());
                jsonGenerator.writeStringField(ENROLLMENT_DATE, dateUtility.ConvertDateToString(biometric.getEnrollmentDate()));
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(biometric.getLastModifiedDate()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, biometric.getLastModifiedBy());
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(biometric.getArchived()));
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating biometric JSON: {}", e.getMessage());
                log.error("biometric JSON: {}", biometric);
            }
        }
    }
    public void buildEnrollmentJson(JsonGenerator jsonGenerator,  List<EnrollmentDto> enrollments) {
        for (EnrollmentDto enrollment : enrollments) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(UUID, enrollment.getUuid());
                jsonGenerator.writeStringField(DATIM_ID, enrollment.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, enrollment.getPersonUuid());
                jsonGenerator.writeStringField(TARGET_GROUP, enrollment.getTargetGroup());
                jsonGenerator.writeStringField(UNIQUE_ID, enrollment.getUniqueId());
                jsonGenerator.writeStringField(OVC_NUMBER, enrollment.getOvcNumber());
                jsonGenerator.writeStringField(HOUSEHOLD_NUMBER, enrollment.getHouseHoldNumber());
                jsonGenerator.writeStringField(DATE_CONFIRMED_HIV, dateUtility.ConvertDateToString(enrollment.getDateConfirmedHiv()));
                jsonGenerator.writeStringField(DATE_OF_REGISTRATION, dateUtility.ConvertDateToString(enrollment.getDateOfRegistration()));
                jsonGenerator.writeStringField(DATE_STARTED, dateUtility.ConvertDateToString(enrollment.getDateStarted()));
                jsonGenerator.writeStringField(ENTRY_POINT, enrollment.getEntryPoint());
                jsonGenerator.writeStringField(ENROLLMENT_SETTING, enrollment.getEnrollmentSetting());
                jsonGenerator.writeStringField(FACILITY_NAME, enrollment.getFacilityName());
                jsonGenerator.writeStringField(PREGNANCY_STATUS, enrollment.getPregnancyStatus());
                jsonGenerator.writeStringField(TB_STATUS, enrollment.getTbStatus());
                jsonGenerator.writeStringField(STATUS_AT_REGISTRATION, enrollment.getStatusAtRegistration());
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(enrollment.getLastModifiedDate()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, enrollment.getLastModifiedBy());
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(enrollment.getArchived()));
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating enrollment JSON: {}", e.getMessage());
                log.error("enrollment JSON: {}", enrollment);
            }
        }
    }
    public void buildObservationJson(JsonGenerator jsonGenerator,  List<ObservationDto> observations) {
        for (ObservationDto observation : observations) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(UUID, observation.getUuid());
                jsonGenerator.writeStringField(DATIM_ID, observation.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, observation.getPersonUuid());
                jsonGenerator.writeStringField(DATA, observation.getData());
                jsonGenerator.writeStringField(TYPE, observation.getType());
                jsonGenerator.writeStringField(DATE_OF_OBSERVATION, dateUtility.ConvertDateToString(observation.getDateOfObservation()));
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(observation.getLastModifiedDate()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, observation.getLastModifiedBy());
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(observation.getArchived()));
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating observation JSON: {}", e.getMessage());
                log.error("observation JSON: {}", observation);
            }
        }
    }
    public void buildStatusTrackerJson(JsonGenerator jsonGenerator,  List<StatusTrackerDto> statusTrackerList) {
        for (StatusTrackerDto trackerDto : statusTrackerList) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(UUID, trackerDto.getUuid());
                jsonGenerator.writeStringField(DATIM_ID, trackerDto.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, trackerDto.getPersonUuid());
                jsonGenerator.writeStringField(HIV_STATUS, trackerDto.getHivStatus());
                jsonGenerator.writeStringField(STATUS_DATE, dateUtility.ConvertDateToString(trackerDto.getStatusDate()));
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(trackerDto.getLastModifiedDate()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, trackerDto.getLastModifiedBy());
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(trackerDto.getArchived()));

                jsonGenerator.writeStringField(CAUSE_OF_DEATH, trackerDto.getCauseOfDeath());
                jsonGenerator.writeStringField(VA_CAUSE_OF_DEATH, trackerDto.getVaCauseOfDeath());
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating status tracker JSON: {}", e.getMessage());
                log.error("status tracker JSON: {}", trackerDto);
            }
        }
    }
    public void buildEacJson(JsonGenerator jsonGenerator,  List<EacDto> eacs) {
        for (EacDto eac : eacs) {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(UUID, eac.getUuid());
                jsonGenerator.writeStringField(DATIM_ID, eac.getDatimId());
                jsonGenerator.writeStringField(PERSON_UUID, eac.getPersonUuid());
                jsonGenerator.writeStringField(BARRIERS_OTHERS, eac.getBarriersOthers());
                jsonGenerator.writeStringField(INTERVENTION_OTHERS, eac.getInterventionOthers());
                jsonGenerator.writeStringField(INTERVENTION, eac.getIntervention());
                jsonGenerator.writeStringField(BARRIERS, eac.getBarriers());
                jsonGenerator.writeStringField(EAC_SESSION_DATE, dateUtility.ConvertDateToString(eac.getEacSessionDate()));
                jsonGenerator.writeStringField(EAC_UUID, eac.getEacUuid());
                jsonGenerator.writeStringField(STATUS, eac.getStatus());
                jsonGenerator.writeStringField(ADHERENCE, eac.getAdherence());
                jsonGenerator.writeStringField(FOLLOW_UP_DATE, dateUtility.ConvertDateToString(eac.getFollowUpDate()));
                jsonGenerator.writeStringField(REFERRAL, eac.getReferral());
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(eac.getLastModifiedDate()));
                jsonGenerator.writeStringField(LAST_MODIFIED_BY, eac.getLastModifiedBy());
                jsonGenerator.writeStringField(ARCHIVED, String.valueOf(eac.getArchived()));
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating eac JSON: {}", e.getMessage());
                log.error("Eac JSON: {}", eac);
            }
        }
    }



}
