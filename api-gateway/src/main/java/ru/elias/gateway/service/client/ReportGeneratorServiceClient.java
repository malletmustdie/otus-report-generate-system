package ru.elias.gateway.service.client;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import ru.elias.gateway.domain.dto.Config;
import ru.elias.gateway.domain.dto.ReportFormat;

@HttpExchange
public interface ReportGeneratorServiceClient {

    @PostExchange
    Config generateDealerWarehouseReport(@RequestParam ReportFormat format);

}
