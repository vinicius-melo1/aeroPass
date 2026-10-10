package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.domain.entities.Usuario;
import com.example.aeropass.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.HttpURLConnection;
import java.util.List;

@Service

public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Value("${spring.secret}")
    private String secret;

    public LoginResponse validarUsuarioAutenticadoRetornaToken(LoginRequest loginRequest){
        if(usuarioRepository.existsUsuarioByEmailAndSenha(loginRequest.email(), loginRequest.senha())) {
            var token = tokenService.gerarToken(loginRequest.email());
            return new LoginResponse(token);
        }
        return null;
    }

    public List<UsuarioResponse> listarTodosUsuariosTable(){

        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminResponse criarAdmin(CriarAdminRequest criarAdminRequest) {

        if(!criarAdminRequest.secretKey().equals(secret)) {
            return new CriarAdminResponse(0L, "Usuário salvo com sucesso!");
        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);
        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminResponse(usuarioAdminSalvar.getId(), "Usuário salvo com sucesso");
    }
}
