package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.EnumStatusPiloto;
import com.example.aeropass.application.DTOs.PilotoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Piloto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    //O Código ANAC (CANAC) é um número único de 6 dígitos que identifica o profissional da aviação civil junto à Agência Nacional de Aviação Civil (ANAC)
    private String canac;

//    private String numeroLicenca;
    
    private String tipoPiloto;

    @ManyToOne
    @JoinColumn(name = "empresa_id",referencedColumnName = "id")
    private Empresa empresa;

    @ManyToMany(mappedBy = "pilotos")
    private Set<Voo> voos = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private EnumStatusPiloto status = EnumStatusPiloto.ATIVO;


    public Piloto(PilotoRequest piloto, Empresa empresa) {
        this.setNome(piloto.nome());
        this.setCanac(piloto.canac());
//        this.setNumeroLicenca(piloto.numeroLicenca());
        this.setTipoPiloto(piloto.tipoPiloto());
        this.setEmpresa(empresa);
    }
    public void setEmpresa(Empresa empresa) {
        if (empresa == null) {
            throw new IllegalArgumentException("O piloto deve estar associado a uma empresa!");
        }
        this.empresa = empresa;
    }

}
