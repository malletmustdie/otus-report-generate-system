package ru.elias.gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.gateway.domain.dto.Config;
import ru.elias.gateway.domain.dto.ReportFormat;
import ru.elias.gateway.client.ReportGeneratorServiceClient;

@Slf4j
@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportGeneratorController {

    private final ReportGeneratorServiceClient client;

    @PostMapping("/dealerWarehouseReport")
    @ResponseStatus(HttpStatus.CREATED)
    public Config getDealerWarehouseReport(@RequestParam ReportFormat format) {
        log.info("POST: /dealerWarehouseReport - try to create dealer warehouse report, format {}", format);
        return client.generateDealerWarehouseReport(format);
    }

}
