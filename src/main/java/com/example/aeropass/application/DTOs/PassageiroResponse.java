package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Passageiro;
import com.example.aeropass.domain.valueObject.CPF;
import com.example.aeropass.domain.valueObject.Passaporte;
import com.example.aeropass.domain.valueObject.Telefone;

public record PassageiroResponse(Long id, String nome, String cpf, String passaporte, String telefone, String email, EnumStatusPassageiro status) {
    public PassageiroResponse(Passageiro passageiroEntidade){
        this(
                passageiroEntidade.getId(),
                passageiroEntidade.getNome(),
                passageiroEntidade.getCpf().toString(),
                passageiroEntidade.getPassaporte().toString(),
                passageiroEntidade.getTelefone().toString(),
                passageiroEntidade.getEmail(),
                passageiroEntidade.getStatus()
        );
    }
}
