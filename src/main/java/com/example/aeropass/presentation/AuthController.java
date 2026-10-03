package com.example.aeropass.presentation;

import com.example.aeropass.application.DTOs.LoginRequest;
import com.example.aeropass.application.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/auth")
@Tag(description = "Controller de autenticação!", name="Autenticação")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

//    @PostMapping("/cadastrar")
//    @Operation(summary = "Cadastro de usuários", description =  "Método de cadastro de novos usuários no sistema")
//    public ResponseEntity<?> registrar(@RequestBody CadastroRequest cadastroRequest) {
//
//    }

    @PostMapping("/login")
    @Operation(summary = "Autenticação de usuários", description = "Método de login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        var resultadoAutenticadoRetornoToken = usuarioService.validarUsuarioAutenticadoRetornaToken(loginRequest);
        if(resultadoAutenticadoRetornoToken != null) {
            return ResponseEntity.ok(resultadoAutenticadoRetornoToken);

        }
        return ResponseEntity.badRequest().body("Usuário ou senha Inválido");
    }

}
