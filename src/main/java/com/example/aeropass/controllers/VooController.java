package com.example.aeropass.controllers;

import com.example.aeropass.entities.Voo;
import com.example.aeropass.repository.UsuarioRepository;
import com.example.aeropass.repository.VooRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voos")
@Tag(name = "Vôos", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de vôos do sistema.")
public class VooController {
    @Autowired
    private VooRepository vooRepository;

    @GetMapping("/listar")
    @Operation(summary = "Método de consulta de lista de vôos.", description = "Método responsável em efetuar a consulta de todos os vôos, sem filtro.")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(vooRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de vôos no sistema.", description = "Método responsável em efetuar a criação de vôos.")
    public ResponseEntity<Voo> criar(@RequestBody Voo voo){

        var vooBanco = vooRepository.save(voo);
        return ResponseEntity.ok(vooBanco);

    }
}
