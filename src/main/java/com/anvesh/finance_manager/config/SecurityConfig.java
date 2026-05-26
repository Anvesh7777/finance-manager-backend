package com.anvesh.finance_manager.config;

import com.anvesh.finance_manager.security.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtFilter;

    // PASSWORD ENCODER
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // AUTH MANAGER
    @Bean
    public AuthenticationManager authenticationManager(

            AuthenticationConfiguration config

    ) throws Exception {

        return config.getAuthenticationManager();
    }

    // SECURITY FILTER CHAIN
    @Bean
    public SecurityFilterChain securityFilterChain(

            HttpSecurity http

    ) throws Exception {

        http

                // ENABLE CORS
                .cors(cors -> {})

                // DISABLE CSRF
                .csrf(csrf -> csrf.disable())

                // SESSIONLESS JWT AUTH
                .sessionManagement(session ->

                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // ROUTE AUTHORIZATION
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC ROUTES
                        .requestMatchers(

                                "/api/users/register",

                                "/api/users/login"

                        ).permitAll()

                        // PREFLIGHT REQUESTS
                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()

                        // SWAGGER / DOCS (OPTIONAL)
                        .requestMatchers(

                                "/swagger-ui/**",

                                "/v3/api-docs/**"

                        ).permitAll()

                        // SECURED ROUTES
                        .anyRequest()
                        .authenticated()
                );

        // JWT FILTER
        http.addFilterBefore(

                jwtFilter,

                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}