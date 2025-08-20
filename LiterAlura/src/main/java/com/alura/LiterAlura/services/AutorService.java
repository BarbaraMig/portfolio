package com.alura.LiterAlura.services;


import com.alura.LiterAlura.entities.Autor;
import com.alura.LiterAlura.repositories.AutorRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutorService {
    private final AutorRepository autorRepository;
    private final ConsumoAPI consumoAPI;
    private static final ObjectMapper mapper = new ObjectMapper();

    public AutorService(AutorRepository autorRepository, ConsumoAPI consumoAPI) {
        this.autorRepository = autorRepository;
        this.consumoAPI = consumoAPI;
    }

    //Consultas API

    public Autor importarAutorPorNome(String nome) {
        String url = "https://gutendex.com/books/?search=" + nome.replace(" ", "%20");
        return importarAutorDaAPI(url);
    }
    public Autor importarAutorPorId(Long id) {
        String url = "https://gutendex.com/books/" + id;
        return importarAutorDaAPI(url);
    }


    public List<Autor> listarAutoresRegistrados() {
        return autorRepository.findAll();
    }

    //Auxiliares

    private Autor importarAutorDaAPI(String url) {
        try {
            String json = consumoAPI.obterDados(url);
            JsonNode root = mapper.readTree(json).path("results");

            if (!root.isArray() || root.isEmpty()) return null;

            JsonNode autorNode = root.get(0).path("authors").get(0); // pega o primeiro autor
            String nome = autorNode.path("name").asText().trim();

            Autor autorExistente = autorRepository.findByNome(nome);
            if (autorExistente != null) return autorExistente;

            Autor autor = new Autor();
            autor.setNome(nome);
            if (autorNode.has("birth_year")) {
                autor.setAnoNascimento(autorNode.path("birth_year").asInt());
            }
            if (autorNode.has("death_year")) {
                autor.setAnoFalecimento(autorNode.path("death_year").asInt());
            }

            return autorRepository.save(autor);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Autor> importarListaDeAutoresDaAPI(String url) {
        List<Autor> autoresImportados = new ArrayList<>();
        try {
            String json = consumoAPI.obterDados(url);
            JsonNode root = mapper.readTree(json).path("results");

            if (!root.isArray()) return autoresImportados;

            for (JsonNode livroNode : root) {
                JsonNode authorsNode = livroNode.path("authors");
                if (authorsNode.isArray()) {
                    for (JsonNode autorNode : authorsNode) {
                        String nome = autorNode.path("name").asText().trim();
                        Autor autorExistente = autorRepository.findByNome(nome);
                        if (autorExistente != null) {
                            autoresImportados.add(autorExistente);
                        } else {
                            Autor autor = new Autor();
                            autor.setNome(nome);
                            if (autorNode.has("birth_year")) {
                                autor.setAnoNascimento(autorNode.path("birth_year").asInt());
                            }
                            if (autorNode.has("death_year")) {
                                autor.setAnoFalecimento(autorNode.path("death_year").asInt());
                            }
                            autoresImportados.add(autorRepository.save(autor));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return autoresImportados;
    }
}
