package ru.elias.reportgenerator.service.report.data;

import ru.elias.reportgenerator.domain.report.data.ReportData;
import ru.elias.reportgenerator.domain.report.param.ReportParam;

public interface DataReportGenerator<D extends ReportData, R extends ReportParam> {

    D generateData(R request);

}
