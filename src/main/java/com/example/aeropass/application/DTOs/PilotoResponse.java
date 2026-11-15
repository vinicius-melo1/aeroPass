package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Piloto;

public record PilotoResponse(Long id, String canac, String numeroLicenca, String tipoPiloto, EnumStatusPiloto statusPiloto) {
    public PilotoResponse(Piloto pilotoEntidade) {
        this(
                pilotoEntidade.getId(),
                pilotoEntidade.getCanac(),
                pilotoEntidade.getNumeroLicenca(),
                pilotoEntidade.getTipoPiloto(),
                pilotoEntidade.getStatus()
        );
    }
}
