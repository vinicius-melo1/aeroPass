package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.valueObject.CNPJ;

public record EmpresaRequest(String razaoSocial, String cnpj, String nomeFantasia) {
}
