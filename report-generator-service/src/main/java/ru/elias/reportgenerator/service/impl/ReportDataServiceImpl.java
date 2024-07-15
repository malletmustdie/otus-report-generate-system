package ru.elias.reportgenerator.service.impl;

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

    @Override
    public ReportConfig saveReport(byte[] reportData, ReportFormat format) {
        log.info("Try to save reportData");
        var reportFile = new ReportMultipartFile(reportData, UUID.randomUUID().toString(), MediaType.APPLICATION_OCTET_STREAM_VALUE);
        var fileName = reportDataServiceClient.saveReport(reportFile);
        return saveConfig(fileName, format);
    }

    private ReportConfig saveConfig(String fileName, ReportFormat format) {
        var config = new ReportConfig(fileName, format.getExtension());
        configControlServiceClient.addConfig(config);
        return config;
    }

}
