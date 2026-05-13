package com.cloudstore.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {


    @Bean
    public WebClient fakeStoreWebClient() {
        return WebClient.builder()
                .baseUrl("https://fakestoreapi.com")  // ← All requests start from here
                .build();
    }
}