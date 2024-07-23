package ru.elias.gateway.service.client;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import ru.elias.gateway.domain.dto.Config;

@HttpExchange
public interface ConfigControlServiceClient {

    @GetExchange
    List<Config> getAllConfigs();

    @GetExchange("/{fileName}")
    Config getConfig(@PathVariable String fileName);

}
