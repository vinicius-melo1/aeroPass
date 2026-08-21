package com.example.aeropass.controllers;

import com.example.aeropass.entities.Voo;
import com.example.aeropass.repository.UsuarioRepository;
import com.example.aeropass.repository.VooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voos")
public class VooController {
    @Autowired
    private VooRepository vooRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(vooRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Voo> criar(@RequestBody Voo voo){

        var vooBanco = vooRepository.save(voo);
        return ResponseEntity.ok(vooBanco);

    }
}
