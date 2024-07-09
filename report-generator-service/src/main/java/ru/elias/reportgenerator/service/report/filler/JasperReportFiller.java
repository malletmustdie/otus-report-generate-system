package ru.elias.reportgenerator.service.report.filler;

import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.context.ApplicationContext;
import ru.elias.reportgenerator.error.exception.GenerateReportException;
import ru.elias.reportgenerator.service.dto.report.ReportData;
import ru.elias.reportgenerator.service.dto.report.ReportFormat;

@Slf4j
public abstract class JasperReportFiller<T extends ReportData> {

    private static final String LOCALE_PROPERTY_NAME = "net.sf.jasperreports.default.locale";
    private final JasperReport reportTemplate;

    protected JasperReportFiller(ApplicationContext context) throws JRException, IOException {
        reportTemplate = getJasperReport(context, getReportTemplateName());
    }

    /**
     * Генерирует jasper-отчет.
     *
     * @param data Объект данных отчета.
     * @return Jasper-отчет.
     */
    public abstract JasperPrint fillReport(T data, ReportFormat format);

    /**
     * Получение типа данных отчета.
     *
     * @return Тип данных отчета.
     */
    public abstract Class<T> getReportType();

    protected abstract String getReportTemplateName();

    protected JasperReport getJasperReport(ApplicationContext context,
                                           String fileName) throws IOException, JRException {
        setGlobalPropertiesJasperReport();
        var reportTemplateFile =
                context.getResource(fileName).getInputStream();
        return JasperCompileManager.compileReport(reportTemplateFile);
    }

    private void setGlobalPropertiesJasperReport() {
        JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance())
                .setProperty(LOCALE_PROPERTY_NAME, "ru_RU");
    }

    public JasperPrint fillReportTemplate(Map<String, Object> params) {
        try {
            return JasperFillManager.fillReport(reportTemplate, params, new JREmptyDataSource());
        } catch (JRException e) {
            log.error(e.getMessage(), e);
            return throwReportExportException(e);
        }
    }

    private JasperPrint throwReportExportException(JRException e) {
        var message = "unsuccessful_report_export";
        log.warn(message, e);
        throw new GenerateReportException(message);
    }

}
