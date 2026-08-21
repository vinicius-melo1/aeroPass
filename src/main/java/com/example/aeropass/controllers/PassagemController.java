package com.example.aeropass.controllers;

import com.example.aeropass.entities.Passagem;
import com.example.aeropass.repository.PassagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passagens")
public class PassagemController {
    @Autowired
    private PassagemRepository passagemRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(passagemRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Passagem> criar(@RequestBody Passagem passagem){

        var passagemBanco = passagemRepository.save(passagem);
        return ResponseEntity.ok(passagemBanco);

    }
}
