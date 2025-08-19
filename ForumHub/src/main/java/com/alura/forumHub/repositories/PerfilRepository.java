package com.alura.forumHub.repositories;


import com.alura.forumHub.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    // Você pode adicionar métodos personalizados se precisar
    Perfil findByNome(String nome);
}
