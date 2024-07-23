package ru.elias.delivery.service;

public interface EmailService {

    void sendEmailWithAttachment(String to, String subject, String text, byte[] attachment, String attachmentName);
}
