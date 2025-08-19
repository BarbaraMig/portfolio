package com.alura.forumHub.services;

import com.alura.forumHub.entities.Resposta;
import com.alura.forumHub.entities.Topico;
import com.alura.forumHub.entities.Usuario;
import com.alura.forumHub.repositories.RespostaRepository;
import com.alura.forumHub.repositories.TopicoRepository;
import com.alura.forumHub.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RespostaService {

    private final RespostaRepository respostaRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    public RespostaService(RespostaRepository respostaRepository, TopicoRepository topicoRepository, UsuarioRepository usuarioRepository) {
        this.respostaRepository = respostaRepository;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Resposta> listarTodos() {
        return respostaRepository.findAll();
    }

    public Resposta criarResposta(Resposta resposta) {
        // Valida tópico
        Optional<Topico> topicoOpt = topicoRepository.findById(resposta.getTopico().getId());
        if (topicoOpt.isEmpty()) {
            throw new IllegalArgumentException("Tópico não encontrado");
        }
        resposta.setTopico(topicoOpt.get());

        // Valida autor
        Optional<Usuario> autorOpt = usuarioRepository.findById(resposta.getAutor().getId());
        if (autorOpt.isEmpty()) {
            throw new IllegalArgumentException("Autor não encontrado");
        }
        resposta.setAutor(autorOpt.get());

        return respostaRepository.save(resposta);
    }
}
