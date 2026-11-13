package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.AviaoRequest;
import com.example.aeropass.application.DTOs.EnumStatusAviao;
import com.example.aeropass.application.DTOs.VooRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy =  "aviao")
    private List<Voo> voos;

    @ManyToOne
    @JoinColumn(name = "empresa_id",referencedColumnName = "id")
    private Empresa empresa;

    private EnumStatusAviao status = EnumStatusAviao.ATIVO;

    public Aviao(AviaoRequest aviao) {
        this.setModelo(aviao.modelo());
        this.setFabricante(aviao.fabricante());
        this.setNumeroSerie(aviao.numeroSerie());
    }
}
