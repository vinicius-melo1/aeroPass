package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.CriarAdminResponse;
import com.example.aeropass.application.DTOs.CriarFormaPagamentoRequest;
import com.example.aeropass.application.DTOs.CriarFormaPagamentoResponse;
import com.example.aeropass.application.DTOs.FormaPagamentoResponse;
import com.example.aeropass.domain.entities.FormaPagamento;
import com.example.aeropass.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Value("${spring.secret}")
    private String secret;

    public List<FormaPagamentoResponse> listarTodasFormaPagamentoTable(){

        return formaPagamentoRepository.findAll()
            .stream()
            .map(FormaPagamentoResponse::new)
            .toList();
    }

    public CriarFormaPagamentoResponse criarFormaPagamento(CriarFormaPagamentoRequest criarFormaPagamentoRequest) {

        if(!criarFormaPagamentoRequest.secretKey().equals(secret)) {
            return new CriarFormaPagamentoResponse(0L, "Forma de pagamento salva com sucesso!");
        }
        FormaPagamento formaPagamentoSalvar = new FormaPagamento(criarFormaPagamentoRequest);
        formaPagamentoRepository.save(formaPagamentoSalvar);

        return new CriarFormaPagamentoResponse(formaPagamentoSalvar.getId(), "Forma de pagamento salva com sucesso");
    }
}
