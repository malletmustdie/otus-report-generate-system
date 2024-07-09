package ru.elias.reportgenerator.service.report.filler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.elias.reportgenerator.domain.report.ReportFormat;
import ru.elias.reportgenerator.domain.report.data.DealerWarehouseReportData;

@Slf4j
@Service
public class DealerWarehouseReportFiller extends JasperReportFiller<DealerWarehouseReportData> {

    private static final String REPORT_DATA = "REPORT_DATA";

    @Autowired
    public DealerWarehouseReportFiller(ApplicationContext context) throws JRException, IOException {
        super(context);
    }

    @SneakyThrows
    @Override
    public JasperPrint fillReport(DealerWarehouseReportData data, ReportFormat format) {
        Map<String, Object> params = new HashMap<>();
        params.put(REPORT_DATA, new JRBeanCollectionDataSource(data.data()));
        if (format == ReportFormat.XLSX) {
            params.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        }
        return this.fillReportTemplate(params);
    }

    @Override
    public Class<DealerWarehouseReportData> getReportType() {
        return DealerWarehouseReportData.class;
    }

    @Override
    protected String getReportTemplateName() {
        return "classpath:jasper-reports/dealer_warehouse.jrxml";
    }

}
