package ru.elias.data.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.data.client.ExternalClient;
import ru.elias.data.domain.dto.DealerWarehouse;

@Slf4j
@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class DataController {

    private final ExternalClient externalClient;

    @GetMapping
    public List<DealerWarehouse> getAll() {
        return externalClient.getData();
    }

}
