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

    public void buildHtsJson(JsonGenerator jsonGenerator, List<HtsReportDto> htsList) throws IOException {
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
                log.error("HTS JSON: {}", hts);
            }
        }

    }

    public void buildRadetJson(JsonGenerator jsonGenerator, List<RadetReportDto> radetList) throws IOException {
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
                log.error("radet JSON: {}", radet);
            }
        }

    }

    public void buildPrepJson(JsonGenerator jsonGenerator,  List<PrepReportDto> prepList) throws IOException {
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
                log.info("clinic.getVisitDate() {}", clinic.getVisitDate());
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
                log.info("clinic.getLastModifiedDate() {}", clinic.getLastModifiedDate());
                jsonGenerator.writeStringField(LAST_MODIFIED_DATE, dateUtility.ConvertDateTimeToString(clinic.getLastModifiedDate()));
                log.info("clinic.getNextAppointment() {}", clinic.getNextAppointment());
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


}
