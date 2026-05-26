package com.anvesh.finance_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource
    corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        // ALLOWED FRONTENDS
        configuration.setAllowedOrigins(
                List.of(

                        "http://localhost:5173",

                        "http://127.0.0.1:5173",

                        "https://finance-manager-frontend-murex.vercel.app"
                )
        );

        // ALLOWED METHODS
        configuration.setAllowedMethods(
                List.of(

                        "GET",

                        "POST",

                        "PUT",

                        "DELETE",

                        "OPTIONS"
                )
        );

        // ALLOWED HEADERS
        configuration.setAllowedHeaders(
                List.of("*")
        );

        // EXPOSE HEADERS
        configuration.setExposedHeaders(
                List.of("Authorization")
        );

        // ALLOW JWT / COOKIES
        configuration.setAllowCredentials(
                true
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}