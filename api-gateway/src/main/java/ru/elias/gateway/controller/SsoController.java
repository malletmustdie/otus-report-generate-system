package ru.elias.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.gateway.domain.dto.auth.request.AccessTokenRequest;
import ru.elias.gateway.domain.dto.auth.request.RefreshAccessTokenRequest;
import ru.elias.gateway.domain.dto.auth.response.RefreshTokenResponse;
import ru.elias.gateway.domain.dto.auth.response.SignInResponse;
import ru.elias.gateway.domain.dto.user.User;
import ru.elias.gateway.service.UserService;

@Tag(name = "Operations with sso")
@Slf4j
@RestController
@RequestMapping("/sso")
@RequiredArgsConstructor
public class SsoController {

    private final UserService userService;

    @Operation(summary = "Get access token")
    @PostMapping("/token/access")
    public SignInResponse auth(@Valid @RequestBody AccessTokenRequest request) {
        log.info("POST: /sso/token/access - Access token request received");
        var response = userService.getAccessToken(request);
        log.info("Access token generated successfully for user: {}", request.username());
        return response;
    }

    @Operation(summary = "Refresh access token")
    @PostMapping("/token/refresh")
    public RefreshTokenResponse refreshAccessToken(@Valid @RequestBody RefreshAccessTokenRequest request) {
        log.info("POST: /sso/token/refresh - Refresh access token request received");
        var response = userService.refresh(request);
        log.info("Access token refreshed successfully");
        return response;
    }

    @Operation(summary = "Registration")
    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registration(@Valid @RequestBody User request) {
        log.info("POST: /sso/reg - Registration request received, requestBody: {}", request);
        userService.createUser(request);
        log.info("User registered successfully: {}", request.username());
    }

    @Operation(summary = "Logout")
    @PostMapping("/logout")
    public void logout(@Valid @RequestBody RefreshAccessTokenRequest request) {
        log.info("POST: /sso/logout - Logout request received");
        userService.logout(request);
    }

}
