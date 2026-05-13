package com.cloudstore.userorderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CorsConfig - Configures CORS so React frontend can call this service.
 *
 * Without this, the browser blocks all requests from React (port 5174)
 * to this service (port 8082) for security reasons.
 *
 * This config says: "Accept requests from localhost:5173 and localhost:5174"
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:5174");

        // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*");

        // Allow all headers (Content-Type, Authorization, etc.)
        config.addAllowedHeader("*");

        // Allow cookies and authorization headers
        config.setAllowCredentials(true);

        // Apply this config to ALL endpoints (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}