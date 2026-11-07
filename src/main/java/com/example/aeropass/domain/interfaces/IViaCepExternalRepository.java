package com.example.aeropass.domain.interfaces;

import com.example.aeropass.infra.dto.ViaCepResponse;
import org.springframework.stereotype.Repository;

public interface IViaCepExternalRepository {
    ViaCepResponse buscarEnderecoViaCepPorCep(String cep);
}
