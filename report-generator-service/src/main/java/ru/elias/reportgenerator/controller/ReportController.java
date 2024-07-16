package ru.elias.reportgenerator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
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

    //private static final String REPORT_NAME = "%s-%s".formatted("dealerWarehouseReport", UUID.randomUUID().toString());

    private final ReportDataDispatcher dataDispatcher;
    private final BaseReportService reportService;

    @PostMapping("/dealerWarehouseReport")
    @ResponseStatus(HttpStatus.CREATED)
    public ReportConfig getDealerWarehouseReport(@RequestParam ReportFormat format) {
        var data = dataDispatcher.getData("dealerWarehouseReport", new EmptyParam());
        return reportService.generateReport(data, format);
    }

}
