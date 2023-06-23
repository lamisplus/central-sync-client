package org.lamisplus.modules.central.service;

import com.fasterxml.jackson.core.JsonGenerator;
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

    public static final String RADET_DATE_OF_BIRTH1 = "DateOfBirth";
    public static final String RADET_AGE = "Age";
    public static final String RADET_GENDER = "Gender";
    public static final String RADET_TARGET_GROUP1 = "TargetGroup";
    private final DateUtility dateUtility;

    public void buildHtsJson(JsonGenerator jsonGenerator, List<HtsReportDto> htsList, String period) throws IOException {
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
                jsonGenerator.writeStringField(UUID, hts.getUuid());
                jsonGenerator.writeStringField(PERIOD, period);
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating HTS JSON: {}", e.getMessage());
                log.error("HTS JSON: {}", hts);
            }
        }

    }

    public void buildRadetJson(JsonGenerator jsonGenerator, List<RadetReportDto> radetList, String period) throws IOException {
        for (RadetReportDto radet : radetList)
        {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField(ConstantUtility.STATE, radet.getState());
                jsonGenerator.writeStringField(ConstantUtility.LGA, radet.getLga());
                jsonGenerator.writeStringField(ConstantUtility.FACILITY_NAME, radet.getFacilityName());
                jsonGenerator.writeStringField(ConstantUtility.DATIM_ID, radet.getDatimId());
                jsonGenerator.writeStringField(ConstantUtility.PERSON_UUID, radet.getPersonUuid());
                jsonGenerator.writeStringField(ConstantUtility.HOSPITAL_NUMBER, radet.getHospitalNumber());
                jsonGenerator.writeStringField(RADET_DATE_OF_BIRTH1, radet.getDateOfBirth().toString());
                jsonGenerator.writeStringField(RADET_AGE, radet.getAge().toString());
                jsonGenerator.writeStringField(RADET_GENDER, radet.getGender());
                jsonGenerator.writeStringField(RADET_TARGET_GROUP1, radet.getTargetGroup());
                jsonGenerator.writeStringField("EnrollmentSetting", radet.getEnrollmentSetting());
                jsonGenerator.writeStringField("ArtStartDate", radet.getArtStartDate().toString());
                jsonGenerator.writeStringField("RegimenAtStart", radet.getRegimenAtStart());
                jsonGenerator.writeStringField("RegimenLineAtStart", radet.getRegimenLineAtStart());
                jsonGenerator.writeStringField("PregnancyStatus", radet.getPregnancyStatus());
                jsonGenerator.writeStringField("CurrentClinicalStage", radet.getCurrentClinicalStage());
                jsonGenerator.writeStringField("CurrentWeight", String.valueOf(radet.getCurrentWeight()));
                jsonGenerator.writeStringField("ViralLoadIndication", radet.getViralLoadIndication());
                jsonGenerator.writeStringField("DateOfViralLoadSampleCollection", dateUtility.ConvertDateToString(radet.getDateOfViralLoadSampleCollection()));
                jsonGenerator.writeStringField("CurrentViralLoad", radet.getCurrentViralLoad());
                jsonGenerator.writeStringField("DateOfCurrentViralLoad", dateUtility.ConvertDateToString(radet.getDateOfCurrentViralLoad()));
                jsonGenerator.writeStringField("DateOfCurrentViralLoadSample", dateUtility.ConvertDateToString(radet.getDateOfCurrentViralLoadSample()));
                jsonGenerator.writeStringField("LastCd4Count", radet.getLastCd4Count());
                jsonGenerator.writeStringField("DateOfLastCd4Count", dateUtility.ConvertDateToString(radet.getDateOfLastCd4Count()));
                jsonGenerator.writeStringField("CurrentRegimenLine", radet.getCurrentRegimenLine());
                jsonGenerator.writeStringField("CurrentARTRegimen", radet.getCurrentARTRegimen());
                jsonGenerator.writeStringField("MonthsOfARVRefill", String.valueOf(radet.getMonthsOfARVRefill()));
                jsonGenerator.writeStringField("LastPickupDate", dateUtility.ConvertDateToString(radet.getLastPickupDate()));
                jsonGenerator.writeStringField("NextPickupDate", String.valueOf(radet.getNextPickupDate()));
                jsonGenerator.writeStringField("CurrentStatusDate", dateUtility.ConvertDateToString(radet.getCurrentStatusDate()));
                jsonGenerator.writeStringField("getCurrentStatus", radet.getCurrentStatus());
                jsonGenerator.writeStringField("PreviousStatusDate", dateUtility.ConvertDateToString(radet.getPreviousStatusDate()));
                jsonGenerator.writeStringField("PreviousStatus", radet.getPreviousStatus());
                jsonGenerator.writeStringField("DateBiometricsEnrolled", dateUtility.ConvertDateToString(radet.getDateBiometricsEnrolled()));
                jsonGenerator.writeStringField("NumberOfFingersCaptured", String.valueOf(radet.getNumberOfFingersCaptured()));
                jsonGenerator.writeStringField("DateOfCommencementOfEAC", dateUtility.ConvertDateToString(radet.getDateOfCommencementOfEAC()));
                jsonGenerator.writeStringField("NumberOfEACSessionCompleted", String.valueOf(radet.getNumberOfEACSessionCompleted()));
                jsonGenerator.writeStringField("DateOfLastEACSessionCompleted", dateUtility.ConvertDateToString(radet.getDateOfLastEACSessionCompleted()));
                jsonGenerator.writeStringField("DateOfExtendEACCompletion", dateUtility.ConvertDateToString(radet.getDateOfExtendEACCompletion()));
                jsonGenerator.writeStringField("DateOfRepeatViralLoadResult", dateUtility.ConvertDateTimeToString(radet.getDateOfRepeatViralLoadResult()));
                jsonGenerator.writeStringField("DateOfRepeatViralLoadEACSampleCollection", dateUtility.ConvertDateTimeToString(radet.getDateOfRepeatViralLoadEACSampleCollection()));
                jsonGenerator.writeStringField("RepeatViralLoadResult", radet.getRepeatViralLoadResult());
                jsonGenerator.writeStringField("TbStatus", radet.getTbStatus());
                jsonGenerator.writeStringField("DateOfTbScreened", dateUtility.ConvertDateToString(radet.getDateOfTbScreened()));
                jsonGenerator.writeStringField("DateOfCurrentRegimen", dateUtility.ConvertDateToString(radet.getDateOfCurrentRegimen()));
                jsonGenerator.writeStringField("DateOfIptStart", dateUtility.ConvertDateToString(radet.getDateOfIptStart()));
                jsonGenerator.writeStringField("IptCompletionDate", dateUtility.ConvertDateToString(radet.getIptCompletionDate()));
                jsonGenerator.writeStringField("IptType", radet.getIptType());
                jsonGenerator.writeStringField("ResultOfCervicalCancerScreening", radet.getResultOfCervicalCancerScreening());
                jsonGenerator.writeStringField("CervicalCancerScreeningType", radet.getCervicalCancerScreeningType());
                jsonGenerator.writeStringField("CervicalCancerScreeningMethod", radet.getCervicalCancerScreeningMethod());
                jsonGenerator.writeStringField("CervicalCancerTreatmentScreened", radet.getCervicalCancerTreatmentScreened());
                jsonGenerator.writeStringField("DateOfCervicalCancerScreening", dateUtility.ConvertDateToString(radet.getDateOfCervicalCancerScreening()));
                jsonGenerator.writeStringField("OvcNumber", radet.getOvcNumber());
                jsonGenerator.writeStringField("HouseholdNumber", radet.getHouseholdNumber());
                jsonGenerator.writeStringField("CareEntry", radet.getCareEntry());
                jsonGenerator.writeStringField("CauseOfDeath", radet.getCauseOfDeath());
                jsonGenerator.writeStringField("VlEligibilityStatus", radet.getVlEligibilityStatus() + "");
                jsonGenerator.writeStringField("DateOfVlEligibilityStatu", dateUtility.ConvertDateToString(radet.getDateOfVlEligibilityStatus()));
                jsonGenerator.writeStringField("TbDiagnosticTestType", radet.getTbDiagnosticTestType());
                jsonGenerator.writeStringField("DateOfTbSampleCollection", dateUtility.ConvertDateToString(radet.getDateOfTbSampleCollection()));
                jsonGenerator.writeStringField("TbDiagnosticResult", radet.getTbDiagnosticResult());
                jsonGenerator.writeStringField("DsdModel", radet.getDsdModel());
                jsonGenerator.writeStringField("DateOfTbDiagnosticResultReceived", dateUtility.ConvertDateToString(radet.getDateOfTbDiagnosticResultReceived()));
                jsonGenerator.writeStringField("TbTreatementType", radet.getTbTreatementType());
                jsonGenerator.writeStringField("TbTreatmentOutcome", radet.getTbTreatmentOutcome());
                jsonGenerator.writeStringField("TbTreatmentStartDate", dateUtility.ConvertDateToString(radet.getTbTreatmentStartDate()));
                jsonGenerator.writeStringField("TbCompletionDate", dateUtility.ConvertDateToString(radet.getTbCompletionDate()));
                jsonGenerator.writeStringField("IptCompletionStatus()", radet.getIptCompletionStatus());

                jsonGenerator.writeStringField(PERIOD, period);
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating radet JSON: {}", e.getMessage());
                log.error("radet JSON: {}", radet);
            }
        }

    }

    public void buildPrepJson(JsonGenerator jsonGenerator,  List<PrepReportDto> prepList, String period) throws IOException {
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
                jsonGenerator.writeStringField(PERIOD, period);
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating PrEP JSON: {}", e.getMessage());
                log.error("prep JSON: {}", prep);
            }
        }

    }

    public void buildClinicJson(JsonGenerator jsonGenerator,  List<ClinicDataDto> clinicDataDtos) throws IOException {
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
                jsonGenerator.writeStringField(BODY_WEIGHT, String.valueOf(clinic.getBodyWeight()));
                jsonGenerator.writeEndObject();
            } catch (IOException e) {
                log.error("Error generating Clinic JSON: {}", e.getMessage());
                log.error("Clinic JSON: {}", clinic);
            }
        }

    }


    public void buildPatientJson(JsonGenerator jsonGenerator,  List<PatientDto> patientDtos) throws IOException {
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

    public void buildLaboratoryOrderJson(JsonGenerator jsonGenerator,  List<LaboratoryOrderDto> laboratoryOrders) throws IOException {
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

    public void buildLaboratorySampleJson(JsonGenerator jsonGenerator,  List<LaboratorySampleDto> laboratorySamples) throws IOException {
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
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating laboratorySample JSON: {}", e.getMessage());
                log.error("laboratorySample JSON: {}", laboratorySample);
            }
        }

    }

    public void buildLaboratoryTestJson(JsonGenerator jsonGenerator,  List<LaboratoryTestDto> laboratoryTests) throws IOException {
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

    public void buildLaboratoryResultJson(JsonGenerator jsonGenerator,  List<LaboratoryResultDto> laboratoryResults) throws IOException {
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
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating laboratoryResult JSON: {}", e.getMessage());
                log.error("laboratoryResult JSON: {}", laboratoryResult);
            }
        }
    }
    public void buildPharmacyJson(JsonGenerator jsonGenerator,  List<PharmacyDto> pharmacies) throws IOException {
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
                jsonGenerator.writeEndObject();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error generating pharmacy JSON: {}", e.getMessage());
                log.error("pharmacy JSON: {}", pharmacy);
            }
        }
    }

    public void buildBiometricJson(JsonGenerator jsonGenerator,  List<BiometricDto> biometrics) throws IOException {
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
                jsonGenerator.writeStringField(HASHED, String.valueOf(biometric.getHashed()));
                jsonGenerator.writeStringField(COUNT, String.valueOf(biometric.getCount()));
                jsonGenerator.writeStringField(TEMPLATE, String.valueOf(biometric.getTemplate()));
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

}
