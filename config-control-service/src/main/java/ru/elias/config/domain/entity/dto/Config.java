package ru.elias.config.domain.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record Config(@NotNull String fileName, @NotEmpty String extension) {
}
