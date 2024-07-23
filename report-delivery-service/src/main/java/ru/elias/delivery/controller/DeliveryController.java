package ru.elias.delivery.controller;

import java.util.Objects;
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
        var response = client.getReportByFilename(fileName, format);
        var body = Objects.requireNonNull(response.getBody());
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
        var response = client.getReportByFilename(fileName, format);
        var body = Objects.requireNonNull(response.getBody());
        emailService.sendEmailWithAttachment(email, "Your Report", "Please find the attached report.", body, "report.pdf");
    }

}
