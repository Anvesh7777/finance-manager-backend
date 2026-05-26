package com.anvesh.finance_manager.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService
            userDetailsService;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {

        try {

            final String authHeader =
                    request.getHeader(
                            "Authorization"
                    );

            // NO TOKEN
            if (
                    authHeader == null
                            ||
                            !authHeader.startsWith(
                                    "Bearer "
                            )
            ) {

                filterChain.doFilter(
                        request,
                        response
                );

                return;
            }

            // EXTRACT TOKEN
            String token =
                    authHeader.substring(7);

            String email =
                    jwtService.extractEmail(token);

            // VALIDATE USER
            if (
                    email != null
                            &&
                            SecurityContextHolder
                                    .getContext()
                                    .getAuthentication()
                                    == null
            ) {

                UserDetails userDetails =

                        userDetailsService
                                .loadUserByUsername(
                                        email
                                );

                // VALID TOKEN
                if (
                        jwtService.isTokenValid(
                                token,
                                email
                        )
                ) {

                    UsernamePasswordAuthenticationToken
                            authToken =

                            new UsernamePasswordAuthenticationToken(

                                    userDetails,

                                    null,

                                    userDetails
                                            .getAuthorities()
                            );

                    authToken.setDetails(

                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authToken
                            );
                }
            }

        } catch (Exception ex) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            response.setContentType(
                    "application/json"
            );

            response.getWriter().write(
                    "{\"message\":\"Invalid or Expired Token\"}"
            );

            return;
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}