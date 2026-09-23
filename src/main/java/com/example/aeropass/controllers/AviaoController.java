package com.example.aeropass.controllers;

import com.example.aeropass.repository.AviaoRepository;
import com.example.aeropass.repository.VooRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passagens")
@Tag(name = "Passagens", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de passagens do sistema!")
public class AviaoController {
    @Autowired
    private AviaoRepository aviaoRepository;
    @Autowired
    private VooRepository vooRepository;

    @GetMapping("/listar")
    @Operation(summary = "Método de consulta de lista de aviões.", description = "Método responsável em efetuar a consulta de todos os aviões, sem filtro.")
    public ResponseEntity<?> listarTodos(){
        return  ResponseEntity.ok(vooRepository.findAll());
    }
}
