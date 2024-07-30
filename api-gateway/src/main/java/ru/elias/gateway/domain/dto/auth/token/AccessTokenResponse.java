package ru.elias.gateway.domain.dto.auth.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenResponse(
        @JsonProperty("access_token")
        String accessToken,
        @JsonProperty("expires_in")
        Long accessExpiresIn,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("refresh_expires_in")
        Long refreshExpiresIn,
        @JsonProperty("token_type")
        String tokenType,
        @JsonProperty("session_state")
        String sessionState,
        @JsonProperty("scope")
        String scope) {
}
