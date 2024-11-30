package com.example.testing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection for a stateless API
                .cors(cors -> {}) // Enable CORS for global access
                .authorizeHttpRequests(auth -> auth
                        // Public routes
                        .requestMatchers(
                                "/api/v1/auth/register",  // Registration
                                "/api/v1/auth/login",     // Login
                                "/api/v1/auth/menu/**", // Menu endpoints accessible to all
                                "/api/v1/auth/users/**"

                        ).permitAll()

                        // Admin-only routes
                        .requestMatchers("/api/v1/auth/admin/**").hasRole("ADMIN")

                        // Authenticated routes
                        .requestMatchers("/api/v1/auth/cart/**").authenticated()   // Cart endpoints
                        .requestMatchers("/api/v1/auth/cart/**").hasAnyRole("USER", "ADMIN")
                        // Orders endpoints
                        .requestMatchers("/api/v1/auth/cart/add").authenticated() // Ensure /add requires authentication


                        // Catch-all for authenticated access
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set stateless sessions
                )
                .authenticationProvider(authenticationProvider) // Add custom authentication provider
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT authentication filter

        return http.build();
    }
}
