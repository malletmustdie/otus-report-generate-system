package ru.elias.gateway.domain.dto.auth.response;

import lombok.Builder;

@Builder
public record SignInResponse(
        String accessToken,
        Long expiresIn,
        String refreshToken,
        Long refreshExpiresIn,
        String tokenType,
        String scope) {
}
