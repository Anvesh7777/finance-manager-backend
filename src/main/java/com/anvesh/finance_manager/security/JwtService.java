package com.anvesh.finance_manager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import java.security.Key;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    // INITIALIZE SECRET KEY
    @PostConstruct
    public void init() {

        key =
                Keys.hmacShaKeyFor(
                        secret.getBytes(
                                StandardCharsets.UTF_8
                        )
                );
    }

    // GENERATE TOKEN
    public String generateToken(
            String email
    ) {

        return Jwts.builder()

                .setSubject(email)

                .setIssuedAt(
                        new Date()
                )

                .setExpiration(

                        new Date(

                                System.currentTimeMillis()
                                        + 1000L * 60 * 60 * 24
                        )
                )

                .signWith(
                        key,
                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    // EXTRACT EMAIL
    public String extractEmail(
            String token
    ) {

        return getClaims(token)
                .getSubject();
    }

    // VALIDATE TOKEN
    public boolean isTokenValid(

            String token,

            String email
    ) {

        return (

                email.equals(
                        extractEmail(token)
                )

                        &&

                        !isTokenExpired(token)
        );
    }

    // TOKEN EXPIRY CHECK
    private boolean isTokenExpired(
            String token
    ) {

        return getClaims(token)

                .getExpiration()

                .before(new Date());
    }

    // GET CLAIMS
    private Claims getClaims(
            String token
    ) {

        return Jwts.parserBuilder()

                .setSigningKey(key)

                .build()

                .parseClaimsJws(token)

                .getBody();
    }
}