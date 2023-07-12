package org.lamisplus.modules.central.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ExportService {
    //String bulkExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate);

    String bulkExport(Long facilityId, Boolean current);
    boolean extractExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate, String period);
    boolean htsExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate, String period);
    boolean prepExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate, String period);
    String getDatimId(Long facilityId);
    boolean clinicExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);
    boolean patientExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);
    boolean laboratoryOrderExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);
    boolean laboratorySampleExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);
    boolean laboratoryTestExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);
    boolean laboratoryResultExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);
    boolean pharmacyExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate);
    boolean biometricExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate);
    boolean enrollmentExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate);
    boolean observationExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate);
    boolean statusTrackerExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate);
    boolean eacExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate);

}
