package ru.elias.reportgenerator.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import ru.elias.reportgenerator.domain.file.ReportMultipartFile;
import ru.elias.reportgenerator.service.ReportDataService;
import ru.elias.reportgenerator.service.client.ReportDataServiceClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportDataServiceImpl implements ReportDataService {

    private final ReportDataServiceClient reportDataServiceClient;

    @Override
    public String saveReport(byte[] reportData) {
        log.info("Try to save reportData");
        var reportFile = new ReportMultipartFile(reportData, UUID.randomUUID().toString(), MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return reportDataServiceClient.saveReport(reportFile);
    }

}
