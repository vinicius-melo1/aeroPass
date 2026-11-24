package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Piloto;

public record PilotoResponse(Long id, String nome, String canac, String tipoPiloto, EnumStatusPiloto status) {
    public PilotoResponse(Piloto pilotoEntidade) {
        this(
                pilotoEntidade.getId(),
                pilotoEntidade.getNome(),
                pilotoEntidade.getCanac(),
                pilotoEntidade.getTipoPiloto(),
                pilotoEntidade.getStatus()
        );
    }
}
