package com.alura.forumHub.services;

import com.alura.forumHub.entities.Usuario;
import com.alura.forumHub.entities.Perfil;
import com.alura.forumHub.repositories.UsuarioRepository;
import com.alura.forumHub.repositories.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    public static Usuario buscarPorId(Integer id,UsuarioRepository usuarioRepository) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
    }

    public Usuario criarUsuario(Usuario usuario) {
        // Verifica se o perfil existe
        Optional<Perfil> perfilOpt = perfilRepository.findById(usuario.getPerfil().getId());
        if (perfilOpt.isEmpty()) {
            throw new IllegalArgumentException("Perfil não encontrado");
        }
        usuario.setPerfil(perfilOpt.get());


        return usuarioRepository.save(usuario);
    }

}
