package ru.elias.delivery.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "mail")
public record MailProperties(
        String host,
        int port,
        String username,
        String password,
        Map<String, String> properties,
        String subject,
        String text,
        String attachmentName) {
}