package com.example.testing.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allow CORS for all paths
                .allowedOrigins("http://localhost:4200", "https://myfrontend.loca.lt/site")  // Allow requests from your frontend origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow specified HTTP methods
                .allowedHeaders("*"); // Allow all headers
//                .allowCredentials(true);  // Allow credentials (cookies, authorization headers, etc.)
    }
}
