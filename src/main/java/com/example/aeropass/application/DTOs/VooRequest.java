package com.example.aeropass.application.DTOs;

import java.time.LocalDateTime;

public record VooRequest(String codigoVoo, Long capacidade, Long assentosDisponiveis, String cidadeOrigem, String cidadeDestino, LocalDateTime dataHoraSaida, LocalDateTime dataHoraChegada) {
}
