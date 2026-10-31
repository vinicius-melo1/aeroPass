package com.example.aeropass.application.DTOs;

import java.time.LocalDateTime;

public record PassagemRequest(String codigoAssento, LocalDateTime dataCompra, Double valor) {
}
