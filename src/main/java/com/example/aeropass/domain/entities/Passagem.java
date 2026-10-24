package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.EnumStatusPassagem;
import com.example.aeropass.application.DTOs.PassageiroRequest;
import com.example.aeropass.application.DTOs.PassagemRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    public Long idVoo;
//
//    public Long idPassageiro;

    private String codigoAssento;

    private LocalDateTime dataCompra;

    private Double valor;

    private String formaPagamento;

    private EnumStatusPassagem status = EnumStatusPassagem.ATIVO;

    public Passagem(PassagemRequest passagem) {
        this.setCodigoAssento(passagem.codigoAssento());
        this.setDataCompra(passagem.dataCompra());
        this.setValor(passagem.valor());
        this.setFormaPagamento(passagem.formaPagamento());
    }
}
