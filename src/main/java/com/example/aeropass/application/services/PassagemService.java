package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.domain.entities.FormaPagamento;
import com.example.aeropass.domain.entities.Passageiro;
import com.example.aeropass.domain.entities.Passagem;
import com.example.aeropass.domain.entities.Voo;
import com.example.aeropass.domain.repository.FormaPagamentoRepository;
import com.example.aeropass.domain.repository.PassageiroRepository;
import com.example.aeropass.domain.repository.PassagemRepository;
import com.example.aeropass.domain.repository.VooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class PassagemService {
    @Autowired
    private PassagemRepository passagemRepository;

    @Autowired
    private VooRepository vooRepository;

    @Autowired
    private PassageiroRepository passageiroRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<PassagemResponse> listarTodasPassagensTable(){
        var usuarioLogado = usuarioService.getUsuarioLogado();

        if (usuarioLogado.empresa_id() != null) {
            return passagemRepository.findByVoo_Empresa_Id(usuarioLogado.empresa_id())
                    .stream()
                    .map(PassagemResponse::new)
                    .toList();
        }

        if (usuarioLogado.authorities().contains(new SimpleGrantedAuthority("ROLE_CLIENTE"))) {
            return passagemRepository.findByPassageiro_Usuario_Id(usuarioLogado.id())
                    .stream()
                    .map(PassagemResponse::new)
                    .toList();
        }

        return passagemRepository.findAll()
                .stream()
                .map(PassagemResponse::new)
                .toList();
    }
    public List<PassagemResponse> listarPassagensPorVoo(Long vooId) {
        if (!vooRepository.existsById(vooId)) {
            return null;
        }

        return passagemRepository.findByVooId(vooId)
                .stream()
                .map(PassagemResponse::new)
                .toList();
    }

    @Transactional
    public PassagemResponse salvarPassagem(PassagemRequest request) {
        var usuarioLogado = usuarioService.getUsuarioLogado();


        Voo voo = vooRepository.findById(request.vooId()).orElse(null);

        Passagem passagem = new Passagem(request, voo);
        passagem.getVoo().validarCriacaoDePassagem(usuarioLogado.empresa_id());

        passagemRepository.save(passagem);
        return new PassagemResponse(passagem);
    }
    public PassagemResponse atualizarPassagem(PassagemRequest passagem, Long id) {

        Passagem passagemBanco = passagemRepository.findById(id).orElse(null);

        if (passagemBanco == null) {
            return null;
        }

        passagemBanco.atualizar(passagem.codigoAssento(), passagem.valor());

        passagemRepository.save(passagemBanco);
        return new PassagemResponse(passagemBanco);
    }

    @Transactional
    public PassagemResponse reservarPassagem(Long passagemId, ReservarPassagemRequest request) {
        var usuarioLogado = usuarioService.getUsuarioLogado();

        Passagem passagem = passagemRepository.findById(passagemId).orElse(null);

        Passageiro passageiro = passageiroRepository.findByUsuarioId(usuarioLogado.id()).orElse(null);

        List<FormaPagamento> encontradas = formaPagamentoRepository.findAllById(request.formasPagamentoIds());

        if (passagem == null || passageiro == null || encontradas.size() != request.formasPagamentoIds().size()) {
            return null;
        }

        passagem.reservar(passageiro, new HashSet<>(encontradas));

        passagemRepository.save(passagem);
        vooRepository.save(passagem.getVoo());
        return new PassagemResponse(passagem);
    }

    @Transactional
    public PassagemResponse cancelarPassagem(Long passagemId) {
        var usuarioLogado = usuarioService.getUsuarioLogado();

        Passagem passagem = passagemRepository.findById(passagemId).orElse(null);

        if (passagem == null || !passagem.pertenceAoUsuario(usuarioLogado.id())) {
            return null;
        }

        passagem.cancelar();

        passagemRepository.save(passagem);
        vooRepository.save(passagem.getVoo());
        return new PassagemResponse(passagem);
    }


//    public PassagemResponse salvarPassagem(PassagemRequest passagem, Long id) {
//
//        if (id == 0L) {
//            Passagem passagemBanco = new Passagem(passagem);
//
//            passagemRepository.save(passagemBanco);
//            return new PassagemResponse(passagemBanco);
//        } else {
//            try{
//                Passagem passagemBanco = passagemRepository.findById(id).orElse(null);
//                if(passagemBanco != null) {
//                    passagemBanco.setCodigoAssento(passagem.codigoAssento());
//                    passagemBanco.setDataCompra(passagem.dataCompra());
//                    passagemBanco.setValor(passagem.valor());
////                  passagemBanco.setFormaPagamento(passagem.formaPagamento());
//                    passagemRepository.save(passagemBanco);
//                    return new PassagemResponse(passagemBanco);
//                }
//
//            }catch (RuntimeException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return null;
//
//    }

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
