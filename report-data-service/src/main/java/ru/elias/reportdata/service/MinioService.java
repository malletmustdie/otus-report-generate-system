package ru.elias.reportdata.service;

public interface MinioService {

    String uploadFile(byte[] data);
    byte[] getFile(String filename);
}
