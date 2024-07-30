package ru.elias.gateway.client;

import ru.elias.gateway.domain.dto.auth.request.AccessTokenRequest;
import ru.elias.gateway.domain.dto.auth.request.RefreshAccessTokenRequest;
import ru.elias.gateway.domain.dto.auth.token.AccessTokenResponse;
import ru.elias.gateway.domain.dto.user.User;

public interface KeycloakClient {
    AccessTokenResponse getAccessToken(AccessTokenRequest accessTokenRequest, boolean isAdmin);
    AccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest refreshAccessTokenRequest);
    void createUser(User user);
    User getUserInfo(String id);
    void updateUser(User user, String id);
    void logout(RefreshAccessTokenRequest refreshAccessTokenRequest);
}
