package com.example.aeropass.controllers;

import com.example.aeropass.DTOs.AtualizarStatusPassageiroRequest;
import com.example.aeropass.DTOs.AtualizarStatusPassagemRequest;
import com.example.aeropass.entities.EnumStatusPassageiro;
import com.example.aeropass.entities.EnumStatusPassagem;
import com.example.aeropass.entities.Passageiro;
import com.example.aeropass.entities.Passagem;
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

    @GetMapping("/listar")
    @Operation(summary = "Método de consulta de lista de passageiros.", description = "Método responsável em efetuar a consulta de todos os passageiros, sem filtro.")
    public ResponseEntity<?> listarTodos(){

        return  ResponseEntity.ok(passageiroRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de um passageiro pelo id.", description = "Método responsável em efetuar a consulta um passageiro, com filtro pelo id.")
    public ResponseEntity<Passageiro> buscarPorId(@PathVariable Long id) {
        Passageiro passageiroBanco = passageiroRepository.findById(id).orElse(null);
        if(passageiroBanco != null) {
            return ResponseEntity.ok(passageiroBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de passageiros.", description = "Método responsável em efetuar a criação de novos passageiros.")
    public ResponseEntity<Passageiro> criar(@RequestBody Passageiro passageiro){

        var passageiroBanco = passageiroRepository.save(passageiro);
        return ResponseEntity.ok(passageiroBanco);

    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status de passageiros através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de status de passageiros através do ID pelo administrador.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusPassageiroRequest statusRequest) {
        Passageiro passageiroBanco = passageiroRepository.findById(id).orElse(null);
        if(passageiroBanco != null) {
            passageiroBanco.setStatus(statusRequest.status());
            passageiroRepository.save(passageiroBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de atributos de passageiros através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de atributos de passageiros através do ID pelo administrador.")
    public ResponseEntity<Passagem> atualizar(@PathVariable Long id, @RequestBody Passageiro passageiro) {
        try{
            Passageiro passageiroBanco = passageiroRepository.findById(id).orElse(null);
            if(passageiroBanco != null) {
                passageiroBanco.setStatus(passageiro.getStatus());
                passageiroBanco.setTelefone(passageiro.getTelefone());
                passageiroBanco.setEmail(passageiro.getEmail());
                passageiroRepository.save(passageiroBanco);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();

        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão (atualização de status para excluído) de passageiros através do ID pelo administrador.", description = "Método de exclusão (atualização de status para excluído) de passageiros através do ID pelo administrador.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Passageiro passageiroBanco = passageiroRepository.findById(id).orElse(null);
        if(passageiroBanco != null) {
            passageiroBanco.setStatus(EnumStatusPassageiro.EXCLUIDO);
            passageiroRepository.save(passageiroBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
