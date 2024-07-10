package ru.elias.reportgenerator.service.report;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.elias.reportgenerator.domain.report.data.ReportData;
import ru.elias.reportgenerator.domain.report.param.ReportParam;
import ru.elias.reportgenerator.error.exception.ReportNotFoundException;
import ru.elias.reportgenerator.service.report.data.DataReportGenerator;

@Service
@RequiredArgsConstructor
public class ReportDataDispatcher {

    private final Map<String, DataReportGenerator> dataGenerators;

    public <R extends ReportParam> ReportData getData(String dataGeneratorName, R request) {
        validateDispatcher(dataGeneratorName);
        return dataGenerators.get(dataGeneratorName).generateData(request);
    }

    private void validateDispatcher(String dataGeneratorName) {
        if (!dataGenerators.containsKey(dataGeneratorName)) {
            throw new ReportNotFoundException("report_generator_not_found_by_name");
        }
    }

}
