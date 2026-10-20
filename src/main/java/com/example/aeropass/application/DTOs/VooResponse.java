package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Usuario;
import com.example.aeropass.domain.entities.Voo;

import java.time.LocalDateTime;

public record VooResponse(Long id, String codigoVoo, Long capacidade, Long assentosDisponiveis, String cidadeOrigem, String cidadeDestino, LocalDateTime dataHoraSaida, LocalDateTime dataHoraChegada, EnumStatusVoo statusVoo) {
    public VooResponse(Voo vooEntidade){
        this(
                vooEntidade.getId(),
                vooEntidade.getCodigoVoo(),
                vooEntidade.getCapacidade(),
                vooEntidade.getAssentosDisponiveis(),
                vooEntidade.getCidadeOrigem(),
                vooEntidade.getCidadeDestino(),
                vooEntidade.getDataHoraSaida(),
                vooEntidade.getDataHoraChegada(),
                vooEntidade.getStatus()
        );
    }
}
