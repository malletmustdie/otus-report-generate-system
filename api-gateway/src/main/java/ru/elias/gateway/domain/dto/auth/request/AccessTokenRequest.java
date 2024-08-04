package ru.elias.gateway.domain.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccessTokenRequest(
        @NotNull
        @NotBlank
        String username,
        @NotNull
        @NotBlank
        String password) {
}
