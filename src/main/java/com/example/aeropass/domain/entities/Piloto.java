package com.example.aeropass.domain.entities;

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
public class Piloto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    //O Código ANAC (CANAC) é um número único de 6 dígitos que identifica o profissional da aviação civil junto à Agência Nacional de Aviação Civil (ANAC)
    private String canac;

    private String numeroLicenca;

    private String tipoPiloto;

    @ManyToMany(mappedBy = "pilotos")
    private Set<Voo> voos = new HashSet<>();

}
