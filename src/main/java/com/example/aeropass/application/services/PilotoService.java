package com.example.aeropass.application.services;


import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.domain.entities.Empresa;
import com.example.aeropass.domain.entities.Piloto;
import com.example.aeropass.domain.repository.PilotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PilotoService {
    @Autowired
    PilotoRepository pilotoRepository;

    @Autowired
    UsuarioService usuarioService;

    public PilotoResponse buscarPorId(Long id) {
        var usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null
                && usuarioLogado.authorities().contains(new SimpleGrantedAuthority("ROLE_CLIENTE"))) {
            return null;
        }

        var piloto = pilotoRepository.findById(id).orElse(null);
        if (piloto == null) {
            return null;
        }

        if (usuarioLogado.empresa_id() != null
                && !usuarioLogado.empresa_id().equals(piloto.getEmpresa().getId())) {
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

    public List<PilotoResponse> listarTodosPilotosTable() {

        var usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.empresa_id() != null) {
            return pilotoRepository.findByEmpresa_Id(usuarioLogado.empresa_id())
                    .stream()
                    .map(PilotoResponse::new)
                    .toList();
        }

        if (usuarioLogado.authorities().contains(new SimpleGrantedAuthority("ROLE_CLIENTE"))) {
            return List.of();
        }

        return pilotoRepository.findAll()
                .stream()
                .map(PilotoResponse::new)
                .toList();
    }


    public PilotoResponse salvarPiloto(PilotoRequest piloto, Long id) {

        if (id == 0L) {
            Empresa empresa = usuarioService.getEmpresaUsuarioLogado();
            Piloto pilotoBanco = new Piloto(piloto, empresa);
            pilotoRepository.save(pilotoBanco);
            return new PilotoResponse(pilotoBanco);
        } else {
            try{
                Piloto pilotoBanco = pilotoRepository.findById(id).orElse(null);
                if(pilotoBanco != null) {
                    pilotoBanco.setNome(piloto.nome());
                    pilotoBanco.setCanac(piloto.canac());
                    pilotoBanco.setTipoPiloto(piloto.tipoPiloto());
                    pilotoRepository.save(pilotoBanco);
                    return new PilotoResponse(pilotoBanco);
                }

            }catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    public boolean atualizarStatus(EnumStatusPiloto status, Long id) {

        Piloto pilotoBanco = pilotoRepository.findById(id).orElse(null);
        if(pilotoBanco != null) {
            pilotoBanco.setStatus(status);
            pilotoRepository.save(pilotoBanco);
            return true;
        }
        return false;

    }
}
