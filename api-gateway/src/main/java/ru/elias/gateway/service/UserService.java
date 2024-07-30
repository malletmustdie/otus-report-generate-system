package ru.elias.gateway.service;

import ru.elias.gateway.domain.dto.auth.request.AccessTokenRequest;
import ru.elias.gateway.domain.dto.auth.request.RefreshAccessTokenRequest;
import ru.elias.gateway.domain.dto.auth.response.RefreshTokenResponse;
import ru.elias.gateway.domain.dto.auth.response.SignInResponse;
import ru.elias.gateway.domain.dto.user.User;

public interface UserService {
    SignInResponse getAccessToken(AccessTokenRequest accessTokenRequest);
    RefreshTokenResponse refresh(RefreshAccessTokenRequest refreshAccessTokenRequest);
    void createUser(User request);
    User getUserInfo(String id);
    void updateUser(User request, String id);
    void logout(RefreshAccessTokenRequest refreshAccessTokenRequest);
}
