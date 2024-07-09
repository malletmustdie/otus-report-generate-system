package ru.elias.reportgenerator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/dealerWarehouseReport")
    public ResponseEntity<byte[]> getDealerWarehouseReport(@RequestParam ReportFormat format) {
        var data = dataDispatcher.getData("dealerWarehouseReport", new EmptyParam());
        var reportStream = reportService.exportReport(data, format);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("dealerWarehouseReport" + format.getExtension())
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(reportStream.toByteArray());
    }

}
