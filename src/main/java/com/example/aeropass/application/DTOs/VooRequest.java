package com.example.aeropass.application.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Set;

public record VooRequest(String codigoVoo, Long capacidade, Long assentosDisponiveis,
                         String cidadeOrigem, String cidadeDestino,
                         @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime dataHoraSaida,
                         @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime dataHoraChegadaPrevista,
                         @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime dataHoraChegada,
                         Long aviaoId, Set<Long> pilotosIds) {
}
