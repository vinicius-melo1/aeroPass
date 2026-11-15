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

        usuarioLogado.authorities();


        return passagemRepository.findAll()
                .stream()
                .map(PassagemResponse::new)
                .toList();
    }

    @Transactional
    public PassagemResponse salvarPassagem(PassagemRequest passagem, Long vooId) {
        Voo voo = vooRepository.findById(vooId).orElse(null);

        Passagem passagemBanco = new Passagem(passagem, voo);

        passagemRepository.save(passagemBanco);
        return new PassagemResponse(passagemBanco);
    }

    public PassagemResponse atualizarPassagem(PassagemRequest passagem, Long id) {

        try{
            Passagem passagemBanco = passagemRepository.findById(id).orElse(null);

            if (passagemBanco != null) {
                passagemBanco.setCodigoAssento(passagem.codigoAssento());
    //          passagemBanco.setDataCompra(passagem.dataCompra());
                passagemBanco.setValor(passagem.valor());

                passagemRepository.save(passagemBanco);
                return new PassagemResponse(passagemBanco);
            }
        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Transactional
    public PassagemResponse reservarPassagem(Long passagemId, ReservarPassagemRequest request) {
        Passagem passagem = passagemRepository.findById(passagemId).orElse(null);

        Passageiro passageiro = passageiroRepository.findById(request.passageiroId()).orElse(null);

        List<FormaPagamento> encontradas = formaPagamentoRepository.findAllById(request.formasPagamentoIds());
        if (encontradas.size() != request.formasPagamentoIds().size()) {
            throw new IllegalArgumentException("Alguma forma de pagamento informada não existe!");
        }

        passagem.reservar(passageiro, new HashSet<>(encontradas));

        passagemRepository.save(passagem);
        vooRepository.save(passagem.getVoo());
        return new PassagemResponse(passagem);
    }

    @Transactional
    public PassagemResponse cancelarPassagem(Long passagemId) {
        Passagem passagem = passagemRepository.findById(passagemId).orElse(null);

        if (passagem != null) {
            passagem.cancelar();

            passagemRepository.save(passagem);
            vooRepository.save(passagem.getVoo());
            return new PassagemResponse(passagem);
        }
        return null;
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
