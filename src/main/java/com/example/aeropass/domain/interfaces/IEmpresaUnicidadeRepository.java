package com.example.aeropass.domain.interfaces;

import com.example.aeropass.domain.valueObject.CNPJ;

public interface IEmpresaUnicidadeRepository {
    boolean existsByCnpj(CNPJ cnpj);
    boolean existsByCnpjAndIdNot(CNPJ cnpj, Long id);
}
