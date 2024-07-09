package ru.elias.data.client;

import java.util.List;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import ru.elias.data.domain.dto.DealerWarehouse;

@HttpExchange
public interface ExternalClient {

    @GetExchange
    List<DealerWarehouse> getData();

}
