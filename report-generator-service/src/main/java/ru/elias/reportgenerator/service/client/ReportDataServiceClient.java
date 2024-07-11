package ru.elias.reportgenerator.service.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface ReportDataServiceClient {

    @PostExchange
    String saveReport(@RequestPart("file") MultipartFile reportData);

}
