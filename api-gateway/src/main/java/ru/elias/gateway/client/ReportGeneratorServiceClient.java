package ru.elias.gateway.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.elias.gateway.domain.dto.report.Config;
import ru.elias.gateway.domain.dto.report.ReportFormat;

@HttpExchange
public interface ReportGeneratorServiceClient {

    @PostExchange("/dealerWarehouseReport")
    Config generateDealerWarehouseReport(@RequestParam ReportFormat format);

}
