package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Passageiro;
import com.example.aeropass.domain.valueObject.CPF;
import com.example.aeropass.domain.valueObject.Passaporte;
import com.example.aeropass.domain.valueObject.Telefone;

public record PassageiroResponse(Long id, String nome, CPF cpf, Passaporte passaporte, Telefone telefone, String email, EnumStatusPassageiro statusPassageiro) {
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
