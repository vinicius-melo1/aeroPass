package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.EnumStatusPassageiro;

public record AtualizarStatusPassageiroRequest(EnumStatusPassageiro status) {
}
