package com.alura.forumHub.repositories;


import com.alura.forumHub.entities.Resposta;
import com.alura.forumHub.entities.Topico;
import com.alura.forumHub.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Integer> {
    List<Resposta> findByTopico(Topico topico);
    List<Resposta> findByAutor(Usuario autor);
    List<Resposta> findBySolucaoTrue();
}
