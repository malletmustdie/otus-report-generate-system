package ru.elias.reportgenerator.service.client;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.elias.reportgenerator.domain.dto.ReportConfig;

@HttpExchange
public interface ConfigControlServiceClient {

    @PostExchange
    void addConfig(@RequestBody ReportConfig config);

}
