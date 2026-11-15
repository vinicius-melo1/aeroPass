package com.example.aeropass.presentation;

import com.example.aeropass.application.DTOs.CadastroRequest;
import com.example.aeropass.application.DTOs.LoginRequest;
import com.example.aeropass.application.DTOs.UsuarioResponse;
import com.example.aeropass.application.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/auth")
@Tag(description = "Controller de autenticação!", name="Autenticação")
public class    AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "Autenticação de usuários", description = "Método responsavel por efetuar o login do usuário")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        var resultadoAutenticadoRetornoToken = usuarioService.validarUsuarioAutenticadoRetornaToken(loginRequest);
        if(resultadoAutenticadoRetornoToken != null) {
            return ResponseEntity.ok(resultadoAutenticadoRetornoToken);

        }
        return ResponseEntity.badRequest().body("Usuário ou senha Inválido");
    }

    @PostMapping("/cadastre-se")
    @Operation(summary = "Método para clientes se cadastrarem.", description = "Método responsável por efetuar o cadastro de um novo cliente, criando o usuário e o passageiro vinculado.")
    public ResponseEntity<?> cadastrar(@RequestBody CadastroRequest cadastro){

        try {
            UsuarioResponse usuarioBanco = usuarioService.cadastrar(cadastro);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
