package com.alura.LiterAlura.controllers;

import com.alura.LiterAlura.entities.Livro;
import com.alura.LiterAlura.repositories.AutorRepository;
import com.alura.LiterAlura.repositories.LivroRepository;
import com.alura.LiterAlura.services.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroController(LivroService livroService, LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroService = livroService;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }


    // Listar todos os livros
    @GetMapping
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }


    // Buscar livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivro(@PathVariable Long id) {
        return livroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/{param}")
    public ResponseEntity<Livro> buscarLivro(@RequestParam String param) {
        Livro livro = livroRepository.findByTitulo(param);
        if (livro != null) {
            return ResponseEntity.ok(livro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Importar livro da API pelo ID
    @PostMapping("/importar/{id}")
    public ResponseEntity<Livro> importarLivro(@PathVariable Long id) {
        try {
            Livro livro = livroService.importarLivroDaAPIPorId(id);
            return ResponseEntity.ok(livro);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    // Importar livro da API pelo Titulo
    @PostMapping("/importar/titulo/{titulo}")
    public ResponseEntity<Livro> importarLivro(@PathVariable String titulo) {
        try {
            Livro livro = livroService.importarLivroDaAPIPorTitulo(titulo);
            return ResponseEntity.ok(livro);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    // Listar livros por autor
    @GetMapping("/{autorId}")
    public ResponseEntity<List<Livro>> listarLivrosPorAutor(@PathVariable Long autorId) {
        return autorRepository.findById(autorId)
                .map(autor -> {
                    List<Livro> livros = livroRepository.findAllByAutoresContaining(autor);
                    return ResponseEntity.ok(livros);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
