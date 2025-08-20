package com.alura.LiterAlura.repositories;

import com.alura.LiterAlura.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNome(String nome);
}
