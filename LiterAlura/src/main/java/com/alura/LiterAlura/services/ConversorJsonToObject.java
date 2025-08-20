package com.alura.LiterAlura.services;

import com.alura.LiterAlura.entities.Autor;
import com.alura.LiterAlura.entities.Livro;
import com.alura.LiterAlura.repositories.AutorRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversorJsonToObject {

    private static final ObjectMapper mapper = new ObjectMapper();

    /** Converte JSON de livro da API em objeto Livro */
    public static Livro converter(String json, AutorRepository autorRepository) {
        try {
            JsonNode root = mapper.readTree(json);

            Livro livro = new Livro();
            if (root.has("id")) {
                livro.setIdAPI(root.path("id").asLong());
            }
            livro.setTitulo(root.path("title").asText());

            // Idiomas
            List<String> idiomas = new ArrayList<>();
            JsonNode languagesNode = root.path("languages");
            if (languagesNode.isArray()) {
                for (JsonNode idiomaNode : languagesNode) {
                    idiomas.add(idiomaNode.asText());
                }
            }
            livro.setIdiomas(idiomas);

            // Autores
            List<Autor> autores = new ArrayList<>();
            JsonNode authorsNode = root.path("authors");
            if (authorsNode.isArray()) {
                for (JsonNode autorNode : authorsNode) {
                    String nomeAutor = autorNode.path("name").asText().trim();
                    Autor autor = autorRepository.findByNome(nomeAutor);

                    if (autor == null) {
                        autor = new Autor();
                        autor.setNome(nomeAutor);
                        if (autorNode.has("birth_year") && !autorNode.get("birth_year").isNull()) {
                            autor.setAnoNascimento(autorNode.path("birth_year").asInt());
                        }
                        if (autorNode.has("death_year") && !autorNode.get("death_year").isNull()) {
                            autor.setAnoFalecimento(autorNode.path("death_year").asInt());
                        }
                        autor = autorRepository.save(autor);
                    }
                    autores.add(autor);
                }
            }
            livro.setAutores(autores);

            return livro;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter JSON para Livro", e);
        }
    }

    /** Converte JSON de autor da API em objeto Autor */
    public static Autor converter(String json) {
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode results = root.path("results");
            if (!results.isArray() || results.isEmpty()) {
                return null;
            }

            JsonNode autorNode = results.get(0);
            Autor autor = new Autor();
            autor.setNome(autorNode.path("name").asText().trim());
            if (autorNode.has("birth_year") && !autorNode.get("birth_year").isNull()) {
                autor.setAnoNascimento(autorNode.path("birth_year").asInt());
            }
            if (autorNode.has("death_year") && !autorNode.get("death_year").isNull()) {
                autor.setAnoFalecimento(autorNode.path("death_year").asInt());
            }
            return autor;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Extrai cada livro como string JSON da lista retornada pela API */
    public static List<String> extrairListaLivros(String jsonLista) throws IOException {
        JsonNode root = mapper.readTree(jsonLista);
        JsonNode results = root.path("results");
        List<String> livrosJson = new ArrayList<>();
        if (results.isArray()) {
            for (JsonNode node : results) {
                livrosJson.add(node.toString());
            }
        }
        return livrosJson;
    }

    /** Extrai cada autor como string JSON da lista retornada pela API */
    public static List<String> extrairListaAutores(String jsonLista) throws IOException {
        JsonNode root = mapper.readTree(jsonLista);
        JsonNode results = root.path("results");
        List<String> autoresJson = new ArrayList<>();
        if (results.isArray()) {
            for (JsonNode node : results) {
                autoresJson.add(node.toString());
            }
        }
        return autoresJson;
    }
}
