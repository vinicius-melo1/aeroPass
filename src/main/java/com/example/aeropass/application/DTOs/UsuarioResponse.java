package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.EnumStatusUsuario;
import com.example.aeropass.domain.entities.Usuario;

public record UsuarioResponse(Long id, String nome, String cpf, String email, EnumStatusUsuario status) {
    public UsuarioResponse(Usuario usuarioEntidade){
        this(
              usuarioEntidade.getId(),
              usuarioEntidade.getNome(),
              usuarioEntidade.getCpf(),
              usuarioEntidade.getEmail(),
              usuarioEntidade.getStatus()
        );
    }
}
