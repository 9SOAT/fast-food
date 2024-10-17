package com.fiap.challenge.food.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.SneakyThrows;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
class SpringDocConfig {

    @Value("classpath:openapi/api.yaml")
    private Resource openAPIResource;

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        OpenAPI openAPI = openApi();
        return openApi -> {
            openApi.setComponents(openAPI.getComponents());
            openApi.setPaths(openAPI.getPaths());
            openApi.setInfo(openAPI.getInfo());
        };
    }

    @SneakyThrows
    public OpenAPI openApi() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(openAPIResource.getInputStream(), OpenAPI.class);
    }
}
