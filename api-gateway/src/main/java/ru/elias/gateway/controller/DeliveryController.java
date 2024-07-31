package ru.elias.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.gateway.service.client.ConfigControlServiceClient;
import ru.elias.gateway.service.client.ReportDeliveryServiceClient;
import ru.elias.gateway.domain.dto.report.ReportFormat;

@Tag(name = "Operations about report delivery")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private final ConfigControlServiceClient configClient;
    private final ReportDeliveryServiceClient deliveryClient;

    @Operation(summary = "Download report")
    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadReport(@PathVariable String fileName) {
        log.info("GET /delivery/download/{}", fileName);
        var format = getFormat(fileName);
        log.debug("Downloading report with format: {}", format);
        return deliveryClient.downloadReport(fileName, format);
    }

    @Operation(summary = "Send report via email")
    @PostMapping("/send-email/{fileName}")
    public void sendEmail(@PathVariable String fileName, @RequestParam String email) {
        log.info("POST /delivery/send-email/{}, param {}", fileName, email);
        var format = getFormat(fileName);
        log.debug("Sending report {} with format {} to email {}", fileName, format, email);
        deliveryClient.sendEmail(fileName, format, email);
    }

    private ReportFormat getFormat(String fileName) {
        log.debug("Getting ReportFormat for report {}", fileName);
        var config = configClient.getConfig(fileName);
        var format = ReportFormat.getFormat(config.extension());
        log.debug("Format for report {} is {}", fileName, format);
        return format;
    }

}
