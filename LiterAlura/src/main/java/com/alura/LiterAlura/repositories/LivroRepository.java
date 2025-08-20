package com.alura.LiterAlura.repositories;

import com.alura.LiterAlura.entities.Autor;
import com.alura.LiterAlura.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findAllByAutoresContaining(Autor autor);
    Livro findByTitulo(String titulo);
    Livro findByIdAPI(Long idAPI);
}

