package com.example.aeropass.application.DTOs;

public record CadastroRequest(String nome, String cpf, String passaporte, String telefone, String email, String senha) {
}
