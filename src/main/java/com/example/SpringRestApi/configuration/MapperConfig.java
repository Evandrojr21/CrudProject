package com.example.SpringRestApi.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MapperConfig {

  @Bean
  public ModelMapper moodelMapper(){
    return new ModelMapper();
  }
}