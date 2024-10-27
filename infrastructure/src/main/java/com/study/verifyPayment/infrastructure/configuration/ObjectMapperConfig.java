package com.study.verifyPayment.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.verifyPayment.infrastructure.configuration.json.Json;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Primary
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return Json.mapper();
    }
}