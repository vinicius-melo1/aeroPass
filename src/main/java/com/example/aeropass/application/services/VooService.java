package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.UsuarioRequest;
import com.example.aeropass.application.DTOs.UsuarioResponse;
import com.example.aeropass.application.DTOs.VooRequest;
import com.example.aeropass.application.DTOs.VooResponse;
import com.example.aeropass.domain.entities.Usuario;
import com.example.aeropass.domain.entities.Voo;
import com.example.aeropass.domain.repository.VooRepository;
import com.example.aeropass.domain.valueObject.CPF;
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
                    vooBanco.setDataHoraSaida(voo.dataHoraSaida());
                    usuarioRepository.save(usuarioBanco);
                    return new UsuarioResponse(usuarioBanco);
                }

            }catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }
}
