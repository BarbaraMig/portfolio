package com.alura.LiterAlura.services;

import com.alura.LiterAlura.entities.Autor;
import com.alura.LiterAlura.entities.Livro;
import com.alura.LiterAlura.repositories.AutorRepository;
import com.alura.LiterAlura.repositories.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoAPI consumoAPI;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, ConsumoAPI consumoAPI) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.consumoAPI = consumoAPI;
    }

    //Importação de livros da API

/*
    public Livro importarBD(Long idAPI){
        livroRepository.findByIdAPI(idAPI);
        Optional<Livro> livroOpt = Optional.ofNullable(livroRepository.findByIdAPI(idAPI));
        if (livroOpt.isPresent()) {
            return livroOpt.get();
        }
        else{
            Livro livro = importarLivroDaAPIPorId(idAPI);
            if (livro == null) {
                return null; // ou lançar exceção se não encontrar nem na API
            }
        }

        return null;
    }
    public Livro verificarExistenciaBD(Long idAPI){
        return livroRepository.findByIdAPI(idAPI);
    }
    public Livro verificarExistenciaBD(String titulo){
        return livroRepository.findByTitulo(titulo);
    }*/

    public Livro importarLivroDaAPIPorId(Long idAPI) {
        return importarLivroDeURL("https://gutendex.com/books/" + idAPI);
    }

    public Livro importarLivroDaAPIPorTitulo(String titulo) {
        String encoded = titulo.replace(" ", "%20");
        return importarLivroDeURL("https://gutendex.com/books?search=" + encoded);
    }

    public List<Livro> procurarPorTitulo(String titulo) throws IOException {
        return buscarLivrosAPI("https://gutendex.com/books/?search=" + titulo.replace(" ", "%20"));
    }

    public List<Livro> procurarPorAutor(String nomeAutor) throws IOException {
        return buscarLivrosAPI("https://gutendex.com/books/?search=" + nomeAutor.replace(" ", "%20"));
    }

    public List<Livro> procurarPorLinguagens(String... idiomas) throws IOException {
        return buscarLivrosAPI("https://gutendex.com/books/?languages=" + String.join(",", idiomas));
    }

    public List<Autor> procurarAutoresVivosAno(int ano) throws IOException {
        List<Livro> livros = buscarLivrosAPI(
                "https://gutendex.com/books/?author_year_start=" + ano + "&author_year_end=" + ano);
        List<Autor> autores = new ArrayList<>();
        for (Livro l : livros) {
            for (Autor a : l.getAutores()) {
                boolean vivo = a.getAnoNascimento() != null &&
                        (a.getAnoFalecimento() == null || a.getAnoFalecimento() > ano);
                if (vivo) autores.add(a);
            }
        }
        return autores;
    }

    //Consulta ao banco de dados
    @Transactional
    public List<Livro> listarLivrosRegistrados() {
        return livroRepository.findAll();
    }

    //Auxiliares

    private Livro importarLivroDeURL(String url) {
        try {
            String json = consumoAPI.obterDados(url);
            Livro livro = ConversorJsonToObject.converter(json, autorRepository);
            return salvarLivroSeNovoComAutores(livro);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Livro> buscarLivrosAPI(String url) throws IOException {
        String jsonLista = consumoAPI.obterDados(url);
        List<String> listaJsonLivros = ConversorJsonToObject.extrairListaLivros(jsonLista);
        List<Livro> livros = new ArrayList<>();

        for (String jsonLivro : listaJsonLivros) {
            Livro livro = ConversorJsonToObject.converter(jsonLivro, autorRepository);
            if (livro != null) {
                livros.add(salvarLivroSeNovoComAutores(livro));
            }
        }
        return livros;
    }

    //Salva livro no banco e associa autores, evitando duplicatas
    private Livro salvarLivroSeNovoComAutores(Livro livro) {
        if (livro == null) return null;

        Livro existente = livroRepository.findByTitulo(livro.getTitulo());
        if (existente != null) return existente;

        List<Autor> autores = new ArrayList<>(livro.getAutores());
        livro.setAutores(new ArrayList<>()); // salva sem autores inicialmente
        Livro livroSalvo = livroRepository.save(livro);

        livroSalvo.setAutores(autores);
        return livroRepository.save(livroSalvo);
    }
}
