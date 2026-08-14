package com.example.aeropass.controllers;

import com.example.aeropass.entities.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping("")
    public ResponseEntity<?> listarTodos() {
        List<Usuario> usuarios = List.of(new Usuario(
                1L,
                "Vinícius",
                "138.987.099-57",
                "123456",
                "viniciusnascmelo@gmail.com"));

        return ResponseEntity.ok(usuarios);
    }
}
