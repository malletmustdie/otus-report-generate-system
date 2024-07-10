package ru.elias.reportgenerator.domain.report.data;

import java.util.List;
import lombok.Builder;
import ru.elias.reportgenerator.domain.dto.DealerWarehouse;

@Builder
public record DealerWarehouseReportData(List<DealerWarehouse> data) implements ReportData {
}
