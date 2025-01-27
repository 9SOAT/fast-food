package com.fiap.challenge.food.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.SneakyThrows;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
class SpringDocConfig {

    @Value("classpath:openapi/api.yaml")
    private Resource openAPIResource;


    @Bean
    @Primary
    public OpenAPI openApi() throws IOException {
        InputStream inputStream = openAPIResource.getInputStream();
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(inputStream, OpenAPI.class);
    }
}
