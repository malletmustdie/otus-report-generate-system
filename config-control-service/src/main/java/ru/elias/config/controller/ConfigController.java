package ru.elias.config.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.config.domain.entity.ConfigEntity;
import ru.elias.config.domain.entity.dto.Config;
import ru.elias.config.repository.ConfigRepository;

@Slf4j
@RestController
@RequestMapping("/api/v1/configs")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigRepository configRepository;

    @PostMapping
    public void addConfig(@RequestBody @Valid Config config) {
        var entity = ConfigEntity.builder()
                .reportName(UUID.fromString(config.fileName()))
                .extension(config.extension())
                .build();
        configRepository.save(entity);
        log.info("Add config for report {}", config);
    }

    @GetMapping
    public List<Config> getConfigs() {
        return configRepository.findAll()
                .stream()
                .map(this::toConfig)
                .toList();
    }

    @GetMapping("/{fileName}")
    public Config getConfig(@PathVariable UUID fileName) {
        return configRepository.findById(fileName)
                .map(this::toConfig)
                .orElseThrow(() -> new RuntimeException("Config for file name '%s' not found!".formatted(fileName)));
    }

    private Config toConfig(ConfigEntity entity) {
        return Config.builder()
                .fileName(entity.getReportName().toString())
                .extension(entity.getExtension())
                .build();
    }

}
