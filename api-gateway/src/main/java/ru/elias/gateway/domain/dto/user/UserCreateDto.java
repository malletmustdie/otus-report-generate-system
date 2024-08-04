package ru.elias.gateway.domain.dto.user;

import java.util.List;
import lombok.Builder;

@Builder
public record UserCreateDto(

        String username,
        String firstName,
        String lastName,
        String email,
        Boolean emailVerified,
        Boolean enabled,
        List<Credential> credentials) {
}
