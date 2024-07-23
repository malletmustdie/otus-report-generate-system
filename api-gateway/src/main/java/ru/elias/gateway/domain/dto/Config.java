package ru.elias.gateway.domain.dto;

import lombok.Builder;

@Builder
public record Config(String fileName, String extension) {
}
