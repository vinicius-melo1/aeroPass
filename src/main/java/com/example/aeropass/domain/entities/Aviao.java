package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.AviaoRequest;
import com.example.aeropass.application.DTOs.EnumStatusAviao;
import com.example.aeropass.application.DTOs.VooRequest;
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
public class Aviao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;

    private String fabricante;

    private String numeroSerie;

    private EnumStatusAviao status = EnumStatusAviao.ATIVO;

    public Aviao(AviaoRequest aviao) {
        this.setModelo(aviao.modelo());
        this.setFabricante(aviao.fabricante());
        this.setNumeroSerie(aviao.numeroSerie());
    }
}
