package com.example.aeropass.application.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.aeropass.application.DTOs.LoginRequest;
import com.example.aeropass.application.DTOs.UsuarioLogado;
import com.example.aeropass.domain.entities.Token;
import com.example.aeropass.domain.entities.Usuario;
import com.example.aeropass.domain.repository.TokenRepository;
import com.example.aeropass.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String gerarToken(LoginRequest loginRequestDto) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        var usuario = usuarioRepository.findAll()
                .stream()
                .filter(u-> u.getEmail().equals(loginRequestDto.email()))
                .findFirst()
                .orElse(null);

        String token =  JWT.create()
                .withIssuer(emissor)
                .withSubject(loginRequestDto.email())
                .withExpiresAt(this.gerarDataExpiracao())
                .sign(algorithm);


        tokenRepository.save(new Token(token,usuario));
        return token;
    }

    public UsuarioLogado verificarToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verificador = JWT.require(algorithm).withIssuer(emissor).build();

        DecodedJWT jwt = verificador.verify(token);

        if (!jwt.getClaim("finalidade").isMissing()) {
            throw new IllegalArgumentException("Token inválido!");
        }

        var tokenBanco = tokenRepository.findTokenByToken(token).orElse(null);

        if(tokenBanco == null) {
            throw new IllegalArgumentException("Token inválido!");
        }

        return new UsuarioLogado(tokenBanco.getUsuario());
    }

    private Instant gerarDataExpiracao() {

        // Pegar data atual
        var dataAtual = LocalDateTime.now();

        // Adicionar ou diminuir tempo da data atual
        dataAtual = dataAtual.plusMinutes(expiracao);

        return dataAtual.toInstant(ZoneOffset.of("-03:00"));
    }


    private static final String FINALIDADE_REDEFINIR_SENHA = "REDEFINIR_SENHA";

    public String gerarTokenRedefinicaoSenha(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(usuario.getEmail())
                .withClaim("finalidade", FINALIDADE_REDEFINIR_SENHA)
                .withExpiresAt(Instant.now().plus(Duration.ofMinutes(30)))
                .sign(algorithm);

        tokenRepository.save(new Token(token, usuario));
        return token;
    }

    public Usuario verificarTokenRedefinicaoSenha(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        JWTVerifier verificador = JWT.require(algorithm)
                .withIssuer(emissor)
                .withClaim("finalidade", FINALIDADE_REDEFINIR_SENHA)
                .build();

        verificador.verify(token); // lança exceção se expirou, é inválido ou não tem a claim

        var tokenBanco = tokenRepository.findTokenByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Link inválido ou já utilizado!"));

        return tokenBanco.getUsuario();
    }

    public void invalidarToken(String token) {
        tokenRepository.findTokenByToken(token).ifPresent(tokenRepository::delete);
    }
}
