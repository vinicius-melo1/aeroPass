package com.example.aeropass.application.services;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.aeropass.domain.entities.Usuario;
import com.example.aeropass.domain.interfaces.IBrevoEmailExternalRepository;
import com.example.aeropass.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedefinicaoSenhaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IBrevoEmailExternalRepository brevoEmailExternalRepository;

    @Value("${aeropass.front-url}")
    private String frontUrl;

    public void solicitarRedefinicao(String email) {
        usuarioRepository.findByEmail(email).ifPresent(usuario -> {
            var token = tokenService.gerarTokenRedefinicaoSenha(usuario);
            var link = frontUrl + "/auth/redefinir-senha?token=" + token;

            var html = "<p>Clique no link para redefinir sua senha (válido por 30 minutos):</p>"
                    + "<p><a href=\"" + link + "\">Redefinir senha</a></p>";

            brevoEmailExternalRepository.enviarEmail(usuario.getEmail(), "AeroPass - Redefinição de senha", html);
        });
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        Usuario usuario;
        try {
            usuario = tokenService.verificarTokenRedefinicaoSenha(token);
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Link inválido ou expirado!");
        }

        usuario.redefinirSenha(novaSenha);
        usuarioRepository.save(usuario);
        tokenService.invalidarToken(token);
    }
}