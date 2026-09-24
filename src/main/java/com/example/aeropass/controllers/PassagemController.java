package com.example.aeropass.controllers;

import com.example.aeropass.DTOs.AtualizarStatusPassagemRequest;
import com.example.aeropass.entities.EnumStatusPassagem;
import com.example.aeropass.entities.Passagem;
import com.example.aeropass.repository.PassagemRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passagens")
@Tag(name = "Passagens", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de passagens do sistema!")
public class PassagemController {
    @Autowired
    private PassagemRepository passagemRepository;

    @GetMapping("/listar")
    @Operation(summary = "Método de consulta de lista de passagens.", description = "Método responsável em efetuar a consulta de todos as passagens, sem filtro.")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(passagemRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de uma passagem pelo id.", description = "Método responsável em efetuar a consulta uma passagem, com filtro pelo id.")
    public ResponseEntity<Passagem> buscarPorId(@PathVariable Long id) {
        Passagem passagemBanco = passagemRepository.findById(id).orElse(null);
        if(passagemBanco != null) {
            return ResponseEntity.ok(passagemBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação para gerar passagens.", description = "Método responsável por gerar novas passagens.")
    public ResponseEntity<Passagem> criar(@RequestBody Passagem passagem){

        var passagemBanco = passagemRepository.save(passagem);
        return ResponseEntity.ok(passagemBanco);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status de passagens através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de status de passagens através do ID pelo administrador.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusPassagemRequest statusRequest) {
        Passagem passagemBanco = passagemRepository.findById(id).orElse(null);
        if(passagemBanco != null) {
            passagemBanco.setStatus(statusRequest.status());
            passagemRepository.save(passagemBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de atributos de passagens através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de atributos de passagens através do ID pelo administrador.")
    public ResponseEntity<Passagem> atualizar(@PathVariable Long id, @RequestBody Passagem passagem) {
        try{
            Passagem passagemBanco = passagemRepository.findById(id).orElse(null);
            if(passagemBanco != null) {
                passagemBanco.setStatus(passagem.getStatus());
                passagemBanco.setCodigoAssento(passagem.getCodigoAssento());
                passagemBanco.setValor(passagem.getValor());
                passagemBanco.setFormaPagamento(passagem.getFormaPagamento());
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();

        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão (atualização de status para excluído) de passagens através do ID pelo administrador.", description = "Método de exclusão (atualização de status para excluído) de passagens através do ID pelo administrador.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Passagem passagemBanco = passagemRepository.findById(id).orElse(null);
        if(passagemBanco != null) {
            passagemBanco.setStatus(EnumStatusPassagem.EXCLUIDO);
            passagemRepository.save(passagemBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
