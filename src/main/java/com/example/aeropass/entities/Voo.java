package com.example.aeropass.entities;

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
public class Voo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // public Long idAviao;

    public String codigoVoo;

    public Long capacidade;

    public Long assentosDisponiveis;

    public String cidadeOrigem;

    public String cidadeDestino;

    public LocalDateTime dataHoraSaida;

    public LocalDateTime dataHoraChegada;

    private EnumStatusVoo status = EnumStatusVoo.ATIVO;
}
