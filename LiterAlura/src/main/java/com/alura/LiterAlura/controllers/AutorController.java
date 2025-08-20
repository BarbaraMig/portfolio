package com.alura.LiterAlura.controllers;

import com.alura.LiterAlura.entities.Autor;
import com.alura.LiterAlura.repositories.AutorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorRepository autorRepository;

    public AutorController(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    // =========================
    // Listar todos os autores
    // =========================
    @GetMapping
    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    // =========================
    // Buscar autor por ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscarAutor(@PathVariable Long id) {
        return autorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================
    // Criar novo autor
    // =========================
    @PostMapping("/criar")
    public Autor criarAutor(@RequestBody Autor autor) {
        return autorRepository.save(autor);
    }

    // =========================
    // Atualizar autor
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<Autor> atualizarAutor(@PathVariable Long id, @RequestBody Autor dados) {
        return autorRepository.findById(id).map(autor -> {
            autor.setNome(dados.getNome());
            autor.setAnoNascimento(dados.getAnoNascimento());
            autor.setAnoFalecimento(dados.getAnoFalecimento());
            autorRepository.save(autor);
            return ResponseEntity.ok(autor);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar autor
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarAutor(@PathVariable Long id) {
        return autorRepository.findById(id).map(autor -> {
            autorRepository.delete(autor);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

