package com.example.aeropass.application.DTOs;

public record EnderecoResponse(String cep, String logradouro, String bairro, String localidade, String uf, String estado) {
}
