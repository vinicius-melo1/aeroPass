package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.EnumStatusFormaPagamento;
import com.example.aeropass.domain.entities.FormaPagamento;

public record FormaPagamentoResponse(Long id, String nome, EnumStatusFormaPagamento status) {
    public FormaPagamentoResponse(FormaPagamento formaPagamentoResponse){
        this(
                formaPagamentoResponse.getId(),
                formaPagamentoResponse.getTipoPagamento(),
                formaPagamentoResponse.getStatus()
        );
    }
}
