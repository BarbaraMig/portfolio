package com.alura.forumHub.controllers;

import com.alura.forumHub.entities.Resposta;
import com.alura.forumHub.services.RespostaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    private final RespostaService respostaService;

    public RespostaController(RespostaService respostaService) {
        this.respostaService = respostaService;
    }

    @GetMapping
    public ResponseEntity<List<Resposta>> listarTodos() {
        return ResponseEntity.ok(respostaService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Resposta> criarResposta(@Valid @RequestBody Resposta resposta) {
        Resposta criado = respostaService.criarResposta(resposta);
        return ResponseEntity.ok(criado);
    }
}
