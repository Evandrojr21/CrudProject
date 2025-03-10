package com.example.SpringRestApi.configuration;

import java.net.URI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Configuration
public class SecretManagerConfig {

  @Bean
  public SecretsManagerClient secretsManagerClient(){
  return SecretsManagerClient.builder()
      .endpointOverride(URI.create("http://localhost:4566"))
      .region(Region.of("us-east-1"))
      .build();
  }
}