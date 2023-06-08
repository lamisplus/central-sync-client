package org.lamisplus.modules.central.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.lamisplus.modules.central.service.ExportService;
import org.lamisplus.modules.central.utility.ConstantUtility;
import org.lamisplus.modules.central.utility.FileUtility;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/export")
public class ExportController {
    private final FileUtility fileUtility;
    private final ExportService exportService;

    @GetMapping("/all")
    public void downloadFile(@RequestParam Long facilityId,
                             @RequestParam String startDate,
                             @RequestParam String endDate,
                             HttpServletResponse response) throws IOException, IllegalAccessException {
        if (facilityId == null || startDate == null || endDate == null)   {
            throw new IllegalAccessException("Invalid request parameters");
        }

        LocalDate reportStartDate = startDate != null ? LocalDate.parse(startDate) : LocalDate.of(2000, 1, 1);
        LocalDate reportEndDate = endDate != null ? LocalDate.parse(endDate) : LocalDate.now();
        String zipFileName = exportService.bulkExport(facilityId, reportStartDate, reportEndDate);
        if (!zipFileName.equals("None")) {
            try (InputStream is = Files.newInputStream(Paths.get(ConstantUtility.TEMP_BATCH_DIR + zipFileName))) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                IOUtils.copy(is, byteArrayOutputStream);
                download(response, byteArrayOutputStream, zipFileName);
            } catch (IOException ignored) {
            }
        }
    }

    private void download(HttpServletResponse response, ByteArrayOutputStream byteArrayOutputStream, String fileName) throws IOException {
        response.setHeader(ConstantUtility.CONTENT_DISPOSITION,  ConstantUtility.ATTACHMENT_FILENAME + fileName);
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Length", Integer.toString(byteArrayOutputStream.size()));
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.close();
        response.flushBuffer();
    }

    @SneakyThrows
    @PostConstruct
    public void initialize() {
        new File(ConstantUtility.TEMP_BATCH_DIR).mkdirs();
        File directory = new File(ConstantUtility.TEMP_BATCH_DIR);

        if (!directory.exists()) {
            return;
        } else {
            FileUtils.cleanDirectory(directory);
        }
    }

}
