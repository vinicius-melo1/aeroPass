package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.EnumStatusPassagem;
import com.example.aeropass.application.DTOs.PassageiroRequest;
import com.example.aeropass.application.DTOs.PassagemRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "voo_id",referencedColumnName = "id")
    private Voo voo;

    @ManyToOne
    @JoinColumn(name = "passageiro_id",referencedColumnName = "id")
    public Passageiro passageiro;

    private String codigoAssento;

    private LocalDateTime dataCompra;

    private Double valor;

    @ManyToMany
    @JoinTable(
            name = "tb_pagamento",
            joinColumns = @JoinColumn(name = "passagem_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
    )
    private Set<FormaPagamento> formaPagamentos = new HashSet<>();

    private EnumStatusPassagem status = EnumStatusPassagem.ATIVO;

    public Passagem(PassagemRequest passagem) {
        this.setCodigoAssento(passagem.codigoAssento());
        this.setDataCompra(passagem.dataCompra());
        this.setValor(passagem.valor());
    }

    public Passagem(PassagemRequest passagem, Voo voo) {
        if (voo == null) {
            throw new IllegalArgumentException("A passagem deve estar associada a um voo!");
        }
        voo.reservarAssento();
        this.setVoo(voo);
        this.setCodigoAssento(passagem.codigoAssento());
        this.setDataCompra(passagem.dataCompra());
        this.setValor(passagem.valor());
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        if (dataCompra.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data de compra não pode ser anterior à data atual!");
        }
        this.dataCompra = dataCompra;
    }

    public void setValor(Double valor) {
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("O valor da passagem deve ser maior que zero!");
        }
        this.valor = valor;
    }
}
