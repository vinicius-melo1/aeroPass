package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.AviaoRequest;
import com.example.aeropass.application.DTOs.EnumStatusPassageiro;
import com.example.aeropass.application.DTOs.PassageiroRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passageiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private String passaporte;

    private String telefone;

    private String email;

    @OneToMany(mappedBy = "passageiro")
    private List<Passagem> passagens;

    private EnumStatusPassageiro status = EnumStatusPassageiro.ATIVO;

    public Passageiro(PassageiroRequest passageiro) {
        this.setNome(passageiro.nome());
        this.setCpf(passageiro.cpf());
        this.setPassaporte(passageiro.passaporte());
        this.setTelefone(passageiro.telefone());
        this.setEmail(passageiro.email());
    }
}
