package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.FormaPagamento;
import com.example.aeropass.domain.entities.Passagem;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record PassagemResponse(Long id, String codigoAssento, Double valor,
                               EnumStatusPassagem status, Long vooId,
                               @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime dataCompra,
                               Set<Long> formasPagamentoIds,
                               Long passageiroId) {
    public PassagemResponse(Passagem passagemEntidade){
        this(
                passagemEntidade.getId(),
                passagemEntidade.getCodigoAssento(),
                passagemEntidade.getValor(),
                passagemEntidade.getStatus(),
                passagemEntidade.getVoo() != null ? passagemEntidade.getVoo().getId() : null,
                passagemEntidade.getDataCompra(),
                passagemEntidade.getFormaPagamentos().stream().map(FormaPagamento::getId).collect(Collectors.toSet()),
                passagemEntidade.getPassageiro() != null ? passagemEntidade.getPassageiro().getId() : null
        );
    }
}