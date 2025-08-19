package com.alura.forumHub.controllers;

import com.alura.forumHub.entities.Perfil;
import com.alura.forumHub.services.PerfilService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping
    public ResponseEntity<List<Perfil>> listarTodos() {
        return ResponseEntity.ok(perfilService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Perfil> criarPerfil(@Valid @RequestBody Perfil perfil) {
        Perfil criado = perfilService.criarPerfil(perfil);
        return ResponseEntity.ok(criado);
    }
}
