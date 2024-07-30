package ru.elias.gateway.domain.dto.report;

import lombok.Builder;

@Builder
public record Config(String fileName, String extension) {
}
