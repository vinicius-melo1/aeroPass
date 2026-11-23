package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Piloto;
import com.example.aeropass.domain.entities.Voo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record VooResponse(Long id, String codigoVoo, Long capacidade, Long assentosDisponiveis,
                          String cidadeOrigem, String cidadeDestino,
                          @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime dataHoraSaida,
                          @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime dataHoraChegadaPrevista,
                          @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime dataHoraChegada,
                          EnumStatusVoo status, Long aviaoId, Set<Long> pilotosIds) {
    public VooResponse(Voo vooEntidade){
        this(
                vooEntidade.getId(),
                vooEntidade.getCodigoVoo(),
                vooEntidade.getCapacidade(),
                vooEntidade.getAssentosDisponiveis(),
                vooEntidade.getCidadeOrigem(),
                vooEntidade.getCidadeDestino(),
                vooEntidade.getDataHoraSaida(),
                vooEntidade.getDataHoraChegadaPrevista(),
                vooEntidade.getDataHoraChegada(),
                vooEntidade.getStatus(),
                vooEntidade.getAviao() != null ? vooEntidade.getAviao().getId() : null,
                vooEntidade.getPilotos().stream().map(Piloto::getId).collect(Collectors.toSet())
        );
    }
}
