package com.example.aeropass.application.services;


import com.example.aeropass.application.DTOs.AviaoResponse;
import com.example.aeropass.application.DTOs.PilotoResponse;
import com.example.aeropass.domain.entities.Piloto;
import com.example.aeropass.domain.repository.PilotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PilotoService {
    @Autowired
    PilotoRepository pilotoRepository;

    public PilotoResponse buscarPorId(Long id) {
        var piloto = pilotoRepository.findById(id).orElse(null);

        if(piloto == null) {
            return null;
        }
        return new PilotoResponse(piloto);
    }

    public Set<Piloto> buscarListaEntidadePorId(Set<Long> ids) {
        List<Piloto> pilotos = pilotoRepository.findAllById(ids);
        if (pilotos.size() != ids.size()) {
            throw new IllegalArgumentException("Um ou mais pilotos não foram encontrados!");
        }
        return new HashSet<>(pilotos);
    }
}
