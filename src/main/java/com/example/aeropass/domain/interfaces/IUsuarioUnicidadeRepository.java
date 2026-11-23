package com.example.aeropass.domain.interfaces;

import com.example.aeropass.domain.valueObject.CPF;

public interface IUsuarioUnicidadeRepository {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    boolean existsByCpf(CPF cpf);
    boolean existsByCpfAndIdNot(CPF cpf, Long id);
}
