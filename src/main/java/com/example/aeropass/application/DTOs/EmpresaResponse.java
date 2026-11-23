package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Empresa;
import com.example.aeropass.domain.valueObject.CNPJ;

public record EmpresaResponse (Long id, String razaoSocial, String cnpj, String nomeFantasia, EnumStatusEmpresa status) {
    public EmpresaResponse(Empresa empresaEntidade) {
        this(
                empresaEntidade.getId(),
                empresaEntidade.getRazaoSocial(),
                empresaEntidade.getCnpj().toString(),
                empresaEntidade.getNomeFantasia(),
                empresaEntidade.getStatus()
        );
    }
}
