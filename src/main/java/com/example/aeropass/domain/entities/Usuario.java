package com.example.aeropass.domain.entities;

import com.example.aeropass.application.DTOs.CriarAdminRequest;
import com.example.aeropass.application.DTOs.EnumStatusUsuario;
import com.example.aeropass.application.DTOs.UsuarioRequest;
import com.example.aeropass.domain.valueObject.CPF;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Embedded
    private CPF cpf;

    private String senha;

    private String email;

    private String role = "ROLE_USER";

    private EnumStatusUsuario status = EnumStatusUsuario.ATIVO;

    public void setEmail(String email) {
        if(!email.isEmpty() && !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido!");
        }
        this.email = email;
    }
    public Usuario(CriarAdminRequest criarAdminRequest) {
        this.setCpf(new CPF(criarAdminRequest.cpf()));
        this.setNome(criarAdminRequest.nome());
        this.setSenha(criarAdminRequest.senha());
        this.setEmail(criarAdminRequest.email());
        this.setRole("ROLE_ADMIN");
    }

    public Usuario(UsuarioRequest usuario) {
        this.setCpf(new CPF(usuario.cpf()));
        this.setNome(usuario.nome());
        this.setSenha(usuario.senha());
        this.setEmail(usuario.email());
        this.setRole("ROLE_USER");
    }
}
