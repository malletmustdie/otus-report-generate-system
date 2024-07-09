package ru.elias.reportgenerator.error.exception;

public class ReportNotFoundException extends GenerateReportException {
    public ReportNotFoundException(String message) {
        super(message);
    }
}
