package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.domain.entities.Voo;
import com.example.aeropass.domain.repository.VooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VooService {

    @Autowired
    private VooRepository vooRepository;

    public List<VooResponse> listarTodosVoosTable(){

        return vooRepository.findAll()
                .stream()
                .map(VooResponse::new)
                .toList();
    }

    public VooResponse salvarVoo(VooRequest voo, Long id) {

        if (id == 0L) {

            Voo vooBanco = new Voo(voo);
            vooRepository.save(vooBanco);
            return new VooResponse(vooBanco);
        } else {
            try{
                Voo vooBanco = vooRepository.findById(id).orElse(null);
                if(vooBanco != null) {
                    vooBanco.setCodigoVoo(voo.codigoVoo());
                    vooBanco.setCapacidade(voo.capacidade());
                    vooBanco.setAssentosDisponiveis(voo.assentosDisponiveis());
                    vooBanco.setCidadeDestino(voo.cidadeDestino());
                    vooBanco.setCidadeOrigem(voo.cidadeOrigem());
                    vooBanco.setDataHoraSaida(voo.dataHoraSaida());
                    vooBanco.setDataHoraChegada(voo.dataHoraChegada());
                    vooRepository.save(vooBanco);
                    return new VooResponse(vooBanco);
                }

            }catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    public VooResponse buscarPorId(Long id) {
        var voo = vooRepository.findById(id).orElse(null);

        if(voo == null) {
            return null;
        }
        return new VooResponse(voo);
    }

    public boolean atualizarStatus(EnumStatusVoo status, Long id) {

        Voo vooBanco = vooRepository.findById(id).orElse(null);
        if(vooBanco != null) {
            vooBanco.setStatus(status);
            vooRepository.save(vooBanco);
            return true;
        }
        return false;

    }
}
