package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.FormaPagamento;
import com.example.aeropass.domain.entities.Passagem;

import java.time.LocalDateTime;

public record PassagemResponse(Long id, String codigoAssento, LocalDateTime dataCompra, Double valor, EnumStatusPassagem statusPassagem) {
    public PassagemResponse(Passagem passagemEntidade){
        this(
                passagemEntidade.getId(),
                passagemEntidade.getCodigoAssento(),
                passagemEntidade.getDataCompra(),
                passagemEntidade.getValor(),
//                passagemEntidade.getFormaPagamento().toString(),
                passagemEntidade.getStatus()
        );
    }
}
