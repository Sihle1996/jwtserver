//package com.example.testing.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.List;
//
//@Configuration
//public class WebConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); // Allow credentials for cross-origin requests
//        config.setAllowedOrigins(List.of("http://localhost:4200")); // Restrict allowed origins
//        config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Restrict allowed headers
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Restrict allowed methods
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//}
