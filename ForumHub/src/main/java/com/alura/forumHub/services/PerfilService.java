package com.alura.forumHub.services;

import com.alura.forumHub.entities.Perfil;
import com.alura.forumHub.repositories.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public List<Perfil> listarTodos() {
        return perfilRepository.findAll();
    }

    public Perfil criarPerfil(Perfil perfil) {
        return perfilRepository.save(perfil);
    }
}
