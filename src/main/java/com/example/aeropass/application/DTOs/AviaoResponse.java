package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Aviao;

public record AviaoResponse(Long id, String modelo, String fabricante, String numeroSerie, EnumStatusAviao status) {
    public AviaoResponse(Aviao aviaoEntidade) {
        this(
                aviaoEntidade.getId(),
                aviaoEntidade.getModelo(),
                aviaoEntidade.getFabricante(),
                aviaoEntidade.getNumeroSerie(),
                aviaoEntidade.getStatus()
        );
    }
}
