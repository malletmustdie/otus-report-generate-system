package ru.elias.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.elias.gateway.domain.dto.user.User;
import ru.elias.gateway.service.UserService;

@Tag(name = "Operations about users")
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user info")
    @GetMapping("/userInfo")
    public User getUserInfo(Principal principal) {
        log.info("GET: /api/v1/users/userInfo - Fetching user info for user: {}", principal.getName());
        return userService.getUserInfo(principal.getName());
    }

    @Operation(summary = "Update user")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User request, Principal principal) {
        log.info("PUT: /api/v1/users - Updating user: {}, requestBody: {}", principal.getName(), request);
        userService.updateUser(request, principal.getName());
    }

}
