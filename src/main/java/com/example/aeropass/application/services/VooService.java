package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.domain.entities.Aviao;
import com.example.aeropass.domain.entities.Empresa;
import com.example.aeropass.domain.entities.Piloto;
import com.example.aeropass.domain.entities.Voo;
import com.example.aeropass.domain.repository.AviaoRepository;
import com.example.aeropass.domain.repository.PilotoRepository;
import com.example.aeropass.domain.repository.VooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class VooService {

    @Autowired
    private VooRepository vooRepository;

    @Autowired
    private AviaoService aviaoService;

    @Autowired
    private PilotoService pilotoService;
    @Autowired
    private UsuarioService usuarioService;

    @Transactional(readOnly = true)
    public List<VooResponse> listarTodosVoosTable(){

        return vooRepository.findAll()
                .stream()
                .map(VooResponse::new)
                .toList();
    }

    @Transactional
    public VooResponse salvarVoo(VooRequest request) {
        Empresa empresa = usuarioService.getEmpresaUsuarioLogado();
        Aviao aviao = aviaoService.buscarEntidadePorId(request.aviaoId());
        Set<Piloto> pilotos = pilotoService.buscarListaEntidadePorId(request.pilotosIds());
        Voo voo = new Voo(request, aviao, pilotos, empresa);
        return new VooResponse(vooRepository.save(voo));

    }

    @Transactional
    public VooResponse atualizarVoo(Long id, VooUpdateRequest request) {
        Voo voo = vooRepository.findById(id).orElse(null);
        if (voo == null) {
            return null;
        }

        Aviao aviao = request.aviaoId() == null ? null : aviaoService.buscarEntidadePorId(request.aviaoId());

        Set<Long> pilotosIds = request.pilotosIds() == null ? Set.of() : request.pilotosIds();
        Set<Piloto> pilotos = pilotoService.buscarListaEntidadePorId(pilotosIds);

        voo.setCodigoVoo(request.codigoVoo());
        voo.setCapacidade(request.capacidade());
        voo.setAssentosDisponiveis(request.assentosDisponiveis());
        voo.setCidadeOrigem(request.cidadeOrigem());
        voo.setCidadeDestino(request.cidadeDestino());
        voo.setDataHoraSaida(request.dataHoraSaida());
        voo.setDataHoraChegadaPrevista(request.dataHoraChegadaPrevista());
        voo.setDataHoraChegada(request.dataHoraChegada());
        voo.setAviao(aviao);
        voo.setPilotos(pilotos);

        vooRepository.save(voo);
        return new VooResponse(voo);
    }

//    public VooResponse salvarVoo(VooRequest voo, Long id) {
//
//        if (id == 0L) {
//
//            Voo vooBanco = new Voo(voo);
//            vooRepository.save(vooBanco);
//            return new VooResponse(vooBanco);
//        } else {
//            try{
//                Voo vooBanco = vooRepository.findById(id).orElse(null);
//                if(vooBanco != null) {
//                    vooBanco.setCodigoVoo(voo.codigoVoo());
//                    vooBanco.setCapacidade(voo.capacidade());
//                    vooBanco.setAssentosDisponiveis(voo.assentosDisponiveis());
//                    vooBanco.setCidadeDestino(voo.cidadeDestino());
//                    vooBanco.setCidadeOrigem(voo.cidadeOrigem());
//                    vooBanco.setDataHoraSaida(voo.dataHoraSaida());
//                    vooBanco.setDataHoraChegadaPrevista(voo.dataHoraChegadaPrevista());
//                    vooBanco.setDataHoraChegada(voo.dataHoraChegada());
//                    vooRepository.save(vooBanco);
//                    return new VooResponse(vooBanco);
//                }
//
//            }catch (RuntimeException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return null;
//
//    }

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
