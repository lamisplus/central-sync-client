package org.lamisplus.modules.central.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "central_radet")
//@IdClass(CentralRadetPK.class)
public class CentralRadet implements Serializable, Persistable<Long> {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String personUuid;
    String hospitalNumber;
    Double age;
    String gender;
    LocalDate dateOfBirth;
    String facilityName;
    String lga;
    String state;
    String datimId;
    String targetGroup;
    String enrollmentSetting;
    LocalDate artStartDate;
    String regimenAtStart;
    String ovcUniqueId;
    String houseHoldUniqueNo;
    String careEntry;
    String regimenLineAtStart;
    LocalDate dateOfViralLoadSampleCollection;
    LocalDate dateOfCurrentViralLoadSample;
    String viralLoadIndication;
    String currentViralLoad;
    LocalDate dateOfCurrentViralLoad;
    String dsdModel;
    LocalDate lastPickupDate;
    String currentArtRegimen;
    String currentRegimenLine;
    LocalDate nextPickupDate;
    Double monthsOfArvRefill;
    String personUuid60;
    LocalDate dateBiometricsEnrolled;
    //Next Phase
    Double numberOfFingersCaptured;
    Double currentWeight;
    String tbStatus;
    String currentClinicalStage;
    String pregnancyStatus;
    LocalDate dateOfTbScreened;
    String personUuid50;
    LocalDate dateOfCommencementOfEac;
    Double numberOfEacSessionCompleted;
    LocalDate dateOfLastEacSessionCompleted;
    LocalDate dateOfExtendEacCompletion;
    LocalDate dateOfRepeatViralLoadResult;
    String repeatViralLoadResult;
    LocalDate dateOfCurrentRegimen;
    LocalDate dateOfIptStart;
    LocalDate iptCompletionDate;
    String iptCompletionStatus;
    String iptType;
    LocalDate dateOfCervicalCancerScreening;
    String cervicalCancerScreeningType;
    String cervicalCancerScreeningMethod;
    String cervicalCancerTreatmentScreened;
    String resultOfCervicalCancerScreening;
    //Next Phase
    String ovcNumber;
    String houseHoldNumber;
    String tbTreatementType;
    LocalDate tbTreatmentStartDate;
    String tbTreatmentOutcome;
    LocalDate tbCompletionDate;
    String createdBy;
    LocalDate dateOfTbSampleCollection;
    String personTbSample;
    String personTbResult;
    LocalDate dateOfTbDiagnosticResultReceived;
    String tbDiagnosticResult;
    String tbDiagnosticTestType;
    String causeOfDeath;
    String previousStatus;
    LocalDate previousStatusDate;
    LocalDate currentStatusDate;
    String currentStatus;
    Boolean vlEligibilityStatus;
    Integer test;
    LocalDate dateOfVlEligibilityStatus;
    String lastCd4Count;
    LocalDate dateOfLastCd4Count;
    String radetUniqueNo;
    String period;
    String submissionTime;
    /*@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")*/

    //    private Long id;
//
//    @Id
//    @Column(name = "period")
//    private String period;
    @Override
    public boolean isNew() {
        return id == null;
    }
}
