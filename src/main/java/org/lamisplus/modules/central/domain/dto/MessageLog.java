package org.lamisplus.modules.central.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageLog {
    private String name;
    private String message;
    private String others;
    private String activity;
    private MessageType category;
    private LocalDateTime timeCreated;
}
