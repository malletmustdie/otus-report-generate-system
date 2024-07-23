package ru.elias.gateway.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.gateway.domain.dto.Config;
import ru.elias.gateway.service.client.ConfigControlServiceClient;

@Slf4j
@RestController
@RequestMapping("/api/v1/configs")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigControlServiceClient client;

    @GetMapping
    public List<Config> getConfigs() {
        log.info("GET: /api/v1/configs - get all configs");
        return client.getAllConfigs();
    }

}
