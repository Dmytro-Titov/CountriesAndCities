package com.andersenlab.countriesandcities.configuration.aws;

import com.andersenlab.countriesandcities.model.City;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3Service {

  private S3Client s3Client;

  @Value("${aws.accessKey}")
  private String accessKey;
  @Value("${aws.secretKey}")
  private String secretKey;
  @Value("${aws.region}")
  private String region;
  @Value("${aws.cityLogosBucketName}")
  private String bucket;

  public S3Service() {
    this.s3Client = null;
  }

  @PostConstruct
  private void initializeS3Client() {
    this.s3Client = S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey))
        .build();
  }

  public String generateS3Url(String key) {
    return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
  }

  public String uploadLogo(City existingCity, MultipartFile logoFile) throws IOException {
    String newKey = existingCity.getId() + "-" + logoFile.getOriginalFilename();

    deleteLogo(existingCity.getLogoKey());

    File tempFile = File.createTempFile("temp-logo", null);
    logoFile.transferTo(tempFile.toPath());

    s3Client.putObject(PutObjectRequest.builder()
        .bucket(bucket)
        .key(newKey)
        .build(), tempFile.toPath());

    tempFile.delete();

    return newKey;
  }

  public void deleteLogo(String logoKey) {
    if (objectExists(logoKey)) {
      s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(logoKey).build());
    }
  }

  private boolean objectExists(String key) {
    try {
      s3Client.headObject(HeadObjectRequest.builder().bucket(bucket).key(key).build());
      return true;
    } catch (S3Exception e) {
      if (e.statusCode() == 404) {
        return false;
      }
      throw e;
    }
  }
}
