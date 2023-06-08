package org.lamisplus.modules.central.service;

import java.time.LocalDate;

public interface ExportService {
    String bulkExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate);
    boolean radetExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate);
    boolean htsExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate);
    boolean prepExport(Long facilityId, LocalDate reportStartDate, LocalDate reportEndDate);
}
