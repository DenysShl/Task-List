package org.example.tasklist.service.impl;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.tasklist.config.MinioProperties;
import org.example.tasklist.exception.UploadException;
import org.example.tasklist.model.TaskImage;
import org.example.tasklist.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final MinioProperties minioProperties;
    private final MinioClient minioClient;

    @Override
    public String upload(TaskImage image) {

        try {
            createBacket();
        } catch (Exception e) {
            throw new UploadException("Can`t create backet: " + e.getMessage());
        }

        MultipartFile file = image.getFile();
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new UploadException("File must be not null!");
        }

        String fileName = generateFileName(file);

        try (InputStream inputStream = file.getInputStream()) {
            saveImage(inputStream, fileName);
        } catch (IOException e) {
            throw new UploadException("Error while uploading file: " + e.getMessage());
        }
        return fileName;
    }

    @SneakyThrows
    private void createBacket() {
        boolean existBucket = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());

        if (!existBucket) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                            .bucket(minioProperties.getBucket())
                            .build());
        }
    }

    private String generateFileName(MultipartFile file) {
        String extention = getExtension(file);
        return UUID.randomUUID() + "." + extention;
    }

    private String getExtension(MultipartFile file) {
        return file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveImage(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                        .stream(inputStream, inputStream.available(), -1)
                        .bucket(minioProperties.getBucket())
                        .object(fileName)
                        .build());
    }

}
