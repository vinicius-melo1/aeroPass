//package com.example.aeropass.presentation;
//
//import com.example.aeropass.application.DTOs.*;
//import com.example.aeropass.application.services.PassageiroService;
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
//@RequestMapping("/passageiros")
//@Tag(name = "Passageiros", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de passageiros do sistema!")
//public class PassageiroController {
//    @Autowired
//    private PassageiroService passageiroService;
//
//    @GetMapping("/listar")
//    @Operation(summary = "Método de consulta de lista de passageiros.", description = "Método responsável em efetuar a consulta de todos os passageiros, sem filtro.")
//    public ResponseEntity<List<PassageiroResponse>> listarTodos(){
//
//        return  ResponseEntity.ok(passageiroService.listarTodosPassageirosTable());
//    }
//
//    @GetMapping("/{id}")
//    @Operation(summary = "Método de consulta de um passageiro pelo id.", description = "Método responsável em efetuar a consulta um passageiro, com filtro pelo id.")
//    public ResponseEntity<PassageiroResponse> buscarPorId(@PathVariable Long id) {
//        PassageiroResponse passageiroBanco =  passageiroService.buscarPorId(id);
//        if(passageiroBanco != null) {
//            return ResponseEntity.ok(passageiroBanco);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Método de criação de passageiros.", description = "Método responsável em efetuar a criação de novos passageiros.")
//    public ResponseEntity<?> criar(@RequestBody PassageiroRequest passageiro){
//        try {
//            PassageiroResponse passageiroBanco = passageiroService.salvarPassageiro(passageiro, 0L);
//            return ResponseEntity.ok(passageiroBanco);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PatchMapping("/{id}/status")
//    @Operation(summary = "Método de atualização de status de passageiros através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de status de passageiros através do ID pelo administrador.")
//    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusPassageiroRequest statusRequest) {
//        var atualizarPassageiro = passageiroService.atualizarStatus(statusRequest.status(),id);
//
//        if(atualizarPassageiro){
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @PutMapping("/{id}")
//    @Operation(summary = "Método de atualização de atributos de passageiros através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de atributos de passageiros através do ID pelo administrador.")
//    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody PassageiroRequest passageiro) {
//        try {
//            PassageiroResponse passageiroBanco = passageiroService.salvarPassageiro(passageiro,id);
//            return ResponseEntity.ok(passageiroBanco);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//    @DeleteMapping("/{id}/excluir")
//    @Operation(summary = "Método de exclusão (atualização de status para excluído) de passageiros através do ID pelo administrador.", description = "Método de exclusão (atualização de status para excluído) de passageiros através do ID pelo administrador.")
//    public ResponseEntity<Void> excluir(@PathVariable Long id) {
//        var atualizarPassageiro = passageiroService.atualizarStatus(EnumStatusPassageiro.EXCLUIDO,id);
//
//        if(atualizarPassageiro){
//            return ResponseEntity.ok().build();
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//}
