package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.AviaoRequest;
import com.example.aeropass.application.DTOs.CadastroRequest;
import com.example.aeropass.application.DTOs.EnumStatusPassageiro;
import com.example.aeropass.application.DTOs.PassageiroRequest;
import com.example.aeropass.domain.valueObject.CPF;
import com.example.aeropass.domain.valueObject.Passaporte;
import com.example.aeropass.domain.valueObject.Telefone;
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

    @Embedded
    private CPF cpf;

    @Embedded
    private Passaporte passaporte;

    @Embedded
    private Telefone telefone;

    private String email;

    @OneToMany(mappedBy = "passageiro")
    private List<Passagem> passagens;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private EnumStatusPassageiro status = EnumStatusPassageiro.ATIVO;

    public Passageiro(PassageiroRequest passageiro) {
        this.setNome(passageiro.nome());
        this.setCpf(new CPF(passageiro.cpf()));
        this.setPassaporte(new Passaporte(passageiro.passaporte()));
        this.setTelefone(new Telefone(passageiro.telefone()));
        this.setEmail(passageiro.email());
    }

    public Passageiro(CadastroRequest request, Usuario usuario) {
        this.setNome(request.nome());
        this.setCpf(new CPF(request.cpf()   ));
        this.setPassaporte(new Passaporte(request.passaporte()));
        this.setTelefone(new Telefone(request.telefone()));
        this.setEmail(request.email());
        this.setUsuario(usuario);
    }
}
