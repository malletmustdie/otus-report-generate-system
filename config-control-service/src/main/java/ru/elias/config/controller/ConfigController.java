package ru.elias.config.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.config.domain.entity.ConfigEntity;
import ru.elias.config.domain.entity.dto.Config;
import ru.elias.config.error.exception.ConfigNotFoundException;
import ru.elias.config.repository.ConfigRepository;

@Slf4j
@RestController
@RequestMapping("/api/v1/configs")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigRepository configRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addConfig(@RequestBody @Valid Config config) {
        log.info("POST request to /api/v1/configs - Adding new config: {}", config);
        var entity = ConfigEntity.builder()
                .reportName(UUID.fromString(config.fileName()))
                .extension(config.extension())
                .build();
        configRepository.save(entity);
        log.info("Successfully added config for report: {}", config);
    }

    @GetMapping
    public List<Config> getConfigs() {
        log.info("GET request to /api/v1/configs - Retrieving all configs");
        var configs = configRepository.findAll()
                .stream()
                .map(this::toConfig)
                .toList();
        log.info("Retrieved {} configs", configs.size());
        return configs;
    }

    @GetMapping("/{fileName}")
    public Config getConfig(@PathVariable UUID fileName) {
        log.info("GET request to /api/v1/configs/{} - Retrieving config", fileName);
        var config = configRepository.findById(fileName)
                .map(this::toConfig)
                .orElseThrow(() -> {
                    log.error("Config for file name '{}' not found", fileName);
                    return new ConfigNotFoundException("Config for file name '%s' not found!".formatted(fileName));
                });
        log.info("Successfully retrieved config for file name: {}", fileName);
        return config;
    }

    @PutMapping("/{fileName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConfig(@PathVariable UUID fileName, @RequestBody @Valid Config config) {
        log.info("PUT request to /api/v1/configs - Update config: {}", config);
        configRepository.findById(fileName)
                        .ifPresent(entity -> {
                            entity.setReportName(UUID.fromString(config.fileName()));
                            entity.setExtension(config.extension());
                            configRepository.save(entity);
                            log.info("Successfully update config for report {}", fileName);
                        });
        log.info("Successfully added config for report: {}", config);
    }

    @DeleteMapping("/{fileName}")
    public void deleteConfig(@PathVariable UUID fileName) {
        log.info("DELETE request to /api/v1/configs/{} - Delete config", fileName);
        configRepository.deleteById(fileName);
    }

    private Config toConfig(ConfigEntity entity) {
        log.debug("Converting ConfigEntity to Config: {}", entity);
        return Config.builder()
                .fileName(entity.getReportName().toString())
                .extension(entity.getExtension())
                .build();
    }

}
