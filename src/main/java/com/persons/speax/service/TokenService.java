package com.persons.speax.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.persons.speax.dto.AuthRequestDTO;
import com.persons.speax.dto.ResponseTokenDTO;
import com.persons.speax.entity.User;
import com.persons.speax.entity.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.Objects;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserDetailsImpl user) {;

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("API Speax")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withIssuedAt(createdAt())
                    .withExpiresAt(expiresAt())
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            throw new RuntimeException("Error generating token: ", exception);
        }

    }

    public Long parseUserId(String bearerToken) {
        if (!bearerToken.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header");
        }

        String token = bearerToken.substring(7);

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("API Speax")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("id").asLong();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid or expired token", exception);
        }
    }


    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("API Speax")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Invalid token");
        }
    }

    private Instant createdAt() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
    }

    private Instant expiresAt() {
        return LocalDateTime
                .now()
                .plusHours(1)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public boolean isValid(String token) {
        return true; // TODO: Implementar
    }

}
