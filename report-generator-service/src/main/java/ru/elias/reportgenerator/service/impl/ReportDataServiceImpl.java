package ru.elias.reportgenerator.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ru.elias.reportgenerator.domain.dto.ReportConfig;
import ru.elias.reportgenerator.domain.file.ReportMultipartFile;
import ru.elias.reportgenerator.domain.report.ReportFormat;
import ru.elias.reportgenerator.service.ReportDataService;
import ru.elias.reportgenerator.service.client.ConfigControlServiceClient;
import ru.elias.reportgenerator.service.client.ReportDataServiceClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportDataServiceImpl implements ReportDataService {

    private final ReportDataServiceClient reportDataServiceClient;
    private final ConfigControlServiceClient configControlServiceClient;
    private final Cache<String, Boolean> idempotencyCache;

    @Override
    public ReportConfig saveReport(String idempotencyKey, byte[] reportData, ReportFormat format) {
        log.info("Saving report with format: {}", format);
        var reportFile = new ReportMultipartFile(reportData, UUID.randomUUID().toString(), MediaType.APPLICATION_OCTET_STREAM_VALUE);
        log.debug("ReportMultipartFile created with UUID: {}", reportFile.getName());
        var fileName = reportDataServiceClient.saveReport(reportFile);
        log.info("Report saved with file name: {}", fileName);
        var config = saveConfig(fileName, format);
        idempotencyCache.put(idempotencyKey, true);
        return config;
    }

    private ReportConfig saveConfig(String fileName, ReportFormat format) {
        log.info("Saving report configuration for file: {}", fileName);
        var config = new ReportConfig(fileName, format.getExtension());
        configControlServiceClient.addConfig(config);
        log.info("Report configuration saved: {}", config);
        return config;
    }
}
