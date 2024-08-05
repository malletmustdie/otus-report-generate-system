package ru.elias.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.gateway.domain.dto.report.Config;
import ru.elias.gateway.service.client.ConfigControlServiceClient;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ConfigControlServiceClient client;

    @Operation(summary = "Update report config")
    @PutMapping("/config/{fileName}")
    public void updateConfig(@PathVariable UUID fileName, @RequestBody Config config) {
        log.info("DELETE: /api/v1/configs - Update report config {}", fileName);
        client.updateConfig(fileName.toString(), config);
        log.info("Successfully update report config {}", fileName);
    }

    @Operation(summary = "Delete report configs")
    @DeleteMapping(("/config/{fileName}"))
    public void deleteConfig(@PathVariable UUID fileName) {
        log.info("DELETE: /api/v1/configs - Delete report config {}", fileName);
        client.deleteConfig(fileName.toString());
        log.info("Successfully delete report config {}", fileName);
    }

}
