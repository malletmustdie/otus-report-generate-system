package ru.elias.gateway.domain.dto.user;

import lombok.Builder;

@Builder
public record Credential(boolean temporary, String type, String value) {
}
