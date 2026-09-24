package com.example.aeropass.controllers;

import com.example.aeropass.DTOs.AtualizarStatusAviaoRequest;
import com.example.aeropass.DTOs.AtualizarStatusVooRequest;
import com.example.aeropass.entities.*;
import com.example.aeropass.repository.AviaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avioes")
@Tag(name = "Aviões", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de aviões do sistema!")
public class AviaoController {
    @Autowired
    private AviaoRepository aviaoRepository;

    @GetMapping("/listar")
    @Operation(summary = "Método de consulta de lista de aviões.", description = "Método responsável em efetuar a consulta de todos os aviões, sem filtro.")
    public ResponseEntity<?> listarTodos(){
        return  ResponseEntity.ok(aviaoRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de um avião pelo id.", description = "Método responsável em efetuar a consulta um avião, com filtro pelo id.")
    public ResponseEntity<Aviao> buscarPorId(@PathVariable Long id) {
        Aviao aviaoBanco = aviaoRepository.findById(id).orElse(null);
        if(aviaoBanco != null) {
            return ResponseEntity.ok(aviaoBanco);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de aviões.", description = "Método responsável em efetuar a criação de novos aviões.")
    public ResponseEntity<Aviao> criar(@RequestBody Aviao aviao){

        var aviaoBanco = aviaoRepository.save(aviao);
        return ResponseEntity.ok(aviaoBanco);

    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status de aviões através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de status de aviões através do ID pelo administrador.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusAviaoRequest statusRequest) {
        Aviao aviaoBanco = aviaoRepository.findById(id).orElse(null);
        if(aviaoBanco != null) {
            aviaoBanco.setStatus(statusRequest.status());
            aviaoRepository.save(aviaoBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de atributos de aviões através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de atributos de aviões através do ID pelo administrador.")
    public ResponseEntity<Aviao> atualizar(@PathVariable Long id, @RequestBody Aviao aviao) {
        try{
            Aviao aviaoBanco = aviaoRepository.findById(id).orElse(null);
            if(aviaoBanco != null) {
                aviaoBanco.setStatus(aviao.getStatus());
                aviaoBanco.setFabricante(aviao.getFabricante());
                aviaoBanco.setNumeroSerie(aviao.getNumeroSerie());
                aviaoBanco.setModelo(aviao.getModelo());
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();

        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão (atualização de status para excluído) de aviões através do ID pelo administrador.", description = "Método de exclusão (atualização de status para excluído) de aviões através do ID pelo administrador.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Aviao aviaoBanco = aviaoRepository.findById(id).orElse(null);
        if(aviaoBanco != null) {
            aviaoBanco.setStatus(EnumStatusAviao.EXCLUIDO);
            aviaoRepository.save(aviaoBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
