package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Piloto;

public record PilotoResponse(Long id, String canac, String tipoPiloto, EnumStatusPiloto status) {
    public PilotoResponse(Piloto pilotoEntidade) {
        this(
                pilotoEntidade.getId(),
                pilotoEntidade.getCanac(),
                pilotoEntidade.getTipoPiloto(),
                pilotoEntidade.getStatus()
        );
    }
}
