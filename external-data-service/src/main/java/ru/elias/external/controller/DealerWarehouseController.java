package ru.elias.external.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.external.domain.dto.DealerWarehouse;
import ru.elias.external.repository.DealerWarehouseRepository;

@Slf4j
@RestController
@RequestMapping("/api/v1/data")
@RequiredArgsConstructor
public class DealerWarehouseController {

    private final DealerWarehouseRepository repository;

    @GetMapping
    public List<DealerWarehouse> getAll() {
        return repository.findAll()
                .stream()
                .map(entity -> DealerWarehouse.builder()
                        .manufacturer(entity.getManufacturer())
                        .model(entity.getModel())
                        .color(entity.getColor())
                        .engine(entity.getEngine())
                        .price(entity.getPrice())
                        .build())
                .toList();
    }

}
