package ru.elias.reportdata.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.ByteArrayInputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.elias.reportdata.error.exception.MinioBucketOperationException;
import ru.elias.reportdata.error.exception.MinioFileRetrievalException;
import ru.elias.reportdata.service.MinioService;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    @SneakyThrows
    public String uploadFile(byte[] data) {
        var filename = UUID.randomUUID().toString();
        log.info("Uploading file with generated filename: {}", filename);
        try (var inputStream = new ByteArrayInputStream(data)) {
            ensureBucketExists();
            minioClient.putObject(createPutObjectArgs(filename, inputStream));
            log.info("File uploaded successfully: {}", filename);
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred uploadFile: " + e.getMessage(), e);
        }
    }

    @Override
    @SneakyThrows
    public byte[] getFile(String filename) {
        log.info("Retrieving file: {}", filename);
        try (var inputStream = minioClient.getObject(createGetObjectArgs(filename))) {
            byte[] fileData = inputStream.readAllBytes();
            log.info("File retrieved successfully: {}", filename);
            return fileData;
        } catch (Exception e) {
            throw new MinioFileRetrievalException("Error occurred while retrieving file: " + e.getMessage());
        }
    }

    private void ensureBucketExists() {
        log.info("Checking if bucket exists: {}", bucketName);
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                log.info("Bucket does not exist. Creating bucket: {}", bucketName);
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                log.info("Bucket already exists: {}", bucketName);
            }
        } catch (Exception e) {
            log.error("Error occurred while ensuring bucket exists: {}", e.getMessage(), e);
            throw new MinioBucketOperationException("Error occurred while ensuring bucket exists: " + e.getMessage());
        }
    }

    private PutObjectArgs createPutObjectArgs(String filename, ByteArrayInputStream inputStream) {
        return PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .stream(inputStream, inputStream.available(), -1)
                .build();
    }

    private GetObjectArgs createGetObjectArgs(String filename) {
        return GetObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .build();
    }

}
