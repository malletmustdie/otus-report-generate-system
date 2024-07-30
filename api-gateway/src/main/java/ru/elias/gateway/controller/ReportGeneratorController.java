package ru.elias.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.gateway.client.ReportGeneratorServiceClient;
import ru.elias.gateway.domain.dto.report.Config;
import ru.elias.gateway.domain.dto.report.ReportFormat;

@Tag(name = "Operations about generation reports")
@Slf4j
@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportGeneratorController {

    private final ReportGeneratorServiceClient client;

    @Operation(summary = "Get dealerWarehouseReport")
    @PostMapping("/dealerWarehouseReport")
    @ResponseStatus(HttpStatus.CREATED)
    public Config getDealerWarehouseReport(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestParam ReportFormat format) {
        log.info("POST: /dealerWarehouseReport - try to create dealer warehouse report, format {}", format);
        MDC.put("IdempotencyKey", idempotencyKey);
        var config = client.generateDealerWarehouseReport(format);
        log.info("Dealer warehouse report created successfully, format {}", format);
        return config;
    }

}
