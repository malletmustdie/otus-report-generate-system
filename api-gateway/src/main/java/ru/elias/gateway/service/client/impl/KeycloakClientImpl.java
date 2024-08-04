package ru.elias.gateway.service.client.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import ru.elias.gateway.service.client.KeycloakClient;
import ru.elias.gateway.config.properties.KeycloakProperties;
import ru.elias.gateway.domain.dto.auth.request.AccessTokenRequest;
import ru.elias.gateway.domain.dto.auth.request.RefreshAccessTokenRequest;
import ru.elias.gateway.domain.dto.auth.token.AccessTokenResponse;
import ru.elias.gateway.domain.dto.user.Credential;
import ru.elias.gateway.domain.dto.user.User;
import ru.elias.gateway.domain.dto.user.UserCreateDto;

@Service
@RequiredArgsConstructor
public class KeycloakClientImpl implements KeycloakClient {

    private final KeycloakProperties keycloakProperties;
    private final RestClient restClient;

    @Override
    public AccessTokenResponse getAccessToken(AccessTokenRequest accessTokenRequest, boolean isAdmin) {
        var body = createTokenRequestBody(
                isAdmin,
                isAdmin ? "client_credentials" : "password",
                accessTokenRequest.username(),
                accessTokenRequest.password(),
                null);
        return sendTokenRequest(keycloakProperties.token().url(), body);
    }

    @Override
    public AccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest refreshAccessTokenRequest) {
        var body = createTokenRequestBody(
                false,
                "refresh_token",
                null,
                null,
                refreshAccessTokenRequest.refreshToken());
        return sendTokenRequest(keycloakProperties.token().url(), body);
    }

    @Override
    public void createUser(User user) {
        var adminToken = fetchAdminToken();
        var userDto = buildUserCreateDto(user);
        sendUserRequest(
                keycloakProperties.admin().url(),
                HttpMethod.POST,
                adminToken,
                userDto,
                Void.class);
    }

    @Override
    public User getUserInfo(String id) {
        var adminToken = fetchAdminToken();
        return sendUserRequest(
                "%s/%s".formatted(keycloakProperties.admin().url(), id),
                HttpMethod.GET,
                adminToken,
                null,
                User.class).getBody();
    }

    @Override
    public void updateUser(User user, String id) {
        var adminToken = fetchAdminToken();
        var userDto = buildUserCreateDto(user);
        sendUserRequest(
                String.format("%s/%s", keycloakProperties.admin().url(), id),
                HttpMethod.PUT,
                adminToken,
                userDto,
                Void.class);
    }

    @Override
    public void logout(RefreshAccessTokenRequest refreshAccessTokenRequest) {
        var body = createTokenRequestBody(
                false,
                null,
                null,
                null,
                refreshAccessTokenRequest.refreshToken());
        var uri = keycloakProperties.token().url().replace("token", "logout");
        sendTokenRequest(uri, body);
    }

    private LinkedMultiValueMap<String, String> createTokenRequestBody(boolean isAdmin,
                                                                       String grantType,
                                                                       String username,
                                                                       String password,
                                                                       String refreshToken) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", grantType);
        addClientCredentials(body, isAdmin);
        if (username != null && password != null) {
            body.add("username", username);
            body.add("password", password);
        }
        if (refreshToken != null) {
            body.add("refresh_token", refreshToken);
        }
        return body;
    }

    private void addClientCredentials(LinkedMultiValueMap<String, String> body, boolean isAdmin) {
        if (isAdmin) {
            body.add("client_id", keycloakProperties.admin().client());
            body.add("client_secret", keycloakProperties.admin().clientSecret());
        } else {
            body.add("client_id", keycloakProperties.token().client());
            body.add("client_secret", keycloakProperties.token().clientSecret());
        }
    }

    private AccessTokenResponse sendTokenRequest(String uri, LinkedMultiValueMap<String, String> body) {
        return restClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .toEntity(AccessTokenResponse.class)
                .getBody();
    }

    private <T> ResponseEntity<T> sendUserRequest(String url,
                                                  HttpMethod method,
                                                  String token,
                                                  UserCreateDto userDto,
                                                  Class<T> responseType) {
        var request = restClient.method(method)
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .contentType(MediaType.APPLICATION_JSON);
        if (userDto != null) {
            request.body(userDto);
        }
        return request.retrieve().toEntity(responseType);
    }

    private String fetchAdminToken() {
        return getAccessToken(new AccessTokenRequest(null, null), true).accessToken();
    }

    private UserCreateDto buildUserCreateDto(User user) {
        return UserCreateDto.builder()
                .username(user.username())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .email(user.email())
                .emailVerified(false)
                .enabled(true)
                .credentials(List.of(Credential.builder()
                        .temporary(false)
                        .type("password")
                        .value(user.password())
                        .build()))
                .build();
    }

}
