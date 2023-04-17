package org.lamisplus.modules.central.domain.dto;

public interface SyncTable {
    int getpatient();
    int getpatient_visit();
    int gettriage_vital_sign();
    int gethiv_enrollment();
    int gethiv_art_clinical();
    int gethiv_art_pharmacy();
    int getlaboratory_order();
    int getlaboratory_test();
    int getlaboratory_sample();
    int getlaboratory_result();
    int getbiometric();
    int gethiv_status_tracker();
    int gethiv_eac();
    int gethiv_eac_session();
    int gethiv_eac_out_come();
    int gethiv_observation();
}
