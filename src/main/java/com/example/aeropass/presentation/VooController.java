package com.example.aeropass.presentation;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.application.services.VooService;
import com.example.aeropass.domain.entities.Voo;
import com.example.aeropass.domain.repository.VooRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voos")
@Tag(name = "Vôos", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de vôos do sistema.")
public class VooController {
    @Autowired
    private VooRepository vooRepository;
    @Autowired
    private VooService vooService;

    @GetMapping("/listar")
    @Operation(summary = "Método de consulta de lista de vôos.", description = "Método responsável em efetuar a consulta de todos os vôos, sem filtro.")
    public ResponseEntity<List<VooResponse>> listarTodos(){

        return  ResponseEntity.ok(vooService.listarTodosVoosTable());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de vôo por ID.", description = "Método responsável por efetuar a consulta de vôo por ID.")
    public ResponseEntity<VooResponse> buscarPorId(@PathVariable Long id) {
        VooResponse vooBanco =  vooService.buscarPorId(id);
        if(vooBanco != null) {
            return ResponseEntity.ok(vooBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de vôos no sistema.", description = "Método responsável em efetuar a criação de vôos.")
    public ResponseEntity<?> criar(@RequestBody VooRequest voo){

        try {
            VooResponse vooBanco = vooService.salvarVoo(voo, 0L);
            return ResponseEntity.ok(vooBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status de um vôo através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de status de um vôo através do ID pelo administrador.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusVooRequest statusRequest) {
        var atualizarVoo = vooService.atualizarStatus(statusRequest.status(),id);

        if(atualizarVoo){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de atributos de vôos através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de atributos de vôos através do ID pelo administrador.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody VooRequest voo) {
        try {
            VooResponse vooBanco = vooService.salvarVoo(voo,id);
            return ResponseEntity.ok(vooBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão (atualização de status para excluído) de vôos através do ID pelo administrador.", description = "Método de exclusão (atualização de status para excluído) de vôos através do ID pelo administrador.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var atualizarVoo = vooService.atualizarStatus(EnumStatusVoo.EXCLUIDO,id);

        if(atualizarVoo){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
