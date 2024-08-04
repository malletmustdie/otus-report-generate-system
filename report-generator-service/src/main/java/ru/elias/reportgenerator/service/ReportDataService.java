package ru.elias.reportgenerator.service;

import ru.elias.reportgenerator.domain.dto.ReportConfig;
import ru.elias.reportgenerator.domain.report.ReportFormat;

public interface ReportDataService {

    ReportConfig saveReport(String idempotencyKey, byte[] reportData, ReportFormat format);
}
