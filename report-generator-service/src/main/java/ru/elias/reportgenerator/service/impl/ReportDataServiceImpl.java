package ru.elias.reportgenerator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.elias.reportgenerator.service.ReportDataService;
import ru.elias.reportgenerator.service.client.ReportDataServiceClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportDataServiceImpl implements ReportDataService {

    private final ReportDataServiceClient reportDataServiceClient;

    @Override
    public void saveReport(byte[] reportData) {
        log.info("Try to save reportData");
        reportDataServiceClient.saveReport(reportData);
    }

}
