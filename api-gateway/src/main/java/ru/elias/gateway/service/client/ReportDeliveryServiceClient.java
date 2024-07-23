package ru.elias.gateway.service.client;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.elias.gateway.domain.dto.Config;
import ru.elias.gateway.domain.dto.ReportFormat;

@HttpExchange
public interface ReportDeliveryServiceClient {

    @GetExchange("/download/{fileName}")
    ResponseEntity<ByteArrayResource> downloadReport(@PathVariable String fileName, @RequestParam ReportFormat format);

    @PostExchange("/send-email/{fileName}")
    void sendEmail(@PathVariable String fileName, @RequestParam ReportFormat format, @RequestParam String email);

}
