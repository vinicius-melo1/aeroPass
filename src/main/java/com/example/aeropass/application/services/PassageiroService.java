package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.application.DTOs.AviaoResponse;
import com.example.aeropass.application.DTOs.PassageiroResponse;
import com.example.aeropass.domain.entities.Passageiro;
import com.example.aeropass.domain.repository.PassageiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassageiroService {

    @Autowired
    private PassageiroRepository passageiroRepository;

    public List<PassageiroResponse> listarTodosPassageirosTable(){

        return passageiroRepository.findAll()
                .stream()
                .map(PassageiroResponse::new)
                .toList();
    }


    public PassageiroResponse salvarPassageiro(PassageiroRequest passageiro, Long id) {

        if (id == 0L) {
            Passageiro passageiroBanco = new Passageiro(passageiro);

            passageiroRepository.save(passageiroBanco);
            return new PassageiroResponse(passageiroBanco);
        } else {
            try{
                Passageiro passageiroBanco = passageiroRepository.findById(id).orElse(null);
                if(passageiroBanco != null) {
                    passageiroBanco.setNome(passageiro.nome());
                    passageiroBanco.setCpf(passageiro.cpf());
                    passageiroBanco.setPassaporte(passageiro.passaporte());
                    passageiroBanco.setTelefone(passageiro.telefone());
                    passageiroBanco.setEmail(passageiro.email());
                    passageiroRepository.save(passageiroBanco);
                    return new PassageiroResponse(passageiroBanco);
                }

            }catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    public PassageiroResponse buscarPorId(Long id) {
        var passageiro = passageiroRepository.findById(id).orElse(null);

        if(passageiro == null) {
            return null;
        }
        return new PassageiroResponse(passageiro);
    }

    public boolean atualizarStatus(EnumStatusPassageiro status, Long id) {

        Passageiro passageiroBanco = passageiroRepository.findById(id).orElse(null);
        if(passageiroBanco != null) {
            passageiroBanco.setStatus(status);
            passageiroRepository.save(passageiroBanco);
            return true;
        }
        return false;

    }
}
