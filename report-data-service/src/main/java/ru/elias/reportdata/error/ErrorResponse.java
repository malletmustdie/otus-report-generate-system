package ru.elias.reportdata.error;

import java.time.Instant;

public record ErrorResponse(int status, String message, Instant timestamp) {
    public ErrorResponse(int status, String message) {
        this(status, message, Instant.now());
    }
}
