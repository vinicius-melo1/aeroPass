package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Passageiro;

public record PassageiroResponse(Long id, String nome, String cpf, String passaporte, String telefone, String email, EnumStatusPassageiro statusPassageiro) {
    public PassageiroResponse(Passageiro passageiroEntidade){
        this(
                passageiroEntidade.getId(),
                passageiroEntidade.getNome(),
                passageiroEntidade.getCpf(),
                passageiroEntidade.getPassaporte(),
                passageiroEntidade.getTelefone(),
                passageiroEntidade.getEmail(),
                passageiroEntidade.getStatus()
        );
    }
}
