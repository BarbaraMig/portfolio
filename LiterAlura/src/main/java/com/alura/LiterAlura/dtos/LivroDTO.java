package com.alura.LiterAlura.dtos;

import com.alura.LiterAlura.entities.Autor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroDTO {

    private Long id;

    @JsonAlias("title")
    private String titulo;

    @JsonAlias("authors")
    private List<Autor> autores;

    @JsonAlias("summaries")
    private List<String> descricao;

    private List<String> subjects;

    @JsonAlias("languages")
    private List<String> idiomas;

    @JsonAlias("download_count")
    private Integer downloads;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public List<Autor> getAutores() { return autores; }
    public void setAutores(List<Autor> autores) { this.autores = autores; }
    public List<String> getDescricao() { return descricao; }
    public void setDescricao(List<String> descricao) { this.descricao = descricao; }
    public List<String> getSubjects() { return subjects; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }
    public List<String> getIdiomas() { return idiomas; }
    public void setIdiomas(List<String> languages) { this.idiomas = idiomas; }
    public Integer getDownloads() { return downloads; }
    public void setDownloads(Integer downloads) { this.downloads = downloads; }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + (titulo != null ? titulo : "N/A") + '\'' +
                ", autores=" + (autores != null && !autores.isEmpty() ? autores : "Nenhum autor") +
                ", subjects=" + (subjects != null && !subjects.isEmpty() ? subjects : "Nenhum assunto") +
                ", idiomas=" + (idiomas != null && !idiomas.isEmpty() ? idiomas : "Nenhum idioma") +
                ", downloads=" + (downloads != null ? downloads : 0) +
                '}';
    }
}
