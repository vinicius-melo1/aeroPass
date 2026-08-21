package com.example.aeropass.controllers;

import com.example.aeropass.DTOs.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.HttpURLConnection;

@RestController
@RequestMapping("/auth")
@Tag(description = "Conmtroller de autenticação!", name="Autenticação")
public class AuthController {

    @PostMapping("/login")
    @Operation(summary = "Autenticação de usuários", description = "Método de login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        if(loginRequest.email().equals("string")&& loginRequest.senha().equals("string")) {
            // Gerar o token
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpURLConnection.HTTP_UNAUTHORIZED).build();
    }

}
