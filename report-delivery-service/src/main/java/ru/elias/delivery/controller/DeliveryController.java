package ru.elias.delivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.delivery.domain.ReportFormat;
import ru.elias.delivery.error.exception.ReportNotFoundException;
import ru.elias.delivery.service.EmailService;
import ru.elias.delivery.service.client.ReportDataServiceClient;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private final ReportDataServiceClient client;
    private final EmailService emailService;

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadReport(@PathVariable String fileName,
                                                            @RequestParam ReportFormat format) {
        log.info("Downloading report: fileName={}, format={}", fileName, format);
        var response = client.getReportByFilename(fileName, format);
        var body = validateReportBody(response, fileName, format);
        log.info("Successfully downloaded report: fileName={}, format={}", fileName, format);
        return ResponseEntity.ok()
                .headers(response.getHeaders())
                .contentLength(body.length)
                .body(new ByteArrayResource(body));
    }

    @PostMapping("/send-email/{fileName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendReportByEmail(@PathVariable String fileName,
                                  @RequestParam ReportFormat format,
                                  @RequestParam String email) {
        log.info("Sending report by email: fileName={}, format={}, email={}", fileName, format, email);
        var response = client.getReportByFilename(fileName, format);
        var body = validateReportBody(response, fileName, format);
        emailService.sendEmailWithAttachment(email, body, fileName.concat(format.getExtension()));
    }

    private byte[] validateReportBody(ResponseEntity<byte[]> response, String fileName, ReportFormat format) {
        var body = response.getBody();
        if (body == null) {
            log.error("Failed to download report: body is null for fileName={}, format={}", fileName, format);
            throw new ReportNotFoundException("Failed to download report, report not found");
        }
        return body;
    }

}
