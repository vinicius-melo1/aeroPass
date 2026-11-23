package com.example.aeropass.domain.repository;

import com.example.aeropass.domain.entities.Empresa;
import com.example.aeropass.domain.interfaces.IEmpresaUnicidadeRepository;
import com.example.aeropass.domain.valueObject.CNPJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, IEmpresaUnicidadeRepository {
    boolean existsByCnpj(CNPJ cnpj);
    boolean existsByCnpjAndIdNot(CNPJ cnpj, Long id);
}
