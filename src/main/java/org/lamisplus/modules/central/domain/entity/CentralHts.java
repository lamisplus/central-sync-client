package org.lamisplus.modules.central.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "central_hts")
public class CentralHts {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clientcode")
    private String clientCode;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "othername")
    private String otherName;

    @Column(name = "sex")
    private String sex;

    @Column(name = "age")
    private int age;

    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "maritalstatus")
    private String maritalStatus;

    @Column(name = "lgaofresidence")
    private String lgaOfResidence;

    @Column(name = "stateofresidence")
    private String stateOfResidence;

    @Column(name = "facility")
    private String facility;

    @Column(name = "state")
    private String state;

    @Column(name = "lga")
    private String lga;

    @Column(name = "patientid")
    private String patientId;

    @Column(name = "education")
    private String education;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "datimcode")
    private String datimCode;

    @Column(name = "htslatitude")
    private String htsLatitude;

    @Column(name = "htslongitude")
    private String htsLongitude;

    @Column(name = "clientaddress")
    private String clientAddress;

    @Column(name = "datevisit")
    private LocalDate dateVisit;

    @Column(name = "firsttimevisit")
    private String firstTimeVisit;

    @Column(name = "numberofchildren")
    private int numberOfChildren;

    @Column(name = "numberofwives")
    private int numberOfWives;

    @Column(name = "indexclient")
    private String indexClient;

    @Column(name = "prepoffered")
    private boolean prepOffered;

    @Column(name = "prepaccepted")
    private boolean prepAccepted;

    @Column(name = "previouslytested")
    private String previouslyTested;

    @Column(name = "targetgroup")
    private String targetGroup;

    @Column(name = "referredfrom")
    private String referredFrom;

    @Column(name = "testingsetting")
    private String testingSetting;

    @Column(name = "counselingtype")
    private String counselingType;

    @Column(name = "pregnancystatus")
    private String pregnancyStatus;

    @Column(name = "breastfeeding")
    private boolean breastfeeding;

    @Column(name = "indextype")
    private String indexType;

    @Column(name = "ifrecencytestingoptin")
    private String recencyTestingOptIn;

    @Column(name = "recencyid")
    private String recencyId;

    @Column(name = "recencytesttype")
    private String recencyTestType;

    @Column(name = "recencytestdate")
    private LocalDate recencyTestDate;

    @Column(name = "recencyinterpretation")
    private String recencyInterpretation;

    @Column(name = "finalrecencyresult")
    private String finalRecencyResult;

    @Column(name = "viralloadresult")
    private String viralLoadResult;

    @Column(name = "viralloadsamplecollectiondate")
    private LocalDate viralLoadSampleCollectionDate;

    @Column(name = "viralloadconfirmationresult")
    private String viralLoadConfirmationResult;

    @Column(name = "viralloadconfirmationdate")
    private LocalDate viralLoadConfirmationDate;

    @Column(name = "assessmentcode")
    private String assessmentCode;

    @Column(name = "modality")
    private String modality;

    @Column(name = "syphilistestresult")
    private String syphilisTestResult;

    @Column(name = "hepatitisbtestresult")
    private String hepatitisBTestResult;

    @Column(name = "hepatitisctestresult")
    private String hepatitisCTestResult;

    @Column(name = "cd4type")
    private String cd4Type;

    @Column(name = "cd4testresult")
    private String cd4TestResult;

    @Column(name = "hivtestresult")
    private String hivTestResult;

    @Column(name = "finalhivtestresult")
    private String finalHivTestResult;

    @Column(name = "dateofhivtesting")
    private LocalDate dateOfHivTesting;

    @Column(name = "numberofcondomsgiven")
    private String numberOfCondomsGiven;

    @Column(name = "numberoflubricantsgiven")
    private String numberOfLubricantsGiven;

    @Column(name = "htsuniqueno")
    private String htsUniqueNo;

    @Column(name = "period")
    private String period;

    @Column(name = "submissiontime")
    private String submissionTime;
}
