package ru.elias.gateway.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record User(
        @NotNull
        @NotBlank
        String username,
        @NotNull
        String email,
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password) {
}
