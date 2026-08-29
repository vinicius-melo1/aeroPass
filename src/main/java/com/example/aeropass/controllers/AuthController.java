package com.example.aeropass.controllers;

import com.example.aeropass.DTOs.CadastroRequest;
import com.example.aeropass.DTOs.LoginRequest;
import com.example.aeropass.DTOs.LoginResponse;
import com.example.aeropass.repository.UsuarioRepository;
import com.example.aeropass.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/auth")
@Tag(description = "Controller de autenticação!", name="Autenticação")
public class AuthController {
    @Autowired
    private TokenService tokenService;

//    @PostMapping("/cadastrar")
//    @Operation(summary = "Cadastro de usuários", description =  "Método de cadastro de novos usuários no sistema")
//    public ResponseEntity<?> registrar(@RequestBody CadastroRequest cadastroRequest) {
//
//    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(summary = "Autenticação de usuários", description = "Método de login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        if(usuarioRepository.existsUsuarioByEmailAndSenha(loginRequest.email(), loginRequest.senha())) {
            var token = tokenService.gerarToken(loginRequest.email());

            return ResponseEntity.ok(new LoginResponse(token));
        }
        return ResponseEntity.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
    }

}
