package com.gerardo.thrive.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {
    private static final List<String> methods = List.of("GET", "POST", "PUT", "DELETE", "PATCH");
    private static final List<String> headers = List.of("Authorization", "Content-Type");

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(headers);
        configuration.setAllowedMethods(methods);
        configuration.addAllowedOrigin("http://localhost:4200");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}