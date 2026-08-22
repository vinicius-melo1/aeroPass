package com.example.aeropass.services;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service

public class TokenService {

    @Value("${spring.secret}")
    private String secret;

    @Value("${spring.expiracao}")
    private Long expiracao;

    @Value("${spring.emissor}")
    private String emissor;

    public String gerarToken(String subject) {
        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            token = com.auth0.jwt.JWT.create()
                    .withIssuer(emissor)
                    .withSubject(subject)
                    .withExpiresAt(getDataExpiracao())
                    .sign(algorithm);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        return token;
    }

    private Instant getDataExpiracao() {

        // Pegar data atual
        var dataAtual = LocalDateTime.now();

        // Adicionar ou diminuir tempo da data atual
        var dataFutura = dataAtual.plusMinutes(expiracao);

        return dataFutura.toInstant(ZoneOffset.of("-03:00"));
    }
}
