//package com.example.aeropass.presentation;
//
//import com.example.aeropass.application.DTOs.*;
//import com.example.aeropass.application.services.PassagemService;
//import com.example.aeropass.domain.entities.Passagem;
//import com.example.aeropass.domain.repository.PassagemRepository;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/passagens")
//@Tag(name = "Passagens", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de passagens do sistema!")
//public class PassagemController {
//    @Autowired
//    private PassagemRepository passagemRepository;
//    @Autowired
//    private PassagemService passagemService;
//
//    @GetMapping("/listar")
//    @Operation(summary = "Método de consulta de lista de passagens.", description = "Método responsável em efetuar a consulta de todos as passagens, sem filtro.")
//    public ResponseEntity<List<PassagemResponse>> listarTodos(){
//        return  ResponseEntity.ok(passagemService.listarTodasPassagensTable());
//    }
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Método de consulta de uma passagem pelo id.", description = "Método responsável em efetuar a consulta uma passagem, com filtro pelo id.")
//    public ResponseEntity<PassagemResponse> buscarPorId(@PathVariable Long id) {
//        PassagemResponse passagemBanco =  passagemService.buscarPorId(id);
//        if(passagemBanco != null) {
//            return ResponseEntity.ok(passagemBanco);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Método de criação para gerar passagens.", description = "Método responsável por gerar novas passagens.")
//    public ResponseEntity<?> criar(@RequestBody PassagemRequest passagem){
//
//        try {
//            PassagemResponse passagemBanco = passagemService.salvarPassagem(passagem, 0L);
//            return ResponseEntity.ok(passagemBanco);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PatchMapping("/{id}/status")
//    @Operation(summary = "Método de atualização de status de passagens através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de status de passagens através do ID pelo administrador.")
//    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusPassagemRequest statusRequest) {
//        var atualizarPassagem = passagemService.atualizarStatus(statusRequest.status(),id);
//
//        if(atualizarPassagem){
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PutMapping("/{id}")
//    @Operation(summary = "Método de atualização de atributos de passagens através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de atributos de passagens através do ID pelo administrador.")
//    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody PassagemRequest passagem) {
//        try {
//            PassagemResponse passagemBanco = passagemService.salvarPassagem(passagem,id);
//            return ResponseEntity.ok(passagemBanco);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//    @DeleteMapping("/{id}/excluir")
//    @Operation(summary = "Método de exclusão (atualização de status para excluído) de passagens através do ID pelo administrador.", description = "Método de exclusão (atualização de status para excluído) de passagens através do ID pelo administrador.")
//    public ResponseEntity<Void> excluir(@PathVariable Long id) {
//        var atualizarPassagem = passagemService.atualizarStatus(EnumStatusPassagem.EXCLUIDO,id);
//
//        if(atualizarPassagem){
//            return ResponseEntity.ok().build();
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//}
