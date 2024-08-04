package ru.elias.data.error;

import java.time.Instant;

public record ErrorResponse(int status, String message, Instant timestamp) {
    public ErrorResponse(int status, String message) {
        this(status, message, Instant.now());
    }
}
