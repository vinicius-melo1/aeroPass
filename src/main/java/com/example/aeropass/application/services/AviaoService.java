package com.example.aeropass.application.services;


import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.domain.entities.Aviao;
import com.example.aeropass.domain.repository.AviaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AviaoService {

    @Autowired
    private AviaoRepository aviaoRepository;

    public List<AviaoResponse> listarTodosAvioesTable(){

        return aviaoRepository.findAll()
                .stream()
                .map(AviaoResponse::new)
                .toList();
    }

    public AviaoResponse salvarAviao(AviaoRequest aviao, Long id) {

        if (id == 0L) {

            Aviao aviaoBanco = new Aviao(aviao);
            aviaoRepository.save(aviaoBanco);
            return new AviaoResponse(aviaoBanco);
        } else {
            try{
                Aviao aviaoBanco = aviaoRepository.findById(id).orElse(null);
                if(aviaoBanco != null) {
                    aviaoBanco.setModelo(aviao.modelo());
                    aviaoBanco.setFabricante(aviao.fabricante());
                    aviaoBanco.setNumeroSerie(aviao.numeroSerie());
                    aviaoRepository.save(aviaoBanco);
                    return new AviaoResponse(aviaoBanco);
                }

            }catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    public AviaoResponse buscarPorId(Long id) {
        var aviao = aviaoRepository.findById(id).orElse(null);

        if(aviao == null) {
            return null;
        }
        return new AviaoResponse(aviao);
    }

    public boolean atualizarStatus(EnumStatusAviao status, Long id) {

        Aviao aviaoBanco = aviaoRepository.findById(id).orElse(null);
        if(aviaoBanco != null) {
            aviaoBanco.setStatus(status);
            aviaoRepository.save(aviaoBanco);
            return true;
        }
        return false;

    }
}
