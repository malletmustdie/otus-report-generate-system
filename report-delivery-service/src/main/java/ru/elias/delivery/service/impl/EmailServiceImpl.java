package ru.elias.delivery.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.elias.delivery.config.properties.MailProperties;
import ru.elias.delivery.service.EmailService;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;

    @Override
    @SneakyThrows
    public void sendEmailWithAttachment(String to, byte[] attachment, String fileName) {
        log.info("Preparing to send email with attachment to recipient: {}", to);
        try {
            var message = createMimeMessage(to, attachment, fileName);
            log.debug("Sending email to {}", to);
            mailSender.send(message);
            log.info("Email with attachment '{}' sent successfully to {}", fileName, to);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}. Attachment fileName: {}. Error: {}", to, fileName, e.getMessage());
            throw e;
        }
    }

    private MimeMessage createMimeMessage(String to, byte[] attachment, String fileName) throws MessagingException {
        log.debug("Creating message for recipient: {}", to);
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);

        log.debug("Setting email properties: to={}, subject={}, text={}", to, mailProperties.subject(), mailProperties.text());
        helper.setTo(to);
        helper.setSubject(mailProperties.subject());
        helper.setText(mailProperties.text());

        var attachmentName = mailProperties.attachmentName().formatted(fileName);
        var attachmentSource = new ByteArrayResource(attachment);
        helper.addAttachment(attachmentName, attachmentSource);

        log.debug("Email message prepared with attachment: {}", attachmentName);
        return message;
    }

}
