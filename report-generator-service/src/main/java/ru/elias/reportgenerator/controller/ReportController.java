package ru.elias.reportgenerator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.reportgenerator.domain.dto.ReportConfig;
import ru.elias.reportgenerator.domain.report.ReportFormat;
import ru.elias.reportgenerator.domain.report.param.EmptyParam;
import ru.elias.reportgenerator.service.report.BaseReportService;
import ru.elias.reportgenerator.service.report.ReportDataDispatcher;

@Slf4j
@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportDataDispatcher dataDispatcher;
    private final BaseReportService reportService;

    @PostMapping("/dealerWarehouseReport")
    @ResponseStatus(HttpStatus.CREATED)
    public ReportConfig getDealerWarehouseReport(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestParam ReportFormat format) {
        log.info("POST: /dealerWarehouseReport - try to create dealer warehouse report, format {}", format);
        var data = dataDispatcher.getData(idempotencyKey, "dealerWarehouseReport", new EmptyParam());
        return reportService.generateReport(idempotencyKey, data, format);
    }

}
