package ru.elias.external.domain.dto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record DealerWarehouse(String manufacturer, String model, String color, String engine, BigDecimal price) {
}
