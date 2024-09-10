package com.example.demo.security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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