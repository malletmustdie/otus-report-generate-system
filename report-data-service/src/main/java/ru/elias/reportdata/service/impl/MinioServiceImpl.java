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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.elias.reportdata.service.MinioService;

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
        try (var inputStream = new ByteArrayInputStream(data)) {
            ensureBucketExists();
            minioClient.putObject(createPutObjectArgs(filename, inputStream));
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred: " + e.getMessage(), e);
        }
    }

    @Override
    @SneakyThrows
    public byte[] getFile(String filename) {
        try (var inputStream = minioClient.getObject(createGetObjectArgs(filename))) {
            return inputStream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred: " + e.getMessage(), e);
        }
    }

    private void ensureBucketExists() throws Exception {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
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
