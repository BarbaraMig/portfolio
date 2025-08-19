package com.alura.forumHub;

import java.util.Scanner;

public class Principal {
    public void executar() {
        //realizarOpcao();
    }



    private void realizarOpcao() {
        exibirMenu();
        int opcao = Integer.parseInt(insercaoDados());
        switch (opcao) {
            case 1:
                //criar topico
                break;
            case 2:
                //listar topicos criados
                break;
            case 3:
                //buscar topico especifico
                break;
            case 4:
                //atualizar topico (PUT)
                break;
            case 5:
                //apagar topico
                break;
            case 0:
                //sair
                break;
            default:
                System.out.println("opção inválida");
                break;
        }
    }

    //Recebimento de informações do usuário
    private String insercaoDados(){
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    //Exibição do Menu
    private void exibirMenu() {
        System.out.println("Escolha o número de sua opção:\n" +
                "1-Criar novo tópico\n" +
                "2-Listar tópicos criados\n" +
                "3-Buscar tópico\n" +
                "4-Atualizar tópico\n" +
                "5-Apagar tópico\n" +
                "0-sair\n");
    }
}
