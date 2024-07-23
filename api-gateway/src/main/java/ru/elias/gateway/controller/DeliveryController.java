package ru.elias.gateway.controller;

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
import ru.elias.gateway.domain.dto.ReportFormat;
import ru.elias.gateway.service.client.ConfigControlServiceClient;
import ru.elias.gateway.service.client.ReportDeliveryServiceClient;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private final ConfigControlServiceClient configClient;
    private final ReportDeliveryServiceClient deliveryClient;

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadReport(@PathVariable String fileName) {
        var format = getFormat(fileName);
        return deliveryClient.downloadReport(fileName, format);
    }

    @PostMapping("/send-email/{fileName}")
    public void sendEmail(@PathVariable String fileName,
                          @RequestParam String email) {
        var format = getFormat(fileName);
        deliveryClient.sendEmail(fileName, format, email);
    }

    private ReportFormat getFormat(String fileName) {
        var config = configClient.getConfig(fileName);
        return ReportFormat.getFormat(config.extension());
    }

}
