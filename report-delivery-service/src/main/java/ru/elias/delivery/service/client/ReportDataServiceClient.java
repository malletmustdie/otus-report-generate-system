package ru.elias.delivery.service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import ru.elias.delivery.domain.ReportFormat;

@HttpExchange
public interface ReportDataServiceClient {

    @GetExchange("/{fileName}")
    ResponseEntity<byte[]> getReportByFilename(@PathVariable String fileName, @RequestParam ReportFormat format);

}
