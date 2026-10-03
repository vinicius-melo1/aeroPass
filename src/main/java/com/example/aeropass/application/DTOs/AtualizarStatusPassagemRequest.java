package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.EnumStatusPassagem;

public record AtualizarStatusPassagemRequest(EnumStatusPassagem status) {
}
