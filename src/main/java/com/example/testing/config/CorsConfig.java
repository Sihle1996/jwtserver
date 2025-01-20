package com.example.testing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Create a CORS configuration object
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Allow credentials such as cookies or Authorization headers
        config.setAllowedOrigins(List.of("http://localhost:4200")); // List specific allowed origins
        config.addAllowedHeader("*"); // Allow all headers
        config.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type")); // Expose necessary headers

        // Apply the configuration to all endpoints
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
