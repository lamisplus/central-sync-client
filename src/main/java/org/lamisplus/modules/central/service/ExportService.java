package org.lamisplus.modules.central.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ExportService {
    //String bulkExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate);

    String bulkExport(Long facilityId, Boolean current);

    boolean exportAnyTable(String tableName, Long facilityId, String startName, String startDate, String endName, String endDate, String fileLocation);

    boolean extractExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate, String period, String fileLocation);
    boolean htsExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate, String period, String fileLocation);
    boolean prepExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate, String period, String fileLocation);
    String getDatimId(Long facilityId);
    boolean clinicExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate, String fileLocation);
    boolean patientExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate, String fileLocation);
    boolean laboratoryOrderExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate, String fileLocation);
    boolean laboratorySampleExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate, String fileLocation);
    boolean laboratoryTestExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate, String fileLocation);
    boolean laboratoryResultExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate, String fileLocation);
    boolean pharmacyExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation);
    boolean biometricExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation);
    boolean enrollmentExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation);
    boolean observationExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation);
    boolean statusTrackerExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation);
    boolean eacExport(Long facilityId, LocalDateTime startDate, LocalDateTime endDate, String fileLocation);

}
