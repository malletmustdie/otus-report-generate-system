package ru.elias.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.gateway.service.client.ConfigControlServiceClient;
import ru.elias.gateway.domain.dto.report.Config;

@Tag(name = "Operations about report config")
@Slf4j
@RestController
@RequestMapping("/api/v1/configs")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigControlServiceClient client;

    @Operation(summary = "Get report configs")
    @GetMapping
    public List<Config> getConfigs() {
        log.info("GET: /api/v1/configs - Fetching all report configs");
        var configs = client.getAllConfigs();
        log.info("Successfully fetched {} report configs", configs.size());
        return configs;
    }

}
