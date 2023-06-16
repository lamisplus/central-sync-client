package org.lamisplus.modules.central.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ExportService {
    //String bulkExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate);

    String bulkExport(Long facilityId);
    boolean radetExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate);
    boolean htsExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate);
    boolean prepExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate);
    String getDatimId(Long facilityId);
    boolean clinicExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);
    boolean patientExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);
    boolean laboratoryOrderExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);
    boolean laboratorySampleExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);

    boolean laboratoryTestExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);

    boolean laboratoryResultExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);

}
