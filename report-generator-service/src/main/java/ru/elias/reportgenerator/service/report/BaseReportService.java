package ru.elias.reportgenerator.service.report;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import ru.elias.reportgenerator.domain.report.ReportFormat;
import ru.elias.reportgenerator.domain.report.data.ReportData;
import ru.elias.reportgenerator.error.exception.GenerateReportException;
import ru.elias.reportgenerator.service.report.filler.JasperReportFiller;

@Slf4j
@Service
public class BaseReportService {

    private static final String LOCALE_PROPERTY_NAME = "net.sf.jasperreports.default.locale";

    private static final String UNSUCCESSFUL_REPORT_EXPORT = "unsuccessful_report_export";

    private final Map<Class<? extends ReportData>, JasperReportFiller<? extends ReportData>> reportFillersMap;

    /**
     * Конструктор, применяемый Spring.
     */
    public BaseReportService(List<JasperReportFiller<?>> jasperReportFillers) {
        this.globalJasperConfig();
        this.reportFillersMap = jasperReportFillers.stream()
                .collect(Collectors.toMap(
                        JasperReportFiller::getReportType,
                        Function.identity()));
    }

    public ByteArrayOutputStream exportReport(ReportData report, ReportFormat format) {
        var os = new ByteArrayOutputStream();
        var jasperPrint = fillReport(report, format);
        exportReportToFormat(jasperPrint, os, format);
        return os;
    }

    private <T extends ReportData> JasperPrint fillReport(T report, ReportFormat format) {
        JasperReportFiller<T> filler = (JasperReportFiller<T>) reportFillersMap.get(report.getClass());
        if (filler == null) {
            throw new IllegalArgumentException("No filler found for report type: " + report.getClass().getName());
        }
        return filler.fillReport(report, format);
    }

    public void exportReportToFormat(JasperPrint jasperPrint, OutputStream outputStream, ReportFormat format) {
        try (outputStream) {
            switch (format) {
                case PDF -> exportPdf(outputStream, jasperPrint);
                case DOCX -> exportDocx(outputStream, jasperPrint);
                case XLSX -> exportXlsx(outputStream, jasperPrint);
                default -> throw new IllegalArgumentException("Unsupported report format: " + format);
            }
        } catch (Exception e) {
            throw new GenerateReportException(UNSUCCESSFUL_REPORT_EXPORT);
        }
    }

    private void exportPdf(OutputStream outputStream, JasperPrint jasperPrint) throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        export(exporter, jasperPrint);
    }

    private void exportDocx(OutputStream outputStream, JasperPrint jasperPrint) throws JRException {
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.setConfiguration(getDocxConfig());
        export(exporter, jasperPrint);
    }

    private void exportXlsx(OutputStream outputStream, JasperPrint jasperPrint) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        export(exporter, jasperPrint);
    }

    private void export(Exporter<ExporterInput, ?, ?, ?> exporter, JasperPrint jasperPrint) throws JRException {
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.exportReport();
    }

    private SimpleDocxReportConfiguration getDocxConfig() {
        SimpleDocxReportConfiguration config = new SimpleDocxReportConfiguration();
        config.setFramesAsNestedTables(true);
        return config;
    }

    private void globalJasperConfig() {
        JRPropertiesUtil.getInstance(DefaultJasperReportsContext.getInstance()).setProperty(LOCALE_PROPERTY_NAME, "ru_RU");
    }

}
