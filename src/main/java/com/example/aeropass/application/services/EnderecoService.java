package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.EnderecoResponse;
import com.example.aeropass.domain.interfaces.IViaCepExternalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
    @Autowired
    private IViaCepExternalRepository viaCepExternalRepository;


    public EnderecoResponse BuscarEndereco(String cep) {
        var enderecoCompleto = viaCepExternalRepository.buscarEnderecoViaCepPorCep(cep);
        return new EnderecoResponse(enderecoCompleto.getCep(),
                enderecoCompleto.getLogradouro(),
                enderecoCompleto.getBairro(),
                enderecoCompleto.getLocalidade(),
                enderecoCompleto.getUf(),
                enderecoCompleto.getEstado());
    }
}
