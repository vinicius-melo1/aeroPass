package com.example.aeropass.application.DTOs;

import com.example.aeropass.domain.entities.Usuario;

public record UsuarioResponse(Long id, String nome, String cpf, String email, EnumStatusUsuario status, String role, Long empresaId) {
    public UsuarioResponse(Usuario usuarioEntidade){
        this(
              usuarioEntidade.getId(),
              usuarioEntidade.getNome(),
              usuarioEntidade.getCpf().toString(),
              usuarioEntidade.getEmail(),
              usuarioEntidade.getStatus(),
              usuarioEntidade.getRole(),
              usuarioEntidade.getEmpresa() != null ? usuarioEntidade.getEmpresa().getId() : null
        );
    }
}
