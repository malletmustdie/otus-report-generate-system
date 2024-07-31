package ru.elias.delivery.service;

public interface EmailService {

    void sendEmailWithAttachment(String to, byte[] attachment, String fileName);
}
