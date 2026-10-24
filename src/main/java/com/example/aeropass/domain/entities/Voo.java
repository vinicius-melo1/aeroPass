package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.EnumStatusVoo;
import com.example.aeropass.application.DTOs.VooRequest;
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
    private Long id;

    // public Long idAviao;

    private String codigoVoo;

    private Long capacidade;

    private Long assentosDisponiveis;

    private String cidadeOrigem;

    private String cidadeDestino;

    private LocalDateTime dataHoraSaida;

    private LocalDateTime dataHoraChegada;

    private EnumStatusVoo status = EnumStatusVoo.ATIVO;

    public Voo(VooRequest voo) {
        this.setCodigoVoo(voo.codigoVoo());
        this.setCapacidade(voo.capacidade());
        this.setAssentosDisponiveis(voo.assentosDisponiveis());
        this.setCidadeOrigem(voo.cidadeOrigem());
        this.setCidadeDestino(voo.cidadeDestino());
        this.setDataHoraSaida(voo.dataHoraSaida());
        this.setDataHoraChegada(voo.dataHoraChegada());
    }
}
