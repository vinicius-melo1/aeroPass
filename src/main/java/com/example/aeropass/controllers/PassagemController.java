package com.example.aeropass.controllers;

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

    @GetMapping
    @Operation(summary = "Método de consulta de lista de passagens.", description = "Método responsável em efetuar a consulta de todos as passagens, sem filtro.")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(passagemRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de para gerar passagens.", description = "Método responsável por gerar novas passagens.")
    public ResponseEntity<Passagem> criar(@RequestBody Passagem passagem){

        var passagemBanco = passagemRepository.save(passagem);
        return ResponseEntity.ok(passagemBanco);

    }
}
