package ru.elias.gateway.service.client;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PutExchange;
import ru.elias.gateway.domain.dto.report.Config;

@HttpExchange
public interface ConfigControlServiceClient {

    @GetExchange
    List<Config> getAllConfigs();

    @GetExchange("/{fileName}")
    Config getConfig(@PathVariable String fileName);

    @PutExchange("/{fileName}")
    void updateConfig(@PathVariable String fileName, @RequestBody Config config);

    @DeleteExchange("/{fileName}")
    void deleteConfig(@PathVariable String fileName);

}
