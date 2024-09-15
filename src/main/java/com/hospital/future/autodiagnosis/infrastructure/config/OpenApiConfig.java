package com.hospital.future.autodiagnosis.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hospital Auto-Diagnosis API")
                        .version("1.0")
                        .description("API for the auto-diagnosis system of the Hospital of the Future"));
    }
}