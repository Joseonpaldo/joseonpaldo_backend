package com.example.demo.security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "조선팔도 api 명세서", version = "2.6.0",
                description = "조선팔도 웹게임 플랫폼 api 명세서입니다."),
        servers = {@Server(url = "http://localhost:8080"),
                    @Server(url = "https://joseonpaldo.site")},
        security = @SecurityRequirement(name = "bearerToken")
)
@SecurityScheme(
        name = "custom-auth-token",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class swaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("2.6.0") String springDocVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("조선팔도 Rest API DOCS")
                        .description("조선팔도 Rest API DOCS입니다.")
                        .version(springDocVersion)
                        .contact(new Contact().name("JoseonPaldo").email("dw5817@gmail.com").url("https://joseonpaldo.site")));
    }
}