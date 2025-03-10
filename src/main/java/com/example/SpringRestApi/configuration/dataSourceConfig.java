package com.example.SpringRestApi.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

@Configuration
public class dataSourceConfig {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Autowired
  private SecretsManagerClient secretsManagerClient;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setUrl(dbUrl);
    dataSource.setUsername(getSecret("dbUsername"));
    dataSource.setPassword(getSecret("dbPassword"));
    return dataSource;
  }

  private String getSecret(String secretId){
    String secret = secretsManagerClient.getSecretValue(
        GetSecretValueRequest.builder()
            .secretId(secretId)
            .build()
    ).secretString();
    return secret;
  }
}