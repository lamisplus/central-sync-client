package org.lamisplus.modules.central.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Quarter {
    private LocalDate startDate;
    private LocalDate endDate;
    private String name;
}
