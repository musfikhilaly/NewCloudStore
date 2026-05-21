package com.cloudstore.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean(name = "fakeStoreWebClient")
    public WebClient fakeStoreWebClient() {
        return WebClient.builder()
                .baseUrl("https://fakestoreapi.com")
                .build();
    }

    @Bean(name = "userOrderWebClient")
    public WebClient userOrderWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
    }
}