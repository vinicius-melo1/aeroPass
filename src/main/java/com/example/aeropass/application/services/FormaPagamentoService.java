package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.CriarAdminResponse;
import com.example.aeropass.application.DTOs.CriarFormaPagamentoRequest;
import com.example.aeropass.application.DTOs.CriarFormaPagamentoResponse;
import com.example.aeropass.application.DTOs.FormaPagamentoResponse;
import com.example.aeropass.domain.entities.FormaPagamento;
import com.example.aeropass.domain.entities.Usuario;
import com.example.aeropass.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamentoResponse> listarTodosUsuariosTable(){

        return formaPagamentoRepository.findAll()
                .stream()
                .map(FormaPagamentoResponse::new)
                .toList();
    }

    public CriarFormaPagamentoResponse criarFormaPagamento(CriarFormaPagamentoRequest criarFormaPagamentoRequest) {
        FormaPagamento formaPagamentoSalvar = new FormaPagamento(criarFormaPagamentoRequest);
        formaPagamentoRepository.save(formaPagamentoSalvar);

        return new CriarFormaPagamentoResponse(formaPagamentoSalvar.getId(), "Forma de pagamento salva com sucesso");
    }
}
