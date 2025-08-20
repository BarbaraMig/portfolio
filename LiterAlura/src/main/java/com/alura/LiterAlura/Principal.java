package com.alura.LiterAlura;

import com.alura.LiterAlura.entities.Livro;
import com.alura.LiterAlura.repositories.AutorRepository;
import com.alura.LiterAlura.repositories.LivroRepository;
import com.alura.LiterAlura.services.AutorService;
import com.alura.LiterAlura.services.ConsumoAPI;
import com.alura.LiterAlura.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {
    @Autowired
    LivroRepository livroRepository;
    @Autowired
    AutorRepository autorRepository;

    LivroService livroService;
    AutorService autorService;

    @Autowired
    public Principal(LivroRepository livroRepository, AutorRepository autorRepository, LivroService livroService, AutorService autorService) {
        this.autorRepository=autorRepository;
        this.livroRepository=livroRepository;
        this.livroService=livroService;
        this.autorService=autorService;
    }

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.autorRepository=autorRepository;
        this.livroRepository=livroRepository;
    }
    public void executar() throws IOException {
        Scanner scan = new Scanner(System.in);
        int opcao;
        String json = criarJson();

        do{
            exibirMenu();
            opcao = Integer.parseInt(scan.nextLine());
            realizarOpcao(opcao,json,livroService,autorService);
        }while(opcao!=0);
    }
    private static String criarJson() {
        String endereco = "https://gutendex.com/books/";
        var consumoAPI = new ConsumoAPI();

        return consumoAPI.obterDados(endereco);
    }

    private static void realizarOpcao(int opcao, String json, LivroService livroService, AutorService autorService) throws IOException {
        String titulo;
        int ano;
        switch (opcao){
            case 1:
                titulo = insercaoDados("insira o titulo do livro");
                System.out.println(livroService.procurarPorTitulo(titulo));
                break;
            case 2:
                List<Livro> livrosList = livroService.listarLivrosRegistrados();
                livrosList.forEach(Livro::toString);
                break;
            case 3:
                System.out.println(autorService.listarAutoresRegistrados());
                break;
            case 4:
                ano = Integer.parseInt(insercaoDados("insira o ano desejado"));
                System.out.println(livroService.procurarAutoresVivosAno(ano));
                break;
            case 5:
                exibirIdiomas();
                String idioma = insercaoDados("insira o idioma desejado");
                System.out.println(livroService.procurarPorLinguagens(idioma));
                break;
            case 0:
                break;
            default:
                System.out.println("opção inválida");
                break;
        }
    }

    private static void listarLivroPorIdioma() {

        String idioma = insercaoDados("Escolha a linguagem\n" +
                "fr-francês\n" +
                "en-inglês\n" +
                "pt-português");
    }



    //Recebe os dados inseridos pelo usuário
    private static String insercaoDados(String mensagem){
        System.out.println(mensagem);
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();

    }

    private static void exibirMenu() {
        System.out.println("Escolha o número de sua opção:\n" +
                "1-Buscar livros pelo titulo\n" +
                "2-listar livros registrados\n" +
                "3-listar  autores registrados\n" +
                "4-listar autores vivos em determinado ano\n" +
                "5-listar livros em determinado idioma\n" +
                "0-sair\n");
    }
    private static void exibirIdiomas() {
        System.out.println("Escolha seu idioma:\n" +
                "pt-Português\n" +
                "en-Inglês\n" +
                "fr-Francês\n");
    }
}
