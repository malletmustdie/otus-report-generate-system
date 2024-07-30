package ru.elias.reportgenerator.error.exception;

public class ReportAlreadyGeneratedException extends GenerateReportException {
    public ReportAlreadyGeneratedException(String message) {
        super(message);
    }
}
