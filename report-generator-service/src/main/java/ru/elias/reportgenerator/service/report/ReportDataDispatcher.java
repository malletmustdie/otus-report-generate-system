package ru.elias.reportgenerator.service.report;

import com.github.benmanes.caffeine.cache.Cache;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.elias.reportgenerator.domain.report.data.ReportData;
import ru.elias.reportgenerator.domain.report.param.ReportParam;
import ru.elias.reportgenerator.error.exception.ReportAlreadyGeneratedException;
import ru.elias.reportgenerator.error.exception.ReportNotFoundException;
import ru.elias.reportgenerator.service.report.data.DataReportGenerator;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportDataDispatcher {

    private final Map<String, DataReportGenerator> dataGenerators;
    private final Cache<String, Boolean> idempotencyCache;

    public <R extends ReportParam> ReportData getData(String idempotencyKey, String dataGeneratorName, R request) {
        validate(idempotencyKey, dataGeneratorName);
        return dataGenerators.get(dataGeneratorName).generateData(request);
    }

    private void validate(String idempotencyKey, String dataGeneratorName) {
        if (idempotencyCache.getIfPresent(idempotencyKey) != null) {
            log.error("Report with idempotency key {} already generated", idempotencyKey);
            throw new ReportAlreadyGeneratedException("Report '%s' already generated!".formatted(dataGeneratorName));
        }
        if (!dataGenerators.containsKey(dataGeneratorName)) {
            log.error("Report {} not found", dataGeneratorName);
            throw new ReportNotFoundException("Report '%s' not found".formatted(dataGeneratorName));
        }
    }

}
