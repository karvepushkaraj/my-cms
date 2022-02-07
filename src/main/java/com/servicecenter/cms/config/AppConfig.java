package com.servicecenter.cms.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableCaching
public class AppConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/service-request")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic dGVjaG5pY2lhbjpwYXNzd29yZA==")
                .build();
    }
}
