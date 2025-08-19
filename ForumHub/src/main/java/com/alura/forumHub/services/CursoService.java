package com.alura.forumHub.services;

import com.alura.forumHub.entities.Curso;
import com.alura.forumHub.entities.Usuario;
import com.alura.forumHub.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Curso criarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public static Curso buscarPorId(Integer id,CursoRepository cursoRepository) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + id));
    }
}
