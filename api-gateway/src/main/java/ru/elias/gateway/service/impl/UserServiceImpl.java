package ru.elias.gateway.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.elias.gateway.client.KeycloakClient;
import ru.elias.gateway.domain.dto.auth.request.AccessTokenRequest;
import ru.elias.gateway.domain.dto.auth.request.RefreshAccessTokenRequest;
import ru.elias.gateway.domain.dto.auth.response.RefreshTokenResponse;
import ru.elias.gateway.domain.dto.auth.response.SignInResponse;
import ru.elias.gateway.domain.dto.user.User;
import ru.elias.gateway.service.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KeycloakClient keycloakClient;

    @Override
    public SignInResponse getAccessToken(AccessTokenRequest accessTokenRequest) {
        log.info("Getting access token for user: {}", accessTokenRequest.username());
        var tokenResponse = keycloakClient.getAccessToken(accessTokenRequest, false);
        return SignInResponse.builder()
                .accessToken(tokenResponse.accessToken())
                .expiresIn(tokenResponse.accessExpiresIn())
                .refreshToken(tokenResponse.refreshToken())
                .refreshExpiresIn(tokenResponse.refreshExpiresIn())
                .tokenType(tokenResponse.tokenType())
                .scope(tokenResponse.scope())
                .build();
    }

    @Override
    public RefreshTokenResponse refresh(RefreshAccessTokenRequest refreshAccessTokenRequest) {
        log.info("Refreshing access token");
        var tokenResponse = keycloakClient.refreshAccessToken(refreshAccessTokenRequest);
        return RefreshTokenResponse.builder()
                .accessToken(tokenResponse.accessToken())
                .accessTokenTTL(tokenResponse.accessExpiresIn())
                .tokenType(tokenResponse.tokenType())
                .build();
    }

    @Override
    public void createUser(User request) {
        log.info("Creating user: {}", request.username());
        keycloakClient.createUser(request);
        log.info("User created successfully: {}", request.username());
    }

    @Override
    public User getUserInfo(String id) {
        log.info("Fetching user info for user ID: {}", id);
        var user = keycloakClient.getUserInfo(id);
        log.info("User info retrieved successfully for user ID: {}", id);
        return user;
    }

    @Override
    public void updateUser(User request, String id) {
        log.info("Updating user: {} with ID: {}", request.username(), id);
        keycloakClient.updateUser(request, id);
        log.info("User updated successfully: {} with ID: {}", request.username(), id);
    }

    @Override
    public void logout(RefreshAccessTokenRequest refreshAccessTokenRequest) {
        log.info("Logging out");
        keycloakClient.logout(refreshAccessTokenRequest);
        log.info("User logged out successfully");
    }

}
