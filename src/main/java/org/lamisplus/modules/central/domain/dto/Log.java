package org.lamisplus.modules.central.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Log {
    private String name;
    private String error;
    private String others;
    private String category;
    private LocalDateTime timeCreated;
}
