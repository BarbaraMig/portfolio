package com.alura.forumHub.controllers;


import com.alura.forumHub.dtos.TopicoDTO;
import com.alura.forumHub.entities.Topico;
import com.alura.forumHub.services.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    // Listar todos os tópicos
    @GetMapping
    public List<Topico> listarTodos() {
        return topicoService.listarTodos();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Topico> buscarPorId(@PathVariable Integer id) {
        return topicoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo tópico
    @PostMapping
    public ResponseEntity<?> criarTopico(@Valid @RequestBody TopicoDTO dto) {
        try {
            Topico topico = topicoService.criarTopico(
                    dto.getTitulo(),
                    dto.getMensagem(),
                    dto.getAutorId(),
                    dto.getCursoId()
            );
            return ResponseEntity.ok(topico);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Listar tópicos por curso
    @GetMapping("/curso/{cursoId}")
    public List<Topico> listarPorCurso(@PathVariable Integer cursoId) {
        return topicoService.listarPorCurso(cursoId);
    }

    // Atualizar um tópico existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Topico> atualizarTopico(@PathVariable Integer id,
                                                  @RequestBody @Valid TopicoDTO topicoDTO) {
        Topico atualizado = topicoService.atualizarTopico(id, topicoDTO);
        return ResponseEntity.ok(atualizado);
    }

    // Deletar um tópico (DELETE)
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarTopico(@PathVariable Integer id) {
        topicoService.deletarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
