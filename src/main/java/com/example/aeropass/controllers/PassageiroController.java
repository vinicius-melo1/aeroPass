package com.example.aeropass.controllers;

import com.example.aeropass.entities.Passageiro;
import com.example.aeropass.repository.PassageiroRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passageiros")
@Tag(name = "Passageiros", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de passageiros do sistema!")
public class PassageiroController {
    @Autowired
    private PassageiroRepository passageiroRepository;

    @GetMapping
    @Operation(summary = "Método de consulta de lista de passageiros.", description = "Método responsável em efetuar a consulta de todos os passageiros, sem filtro.")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(passageiroRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de passageiros.", description = "Método responsável em efetuar a criação de novos passageiros.")
    public ResponseEntity<Passageiro> criar(@RequestBody Passageiro passageiro){

        var passageiroBanco = passageiroRepository.save(passageiro);
        return ResponseEntity.ok(passageiroBanco);

    }
}
