package com.alura.forumHub.controllers;

import com.alura.forumHub.entities.Curso;
import com.alura.forumHub.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listarTodos() {
        return ResponseEntity.ok(cursoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Curso> criarCurso(@Valid @RequestBody Curso curso) {
        Curso criado = cursoService.criarCurso(curso);
        return ResponseEntity.ok(criado);
    }
}
