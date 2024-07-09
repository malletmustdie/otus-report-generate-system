package ru.elias.reportgenerator.domain.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.elias.reportgenerator.domain.report.data.ReportData;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealerWarehouse implements ReportData {
    private String manufacturer;
    private String model;
    private String color;
    private String engine;
    private BigDecimal price;
}
