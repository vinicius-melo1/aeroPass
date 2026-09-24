package com.example.aeropass.controllers;

import com.example.aeropass.DTOs.AtualizarStatusPassagemRequest;
import com.example.aeropass.DTOs.AtualizarStatusVooRequest;
import com.example.aeropass.entities.EnumStatusPassagem;
import com.example.aeropass.entities.EnumStatusVoo;
import com.example.aeropass.entities.Passagem;
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

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de vôo por ID.", description = "Método responsável por efetuar a consulta de vôo por ID.")
    public ResponseEntity<Voo> buscarPorId(@PathVariable Long id) {
        Voo vooBanco = vooRepository.findById(id).orElse(null);
        if(vooBanco != null) {
            return ResponseEntity.ok(vooBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de vôos no sistema.", description = "Método responsável em efetuar a criação de vôos.")
    public ResponseEntity<Voo> criar(@RequestBody Voo voo){

        var vooBanco = vooRepository.save(voo);
        return ResponseEntity.ok(vooBanco);

    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status de um vôo através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de status de um vôo através do ID pelo administrador.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusVooRequest statusRequest) {
        Voo vooBanco = vooRepository.findById(id).orElse(null);
        if(vooBanco != null) {
            vooBanco.setStatus(statusRequest.status());
            vooRepository.save(vooBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de atributos de vôos através do ID pelo administrador.", description = "Método responsável por efetuar a atualização de atributos de vôos através do ID pelo administrador.")
    public ResponseEntity<Voo> atualizar(@PathVariable Long id, @RequestBody Voo voo) {
        try{
            Voo vooBanco = vooRepository.findById(id).orElse(null);
            if(vooBanco != null) {
                vooBanco.setStatus(voo.getStatus());
                vooBanco.setCodigoVoo(voo.getCodigoVoo());
                vooBanco.setCapacidade(voo.getCapacidade());
                vooBanco.setAssentosDisponiveis(voo.getAssentosDisponiveis());
                vooBanco.setCidadeOrigem(voo.getCidadeOrigem());
                vooBanco.setCidadeOrigem(voo.getCidadeDestino());
                vooBanco.setDataHoraSaida(voo.getDataHoraSaida());
                vooBanco.setDataHoraChegada(voo.getDataHoraChegada());
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();

        }catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão (atualização de status para excluído) de vôos através do ID pelo administrador.", description = "Método de exclusão (atualização de status para excluído) de vôos através do ID pelo administrador.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Voo vooBanco = vooRepository.findById(id).orElse(null);
        if(vooBanco != null) {
            vooBanco.setStatus(EnumStatusVoo.EXCLUIDO);
            vooRepository.save(vooBanco);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
