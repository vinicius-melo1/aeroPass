package com.example.aeropass.application.DTOs;

import java.time.LocalDateTime;
import java.util.Set;

public record VooRequest(String codigoVoo, Long capacidade, Long assentosDisponiveis,
                         String cidadeOrigem, String cidadeDestino, LocalDateTime dataHoraSaida,
                         LocalDateTime dataHoraChegadaPrevista, LocalDateTime dataHoraChegada,
                         Long aviaoId, Set<Long> pilotosIds) {
}
