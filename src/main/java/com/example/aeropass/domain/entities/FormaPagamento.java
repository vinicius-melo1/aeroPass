package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.CriarFormaPagamentoRequest;
import com.example.aeropass.application.DTOs.EnumStatusFormaPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String tipoPagamento;

    @Enumerated(EnumType.STRING)
    private EnumStatusFormaPagamento status = EnumStatusFormaPagamento.ATIVO;

    // Corrigido: aponta para o campo 'formaPagamentos' definido na classe Passagem
    @ManyToMany(mappedBy = "formaPagamentos")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Passagem> passagens = new HashSet<>();

    public FormaPagamento(CriarFormaPagamentoRequest criarFormaPagamentoRequest) {
        this.setTipoPagamento(criarFormaPagamentoRequest.tipoPagamento());
    }
}