package com.example.aeropass.application.DTOs;

public record PassagemRequest(Long vooId, String codigoAssento, Double valor) {
}
