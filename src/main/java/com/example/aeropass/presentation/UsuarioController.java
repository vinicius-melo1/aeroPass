package com.example.aeropass.presentation;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.application.services.UsuarioService;

import com.example.aeropass.domain.repository.UsuarioRepository;
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

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/listar")
    @Operation(summary = "Método de consulta de lista de usuários.", description = "Método responsável por efetuar a consulta de todos os usuários, sem filtro.")
    public ResponseEntity<List<UsuarioResponse>> listarTodos(){

        return  ResponseEntity.ok(usuarioService.listarTodosUsuariosTable());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de usuários por ID.", description = "Método responsável por efetuar a consulta de usuários por ID.")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        UsuarioResponse usuarioBanco = usuarioService.buscarPorId(id);
        if(usuarioBanco != null) {
            return ResponseEntity.ok(usuarioBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/admin")
    public ResponseEntity<CriarAdminResponse> criarAdmin(@RequestBody CriarAdminRequest criarAdminRequest) {
        try {
            CriarAdminResponse respostaSalvar = usuarioService.criarAdmin(criarAdminRequest);
            return ResponseEntity.ok(respostaSalvar);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de usuários.", description = "Método responsável por efetuar a criação de novos usuários.")
    public ResponseEntity<?> criar(@RequestBody UsuarioRequest usuario){

        try {
            UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(usuario, 0L);
            return ResponseEntity.ok(usuarioBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status de usuários através do ID.", description = "Método responsável por efetuar a atualização de status de usuários através do ID.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusRequest statusRequest) {
        var atualizarUsuario = usuarioService.atualizarStatus(statusRequest.status(),id);

        if(atualizarUsuario){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de atributos de usuários através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de atributos de usuários através do ID pelo administrador.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody UsuarioRequest usuario) {
        try {
            UsuarioResponse usuarioBanco = usuarioService.salvarUsuario(usuario,id);
            return ResponseEntity.ok(usuarioBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/cadastrar-se")
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Método de cadastro de novos usuários.", description = "Método responsável por efetuar o cadastro de novos usuários.")
//    public ResponseEntity<Usuario> criar(@RequestBody CadastroRequest cadastroRequest){
//
//        Usuario usuario = new Usuario();
//        usuario.setNome(cadastroRequest.nome());
//        usuario.setCpf(cadastroRequest.cpf()));
//        usuario.setEmail(cadastroRequest.email());
//        usuario.setSenha(cadastroRequest.senha());
//
//        var usuarioBanco = usuarioRepository.save(usuario);
//        return ResponseEntity.ok(usuarioBanco);
//    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão de usuários.", description = "Método de exclusão (atualização de status para excluído) de usuários através do ID pelo administrador.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
//        usuarioRepository.deleteById(id);
//        return ResponseEntity.ok().build();
        var atualizarUsuario = usuarioService.atualizarStatus(EnumStatusUsuario.EXCLUIDO,id);

        if(atualizarUsuario){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscarusuariologado")
    @Operation(summary = "Usuário logado", description = "Método de busca de usuário logado")
    public ResponseEntity<UsuarioResponse> buscarUsuarioLogado() {
        return ResponseEntity.ok(usuarioService.BuscarUsuarioLogado());
    }
}
