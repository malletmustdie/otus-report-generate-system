package ru.elias.reportgenerator.service.client;

import java.util.List;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import ru.elias.reportgenerator.domain.dto.DealerWarehouse;

@HttpExchange
public interface DataServiceClient {

    @GetExchange
    List<DealerWarehouse> getDealerWarehouseData();

}
