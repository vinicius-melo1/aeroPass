//package com.example.aeropass.presentation;
//
//import com.example.aeropass.application.DTOs.*;
//import com.example.aeropass.application.services.AviaoService;
//import com.example.aeropass.domain.entities.Aviao;
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
//@RequestMapping("/avioes")
//@Tag(name = "Aviões", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de aviões do sistema!")
//public class AviaoController {
//
//    @Autowired
//    private AviaoService aviaoService;
//
//    @GetMapping("/listar")
//    @Operation(summary = "Método de consulta de lista de aviões.", description = "Método responsável em efetuar a consulta de todos os aviões, sem filtro.")
//    public ResponseEntity<List<AviaoResponse>> listarTodos(){
//        return  ResponseEntity.ok(aviaoService.listarTodosAvioesTable());
//    }
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Método de consulta de um avião pelo id.", description = "Método responsável em efetuar a consulta um avião, com filtro pelo id.")
//    public ResponseEntity<AviaoResponse> buscarPorId(@PathVariable Long id) {
//        AviaoResponse aviaoBanco =  aviaoService.buscarPorId(id);
//        if(aviaoBanco != null) {
//            return ResponseEntity.ok(aviaoBanco);
//        }
//        return ResponseEntity.notFound().build();
//    }
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Método de criação de aviões.", description = "Método responsável em efetuar a criação de novos aviões.")
//    public ResponseEntity<?> criar(@RequestBody AviaoRequest aviao){
//
//        try {
//            AviaoResponse aviaoBanco = aviaoService.salvarAviao(aviao, 0L);
//            return ResponseEntity.ok(aviaoBanco);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PatchMapping("/{id}/status")
//    @Operation(summary = "Método de atualização de status de aviões através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de status de aviões através do ID pelo administrador.")
//    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusAviaoRequest statusRequest) {
//        var atualizarAviao = aviaoService.atualizarStatus(statusRequest.status(),id);
//
//        if(atualizarAviao){
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PutMapping("/{id}")
//    @Operation(summary = "Método de atualização de atributos de aviões através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de atributos de aviões através do ID pelo administrador.")
//    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AviaoRequest aviao) {
//        try {
//            AviaoResponse aviaoBanco = aviaoService.salvarAviao(aviao,id);
//            return ResponseEntity.ok(aviaoBanco);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/{id}/excluir")
//    @Operation(summary = "Método de exclusão (atualização de status para excluído) de aviões através do ID pelo administrador.", description = "Método de exclusão (atualização de status para excluído) de aviões através do ID pelo administrador.")
//    public ResponseEntity<Void> excluir(@PathVariable Long id) {
//        var atualizarAviao = aviaoService.atualizarStatus(EnumStatusAviao.EXCLUIDO,id);
//
//        if(atualizarAviao){
//            return ResponseEntity.ok().build();
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//}
