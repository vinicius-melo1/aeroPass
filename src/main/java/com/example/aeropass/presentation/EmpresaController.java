package com.example.aeropass.presentation;


import com.example.aeropass.application.DTOs.AtualizarStatusEmpresaRequest;
import com.example.aeropass.application.DTOs.EmpresaRequest;
import com.example.aeropass.application.DTOs.EmpresaResponse;
import com.example.aeropass.application.DTOs.EnumStatusEmpresa;
import com.example.aeropass.application.services.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
@Tag(name = "Empresas", description = "Grupo de API responsável por controlar a estrutura de criação e consulta de empresas do sistema!")
public class EmpresaController {
    ;

    @Autowired
    private EmpresaService empresaService;


    @GetMapping("/listar")
    @Operation(summary = "Método de consulta de lista de empresas.", description = "Método responsável por efetuar a consulta das empresas, sem filtro.")
    public ResponseEntity<List<EmpresaResponse>> listarTodos(){

        return  ResponseEntity.ok(empresaService.listarTodasEmpresasTable());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Método de consulta de empresas por ID.", description = "Método responsável por efetuar a consulta de empresas por ID.")
    public ResponseEntity<EmpresaResponse> buscarPorId(@PathVariable Long id) {
        EmpresaResponse empresaBanco = empresaService.buscarPorId(id);
        if(empresaBanco != null) {
            return ResponseEntity.ok(empresaBanco);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Método de criação de empresas.", description = "Método responsável por efetuar a criação de novas empresas.")
    public ResponseEntity<?> criar(@RequestBody EmpresaRequest empresa){

        try {
            EmpresaResponse empresaBanco = empresaService.salvarEmpresa(empresa, 0L);
            return ResponseEntity.ok(empresaBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Método de atualização de status de empresas através do ID.", description = "Método responsável por efetuar a atualização de status de empresas através do ID.")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody AtualizarStatusEmpresaRequest statusRequest) {
        var atualizarEmpresa = empresaService.atualizarStatus(statusRequest.status(),id);

        if(atualizarEmpresa){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Método de atualização de atributos de empresas através do ID.", description = "Método responsável por efetuar a atualização de atributos de empresas através do ID.")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody EmpresaRequest empresa) {
        try {
            EmpresaResponse empresaBanco = empresaService.salvarEmpresa(empresa,id);
            return ResponseEntity.ok(empresaBanco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}/excluir")
    @Operation(summary = "Método de exclusão de empresas.", description = "Método de exclusão (atualização de status para excluído) de empresas através do ID.")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
//        usuarioRepository.deleteById(id);
//        return ResponseEntity.ok().build();
        var atualizarEmpresa = empresaService.atualizarStatus(EnumStatusEmpresa.EXCLUIDO, id);

        if (atualizarEmpresa) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
