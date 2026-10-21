package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.domain.entities.Passageiro;
import com.example.aeropass.domain.entities.Passagem;
import com.example.aeropass.domain.repository.PassagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassagemService {
    @Autowired
    private PassagemRepository passagemRepository;

    public List<PassagemResponse> listarTodasPassagensTable(){

        return passagemRepository.findAll()
                .stream()
                .map(PassagemResponse::new)
                .toList();
    }

    public PassagemResponse salvarPassagem(PassagemRequest passagem, Long id) {

        if (id == 0L) {
            Passagem passagemBanco = new Passagem(passagem);

            passagemRepository.save(passagemBanco);
            return new PassagemResponse(passagemBanco);
        } else {
            try{
                Passagem passagemBanco = passagemRepository.findById(id).orElse(null);
                if(passagemBanco != null) {
                    passagemBanco.setCodigoAssento(passagem.codigoAssento());
                    passagemBanco.setDataCompra(passagem.dataCompra());
                    passagemBanco.setValor(passagem.valor());
                    passagemBanco.setFormaPagamento(passagem.formaPagamento());
                    return new PassagemResponse(passagemBanco);
                }

            }catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    public PassagemResponse buscarPorId(Long id) {
        var passagem = passagemRepository.findById(id).orElse(null);

        if(passagem == null) {
            return null;
        }
        return new PassagemResponse(passagem);
    }

    public boolean atualizarStatus(EnumStatusPassagem status, Long id) {

        Passagem passagemBanco = passagemRepository.findById(id).orElse(null);
        if(passagemBanco != null) {
            passagemBanco.setStatus(status);
            passagemRepository.save(passagemBanco);
            return true;
        }
        return false;

    }
}
