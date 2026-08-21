package com.example.aeropass.controllers;

import com.example.aeropass.entities.Passageiro;
import com.example.aeropass.repository.PassageiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passageiros")
public class PassageiroController {
    @Autowired
    private PassageiroRepository passageiroRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(passageiroRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Passageiro> criar(@RequestBody Passageiro passageiro){

        var passageiroBanco = passageiroRepository.save(passageiro);
        return ResponseEntity.ok(passageiroBanco);

    }
}
