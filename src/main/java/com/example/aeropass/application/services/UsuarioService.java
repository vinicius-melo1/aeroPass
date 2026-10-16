package com.example.aeropass.application.services;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.application.DTOs.EnumStatusUsuario;
import com.example.aeropass.domain.entities.Usuario;
import com.example.aeropass.domain.repository.UsuarioRepository;
import com.example.aeropass.domain.valueObject.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Value("${spring.secret}")
    private String secret;

    public LoginResponse validarUsuarioAutenticadoRetornaToken(LoginRequest loginRequest){
        if(usuarioRepository.existsUsuarioByEmailAndSenha(loginRequest.email(), loginRequest.senha())) {
            var token = tokenService.gerarToken(loginRequest.email());
            return new LoginResponse(token);
        }
        return null;
    }

    public List<UsuarioResponse> listarTodosUsuariosTable(){

        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioResponse::new)
                .toList();
    }

    public CriarAdminResponse criarAdmin(CriarAdminRequest criarAdminRequest) {

        if(!criarAdminRequest.secretKey().equals(secret)) {
            return new CriarAdminResponse(0L, "Usuário salvo com sucesso!");
        }

        Usuario usuarioAdminSalvar = new Usuario(criarAdminRequest);
        usuarioRepository.save(usuarioAdminSalvar);

        return new CriarAdminResponse(usuarioAdminSalvar.getId(), "Usuário salvo com sucesso");
    }

    public UsuarioResponse salvarUsuario(UsuarioRequest usuario, Long id) {

        if (id == 0L) {

            Usuario usuarioBanco = new Usuario(usuario);
            usuarioRepository.save(usuarioBanco);
            return new UsuarioResponse(usuarioBanco);
        } else {
            try{
                Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
                if(usuarioBanco != null) {
                    usuarioBanco.setNome(usuario.nome());
                    usuarioBanco.setCpf(new CPF(usuario.cpf()));
                    usuarioBanco.setEmail(usuario.email());
                    usuarioBanco.setSenha(usuario.senha());
                    usuarioRepository.save(usuarioBanco);
                    return new UsuarioResponse(usuarioBanco);
                }

            }catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return null;

    }

    public UsuarioResponse buscarPorId(Long id) {
        var usuario = usuarioRepository.findById(id).orElse(null);

        if(usuario == null) {
            return null;
        }
        return new UsuarioResponse(usuario);
    }

    public boolean atualizarStatus(EnumStatusUsuario status, Long id) {

        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null) {
            usuarioBanco.setStatus(status);
            usuarioRepository.save(usuarioBanco);
            return true;
        }
        return false;

    }
}
