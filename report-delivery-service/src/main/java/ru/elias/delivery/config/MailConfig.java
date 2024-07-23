package ru.elias.delivery.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import ru.elias.delivery.config.properties.MailProperties;

@Configuration
@RequiredArgsConstructor
public class MailConfig {

   private final MailProperties mailProperties;

    @Bean
    public JavaMailSender javaMailSender() {
        var mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailProperties.host());
        mailSender.setPort(mailProperties.port());
        mailSender.setUsername(mailProperties.username());
        mailSender.setPassword(mailProperties.password());

        var props = mailSender.getJavaMailProperties();
        props.putAll(mailProperties.properties());

        return mailSender;
    }
}

