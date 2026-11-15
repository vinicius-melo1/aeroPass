package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Empresa;
import com.example.aeropass.domain.valueObject.CNPJ;

public record EmpresaResponse (Long id, String razaoSocial, CNPJ cnpj, String nomeFantasia) {
    public EmpresaResponse(Empresa empresaEntidade) {
        this(
                empresaEntidade.getId(),
                empresaEntidade.getRazaoSocial(),
                empresaEntidade.getCnpj(),
                empresaEntidade.getNomeFantasia()
        );
    }
}
