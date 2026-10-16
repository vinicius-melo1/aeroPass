package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.CriarFormaPagamentoRequest;
import com.example.aeropass.application.DTOs.EnumStatusFormaPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String tipoPagamento;

    private EnumStatusFormaPagamento status = EnumStatusFormaPagamento.ATIVO;

    public FormaPagamento(CriarFormaPagamentoRequest criarFormaPagamentoRequest) {
        this.setTipoPagamento(criarFormaPagamentoRequest.tipoPagamento());
    }
}
