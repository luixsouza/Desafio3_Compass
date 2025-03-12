package com.compass.ms_event_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
        .title("REST API - Microservice de Eventos")
        .description("API's para um Microservice de Eventos")
        .version("v1")
        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"))
        .contact(new Contact()
        .name("Luis Eduardo")
        .email("luis.teles.pb@compasso.com.br")
        .url("https://github.com/luixsouza/Desafio3_Compass"))
        );
    }
}