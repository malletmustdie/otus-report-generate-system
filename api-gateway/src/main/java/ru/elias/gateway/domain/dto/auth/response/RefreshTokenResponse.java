package ru.elias.gateway.domain.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record RefreshTokenResponse(
        String accessToken,
        Long accessTokenTTL,
        String tokenType) {
}
