package com.example.aeropass.application.services;


import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.domain.entities.Aviao;
import com.example.aeropass.domain.entities.Empresa;
import com.example.aeropass.domain.repository.AviaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AviaoService {
    @Autowired
    private AviaoRepository aviaoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<AviaoResponse> listarTodosAvioesTable() {

        var usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.empresa_id() != null) {
            return aviaoRepository.findByEmpresa_Id(usuarioLogado.empresa_id())
                    .stream()
                    .map(AviaoResponse::new)
                    .toList();
        }

        if (usuarioLogado.authorities().contains(new SimpleGrantedAuthority("ROLE_CLIENTE"))) {
            return List.of();
        }

        return aviaoRepository.findAll()
                .stream()
                .map(AviaoResponse::new)
                .toList();
    }

    public AviaoResponse salvarAviao(AviaoRequest aviao, Long id) {

        if (id == 0L) {
            Empresa empresa = usuarioService.getEmpresaUsuarioLogado();
            Aviao aviaoBanco = new Aviao(aviao, empresa);
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

    public Aviao buscarEntidadePorId(Long id) {
        return aviaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Avião não encontrado!"));
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
