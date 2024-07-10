package ru.elias.reportgenerator.service.client;

import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface ReportDataServiceClient {

    @PostExchange
    void saveReport(byte[] reportData);

}
