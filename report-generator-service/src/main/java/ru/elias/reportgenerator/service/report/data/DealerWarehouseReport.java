package ru.elias.reportgenerator.service.report.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.elias.reportgenerator.domain.report.data.DealerWarehouseReportData;
import ru.elias.reportgenerator.domain.report.param.EmptyParam;
import ru.elias.reportgenerator.service.client.DataServiceClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealerWarehouseReport implements DataReportGenerator<DealerWarehouseReportData, EmptyParam> {

    private final DataServiceClient dataServiceClient;

    @Override
    public DealerWarehouseReportData generateData(EmptyParam request) {
        return getDealerWarehouseReportData();
    }

    private DealerWarehouseReportData getDealerWarehouseReportData() {
        return DealerWarehouseReportData.builder()
                .data(dataServiceClient.getDealerWarehouseData())
                .build();
    }

}
