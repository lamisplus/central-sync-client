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
    public boolean clinicExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);
    public boolean patientExport(Long facilityId, LocalDateTime reportStartDate, LocalDateTime reportEndDate);

}
