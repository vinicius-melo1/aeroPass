package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.CriarFormaPagamentoRequest;
import com.example.aeropass.application.DTOs.EnumStatusFormaPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoPagamento;

    private EnumStatusFormaPagamento status = EnumStatusFormaPagamento.ATIVO;

    // Corrigido: aponta para o campo 'formaPagamentos' definido na classe Passagem
    @ManyToMany(mappedBy = "formaPagamentos")
    private Set<Passagem> passagens = new HashSet<>();

    public FormaPagamento(CriarFormaPagamentoRequest criarFormaPagamentoRequest) {
        this.setTipoPagamento(criarFormaPagamentoRequest.tipoPagamento());
    }
}