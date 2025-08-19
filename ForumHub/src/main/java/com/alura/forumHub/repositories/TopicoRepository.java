package com.alura.forumHub.repositories;


import com.alura.forumHub.entities.Curso;
import com.alura.forumHub.entities.Topico;
import com.alura.forumHub.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Integer> {
    List<Topico> findByCurso(Curso curso);
    List<Topico> findByAutor(Usuario autor);
    List<Topico> findByStatus(String status);
    List<Topico> findByTituloAndMensagem(String titulo, String mensagem);
}
