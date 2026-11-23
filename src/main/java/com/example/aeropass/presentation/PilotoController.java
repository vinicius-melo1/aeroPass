package com.example.aeropass.presentation;

import com.example.aeropass.application.DTOs.*;
import com.example.aeropass.application.services.PilotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pilotos")
@Tag(name = "Pilotos", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de pilotos do sistema!")
public class PilotoController {
    @Autowired
    private PilotoService pilotoService;

    @GetMapping("/listar")
    @Operation(summary = "Método de consulta de lista de pilotos.", description = "Método responsável em efetuar a consulta de todos os pilotos, sem filtro.")
    public ResponseEntity<List<PilotoResponse>> listarTodos(){
        return  ResponseEntity.ok(pilotoService.listarTodosPilotosTable());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de um passageiro pelo id.", description = "Método responsável em efetuar a consulta um passageiro, com filtro pelo id.")
    public ResponseEntity<PilotoResponse> buscarPorId(@PathVariable Long id) {
        PilotoResponse pilotoBanco =  pilotoService.buscarPorId(id);
        if(pilotoBanco != null) {
            return ResponseEntity.ok(pilotoBanco);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de pilotos.", description = "Método responsável em efetuar a criação de novos pilotos.")
    public ResponseEntity<?> criar(@RequestBody PilotoRequest piloto){
        try {
            PilotoResponse pilotoBanco = pilotoService.salvarPiloto(piloto, 0L);
            return ResponseEntity.ok(pilotoBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status de pilotos através do ID.", description = "Método responsável por efetuar a atualização de status de pilotos através do ID.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusPilotoRequest statusRequest) {
        var atualizarPiloto = pilotoService.atualizarStatus(statusRequest.status(),id);

        if(atualizarPiloto){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de atributos de pilotos através do ID.", description = "Método responsável por efetuar a atualização de atributos de pilotos através do ID.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody PilotoRequest piloto) {
        try {
            PilotoResponse pilotoBanco = pilotoService.salvarPiloto(piloto,id);
            return ResponseEntity.ok(pilotoBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão (atualização de status para excluído) de aviões através do ID.", description = "Método de exclusão (atualização de status para excluído) de aviões através do ID.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var atualizarPiloto = pilotoService.atualizarStatus(EnumStatusPiloto.EXCLUIDO,id);

        if(atualizarPiloto){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}
