package org.lamisplus.modules.central.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "central_prep")
public class CentralPrep {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "personuuid")
    private String personUuid;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "hospitalnumber")
    private String hospitalNumber;

    @Column(name = "surname")
    private String surname;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "hivenrollmentdate")
    private LocalDate hivEnrollmentDate;

    @Column(name = "age")
    private double age;

    @Column(name = "othername")
    private String otherName;

    @Column(name = "sex")
    private String sex;

    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;

    @Column(name = "dateofregistration")
    private LocalDate dateOfRegistration;

    @Column(name = "maritalstatus")
    private String maritalStatus;

    @Column(name = "education")
    private String education;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "facilityname")
    private String facilityName;

    @Column(name = "lga")
    private String lga;

    @Column(name = "state")
    private String state;

    @Column(name = "datimid")
    private String datimId;

    @Column(name = "residentialstate")
    private String residentialState;

    @Column(name = "residentiallga")
    private String residentialLga;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "baselineregimen")
    private String baselineRegimen;

    @Column(name = "baselinesystolicbp")
    private double baselineSystolicBp;

    @Column(name = "baselinediastolicbp")
    private double baselineDiastolicBp;

    @Column(name = "baselinetweight")
    private double baselineTWeight;

    @Column(name = "baselineheight")
    private double baselineHeight;

    @Column(name = "targetgroup")
    private String targetGroup;

    @Column(name = "prepcommencementdate")
    private LocalDate prepCommencementDate;

    @Column(name = "baselineurinalysis")
    private String baselineUrinalysis;

    @Column(name = "baselineurinalysisdate")
    private LocalDate baselineUrinalysisDate;

    @Column(name = "baselinecreatinine")
    private String baselineCreatinine;

    @Column(name = "baselinehepatitisb")
    private String baselineHepatitisB;

    @Column(name = "baselinehepatitisc")
    private String baselineHepatitisC;

    @Column(name = "interruptionreason")
    private String interruptionReason;

    @Column(name = "interruptiondate")
    private LocalDate interruptionDate;

    @Column(name = "hivstatusatprepinitiation")
    private String hivStatusAtPrepInitiation;

    @Column(name = "indicationforprep")
    private String indicationForPrep;

    @Column(name = "currentregimen")
    private String currentRegimen;

    @Column(name = "dateoflastpickup")
    private LocalDate dateOfLastPickup;

    @Column(name = "currentsystolicbp")
    private double currentSystolicBp;

    @Column(name = "currentdiastolicbp")
    private double currentDiastolicBp;

    @Column(name = "currentweight")
    private double currentWeight;

    @Column(name = "currentheight")
    private double currentHeight;

    @Column(name = "currenturinalysis")
    private String currentUrinalysis;

    @Column(name = "currenturinalysisdate")
    private LocalDate currentUrinalysisDate;

    @Column(name = "currenthivstatus")
    private String currentHivStatus;

    @Column(name = "dateofcurrenthivstatus")
    private LocalDate dateOfCurrentHivStatus;

    @Column(name = "pregnancystatus")
    private String pregnancyStatus;

    @Column(name = "currentstatus")
    private String currentStatus;

    @Column(name = "dateofcurrentstatus")
    private LocalDate dateOfCurrentStatus;

    @Column(name = "prepuniqueno")
    private String prepUniqueNo;

    @Column(name = "period")
    private String period;

    @Column(name = "submissiontime")
    private String submissionTime;
}
