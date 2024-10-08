package ru.elias.reportgenerator.service.report;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import ru.elias.reportgenerator.domain.dto.ReportConfig;
import ru.elias.reportgenerator.domain.report.ReportFormat;
import ru.elias.reportgenerator.domain.report.data.ReportData;
import ru.elias.reportgenerator.error.exception.GenerateReportException;
import ru.elias.reportgenerator.service.ReportDataService;
import ru.elias.reportgenerator.service.report.filler.JasperReportFiller;

@Slf4j
@Service
public class BaseReportService {

    private static final String LOCALE_PROPERTY_NAME = "net.sf.jasperreports.default.locale";

    private static final String UNSUCCESSFUL_REPORT_EXPORT = "unsuccessful_report_export";

    private final Map<Class<? extends ReportData>, JasperReportFiller<? extends ReportData>> reportFillersMap;

    private final ReportDataService reportDataService;

    /**
     * C-tor.
     */
    public BaseReportService(List<JasperReportFiller<?>> jasperReportFillers, ReportDataService reportDataService) {
        log.info("Initializing BaseReportService...");
        this.globalJasperConfig();
        this.reportDataService = reportDataService;
        this.reportFillersMap = jasperReportFillers.stream()
                .collect(Collectors.toMap(
                        JasperReportFiller::getReportType,
                        Function.identity()));
        log.info("BaseReportService initialized with {} report fillers.", jasperReportFillers.size());
    }

    public ReportConfig generateReport(String idempotencyKey, ReportData report, ReportFormat format) {
        log.info("Generating report for format: {}", format);
        var reportData = generate(report, format);
        log.info("Report generated successfully, saving report...");
        return reportDataService.saveReport(idempotencyKey, reportData.toByteArray(), format);
    }

    private ByteArrayOutputStream generate(ReportData report, ReportFormat format) {
        log.debug("Generating ByteArrayOutputStream for report: {}", report.getClass().getSimpleName());
        var os = new ByteArrayOutputStream();
        var jasperPrint = fillReport(report, format);
        log.debug("Filled report for format: {}", format);
        exportReportToFormat(jasperPrint, os, format);
        log.debug("Report exported to format: {}", format);
        return os;
    }

    private <T extends ReportData> JasperPrint fillReport(T report, ReportFormat format) {
        log.debug("Filling report for type: {}", report.getClass().getName());
        JasperReportFiller<T> filler = (JasperReportFiller<T>) reportFillersMap.get(report.getClass());
        if (filler == null) {
            log.error("No filler found for report type: {}", report.getClass().getName());
            throw new IllegalArgumentException("No filler found for report type: " + report.getClass().getName());
        }
        log.debug("Filler found for report type: {}", report.getClass().getName());
        return filler.fillReport(report, format);
    }

    private void exportReportToFormat(JasperPrint jasperPrint, OutputStream outputStream, ReportFormat format) {
        log.debug("Exporting report to format: {}", format);
        try (outputStream) {
            var exporter = switch (format) {
                case PDF -> new JRPdfExporter();
                case DOCX -> {
                    var docxExporter = new JRDocxExporter();
                    docxExporter.setConfiguration(getDocxConfig());
                    yield docxExporter;
                }
                case XLSX -> new JRXlsxExporter();
            };
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.exportReport();
            log.info("Report exported successfully to format: {}", format);
        } catch (Exception e) {
            log.error("Error exporting report to format: {}", format, e);
            throw new GenerateReportException(UNSUCCESSFUL_REPORT_EXPORT);
        }
    }

    private SimpleDocxReportConfiguration getDocxConfig() {
        log.debug("Getting DOCX configuration...");
        SimpleDocxReportConfiguration config = new SimpleDocxReportConfiguration();
        config.setFramesAsNestedTables(true);
        return config;
    }

    private void globalJasperConfig() {
        log.info("Setting global JasperReports configuration...");
        JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance()).setProperty(LOCALE_PROPERTY_NAME, "ru_RU");
    }

}

