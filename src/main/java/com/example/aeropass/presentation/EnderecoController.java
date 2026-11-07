package com.example.aeropass.presentation;


import com.example.aeropass.application.DTOs.EnderecoResponse;
import com.example.aeropass.application.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/{cep}")
    public EnderecoResponse buscarEndereco(@PathVariable String cep) {
        return enderecoService.BuscarEndereco(cep);
    }

}
