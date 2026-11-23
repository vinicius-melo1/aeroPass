package com.example.aeropass.domain.domainServices;

import com.example.aeropass.domain.entities.Empresa;
import com.example.aeropass.domain.interfaces.IEmpresaUnicidadeRepository;
import com.example.aeropass.domain.valueObject.CNPJ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmpresaDomainService {
    private final IEmpresaUnicidadeRepository repository;

    public void validarUnicidade(Empresa empresa) {
        validarUnicidade(empresa.getId(), empresa.getCnpj());
    }

    public void validarUnicidade(Long id, CNPJ cnpj) {
        boolean existe = id == null
                ? repository.existsByCnpj(cnpj)
                : repository.existsByCnpjAndIdNot(cnpj, id);

        if (existe) {
            throw new IllegalArgumentException("Já existe uma empresa cadastrada com este CNPJ!");
        }
    }
}
