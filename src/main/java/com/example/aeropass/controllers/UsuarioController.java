package com.example.aeropass.controllers;

import com.example.aeropass.DTOs.AtualizarStatusRequest;
import com.example.aeropass.DTOs.CadastroRequest;
import com.example.aeropass.entities.EnumStatusUsuario;
import com.example.aeropass.entities.Usuario;
import com.example.aeropass.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de usuários do sistema!")
public class UsuarioController {
    @Autowired // Injeção de dependencia
    private UsuarioRepository usuarioRepository;

    @GetMapping("/listar")
    @Operation(summary = "Método de consulta de lista de usuários.", description = "Método responsável por efetuar a consulta de todos os usuários, sem filtro.")
    public ResponseEntity<?> listarTodos(){
        return  ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null) {
            return ResponseEntity.ok(usuarioBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de usuários pelo administrador.", description = "Método responsável por efetuar a criação de novos usuários pelo administrador.")
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario){
        var usuarioBanco = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioBanco);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest) {
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null) {
            usuarioBanco.setStatus(statusRequest.status());
            usuarioRepository.save(usuarioBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try{
            Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
            if(usuarioBanco != null) {
                usuarioBanco.setStatus(usuario.getStatus());
                usuarioBanco.setNome(usuario.getNome());
                usuarioBanco.setCpf(usuario.getCpf());
                usuarioBanco.setEmail(usuario.getEmail());
                usuarioBanco.setSenha(usuario.getSenha());
                usuarioRepository.save(usuarioBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();

        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/cadastrar-se")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de cadastro de novos usuários.", description = "Método responsável por efetuar o cadastro de novos usuários.")
    public ResponseEntity<Usuario> criar(@RequestBody CadastroRequest cadastroRequest){

        Usuario usuario = new Usuario();
        usuario.setNome(cadastroRequest.nome());
        usuario.setCpf(cadastroRequest.cpf());
        usuario.setEmail(cadastroRequest.email());
        usuario.setSenha(cadastroRequest.senha());

        var usuarioBanco = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioBanco);
    }

    @DeleteMapping("/{id}/excluir")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
//        usuarioRepository.deleteById(id);
//        return ResponseEntity.ok().build();
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        if(usuarioBanco != null) {
            usuarioBanco.setStatus(EnumStatusUsuario.EXCLUIDO);
            usuarioRepository.save(usuarioBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
