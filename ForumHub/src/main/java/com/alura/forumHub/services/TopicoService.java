package com.alura.forumHub.services;

import com.alura.forumHub.dtos.TopicoDTO;
import com.alura.forumHub.entities.Curso;
import com.alura.forumHub.entities.Topico;
import com.alura.forumHub.entities.Usuario;
import com.alura.forumHub.repositories.CursoRepository;
import com.alura.forumHub.repositories.TopicoRepository;
import com.alura.forumHub.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;


    public TopicoService(TopicoRepository topicoRepository, CursoRepository cursoRepository, UsuarioRepository usuarioRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Topico> listarTodos() {
        return topicoRepository.findAll();
    }

    public Optional<Topico> buscarPorId(Integer id) {
        return topicoRepository.findById(id);
    }

    public Topico criarTopico(String titulo, String mensagem, Integer autorId, Integer cursoId) {
        // Verifica duplicidade
        if (!topicoRepository.findByTituloAndMensagem(titulo, mensagem).isEmpty()) {
            throw new RuntimeException("Já existe um tópico com esse título e mensagem");
        }

        Usuario autor = usuarioRepository.findById(autorId)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(titulo);
        topico.setMensagem(mensagem);
        topico.setStatus("ABERTO"); // Status inicial padrão
        topico.setAutor(autor);
        topico.setCurso(curso);

        return topicoRepository.save(topico);
    }

    public List<Topico> listarPorCurso(Integer cursoId) {
        return null;
    }

    // Atualizar tópico
    public Topico atualizarTopico(Integer id, @Valid TopicoDTO topicoDTO) {
        Topico topicoExistente = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado com id: " + id));

        // Atualiza apenas se o título foi enviado
        if (topicoDTO.getTitulo() != null && !topicoDTO.getTitulo().isBlank()) {
            topicoExistente.setTitulo(topicoDTO.getTitulo());
        }

        // Atualiza apenas se a mensagem foi enviada
        if (topicoDTO.getMensagem() != null && !topicoDTO.getMensagem().isBlank()) {
            topicoExistente.setMensagem(topicoDTO.getMensagem());
        }

        // Atualiza apenas se o autor foi enviado
        if (topicoDTO.getAutorId() != null) {
            topicoExistente.setAutor(UsuarioService.buscarPorId(topicoDTO.getAutorId(),usuarioRepository));
        }

        // Atualiza apenas se o curso foi enviado
        if (topicoDTO.getCursoId() != null) {
            topicoExistente.setCurso(CursoService.buscarPorId(topicoDTO.getCursoId(),cursoRepository));
        }

        return topicoRepository.save(topicoExistente);
    }


    // Deletar tópico
    public void deletarTopico(Integer id) {
        Topico topicoExistente = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado com id: " + id));

        topicoRepository.delete(topicoExistente);
    }
}