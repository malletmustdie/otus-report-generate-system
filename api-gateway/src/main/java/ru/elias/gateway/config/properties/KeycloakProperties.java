package ru.elias.gateway.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("keycloak")
public record KeycloakProperties(
        String authUrl,
        Client token,
        Client admin) {
    public record Client(String url, String realm, String client, String clientSecret) {}
}
