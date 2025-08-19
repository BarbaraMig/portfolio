package com.alura.forumHub.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoDTO() {

    @NotBlank(message = "O título é obrigatório")
    private static String titulo;

    @NotBlank(message = "A mensagem é obrigatória")
    private static String mensagem;

    @NotNull(message = "O ID do autor é obrigatório")
    private static Integer autorId;

    @NotNull(message = "O ID do curso é obrigatório")
    private static Integer cursoId;

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getAutorId() {
        return autorId;
    }

    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    public Integer getCursoId() {
        return cursoId;
    }

    public void setCursoId(Integer cursoId) {
        this.cursoId = cursoId;
    }
}



