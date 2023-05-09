package org.lamisplus.modules.central.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.central.service.ExportService;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ExportController {

    private final String EXPORT_URL_VERSION_ONE = "/api/v1/central-sync-export";
    private final SimpMessageSendingOperations messagingTemplate;
    private final ExportService exportService;


    @GetMapping(EXPORT_URL_VERSION_ONE + "/patient")
    public void exportBiometricData(HttpServletResponse response, @RequestParam("tableName") String tableName,
                                    @RequestParam("facilityId") Long facilityId,
                                    @RequestParam("userName") String userName) throws IOException {
        messagingTemplate.convertAndSend("/topic/patient", "start");
        ByteArrayOutputStream baos = exportService.generatePersonData(response, tableName, facilityId, userName);
        setStream(baos, response);
        messagingTemplate.convertAndSend("/topic/patient", "end");
    }

    private void setStream(ByteArrayOutputStream baos, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Length", Integer.toString(baos.size()));
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(baos.toByteArray());
        outputStream.close();
        response.flushBuffer();
    }
}
