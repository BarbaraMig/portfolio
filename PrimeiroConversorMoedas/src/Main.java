import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

public class Main{

    public static void main(String[] args) {
        int opcao = 0;
        Scanner scan = new Scanner(System.in);
        LinkedTreeMap<String,Object> listaConversao = new LinkedTreeMap<>();

        listaConversao = converterLista();

        do{
            //Exibe opções
            exibirMenu();
            opcao = scan.nextInt();

            //Caso a opção não seja válida, a conversão não será chamada

            //Caso a opção seja 0 o programa é encerrado
            if(opcao == 0){
                System.out.println("Programa Encerrado");
                break;
            }
            if(validarOpcao(opcao)){
                //Converte e exibe o valor da conversão
                executarOpcao(listaConversao, opcao);
            }
            else
                System.out.println("Opção Inválida");

        }while(opcao != 0);

    }



    private static void executarOpcao(LinkedTreeMap<String,Object> listaConversao, int opcao ) {
        Scanner scan = new Scanner(System.in);
        //Guarda o valor que será convertido
        System.out.println("digite o valor desejado para conversão:");
        float valorDesejado = scan.nextFloat();

        //Faz o calculo dependendo da moeda escolhida
        double valorResultante = calculoConversao(opcao, listaConversao,valorDesejado);
        if (!(valorResultante == -2)) {
            System.out.println("O valor resultando é " + valorResultante);
        }


    }

    private static double calculoConversao(int opcao, LinkedTreeMap<String,Object> listaConversao, float valorDesejado){
        switch (opcao){
            case 1:
                //DólarAmericano(USD) -> Peso Argentino(ARS)
                return valorDesejado * (double)listaConversao.get("ARS");

            case 2:
                //DólarAmericano(USD) -> Peso Colombiano(COP)
                return valorDesejado * (double)listaConversao.get("COP");

            case 3:
                //DólarAmericano(USD) -> Real(BRL)
                return valorDesejado * (double)listaConversao.get("BRL");

            case 4:
                //Real(BRL) -> DólarAmericano(USD)
                return (valorDesejado / (double)listaConversao.get("BRL"));

            case 5:
                //Peso Colombiano(COP) -> DólarAmericano(USD)
                return valorDesejado / (double)listaConversao.get("COP");

            case 6:
                //Peso Argentino(ARS) -> DólarAmericano(USD)
                return valorDesejado / (double)listaConversao.get("ARS");

            default:
                return -2;
        }
    }

    private static LinkedTreeMap<String,Object> converterLista(){
        Gson gson = new Gson();
        HashMap<String, Object> listaAux = new HashMap<>();
        LinkedTreeMap<String,Object> listaConversao = new LinkedTreeMap<>();

        try{
            HttpClient cliente = HttpClient.newHttpClient();

            HttpRequest pedido = HttpRequest.newBuilder().
                    uri(URI.create("https://v6.exchangerate-api.com/v6/6f2812447af7255131cb85e5/latest/USD")).build();
            HttpResponse<String> resposta = cliente.send(pedido, HttpResponse.BodyHandlers.ofString());
            // Imprime todas as conversões

            //Transforma o conteúdo da resposta toda em um HashMap
            listaAux = (HashMap<String, Object>) gson.fromJson(resposta.body(),listaAux.getClass());

            //Transforma a lista com as conversões em  em uma
            listaConversao = (LinkedTreeMap) listaAux.get("conversion_rates");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listaConversao;
    }


    public static void exibirMenu() {
        System.out.println("digite a opção desejada:" +
                "\n 1) Dólar -> Peso Argentino" +
                "\n 2) Dólar -> Peso Colombiano" +
                "\n 3) Dólar -> Real" +
                "\n 4) Real -> Dólar" +
                "\n 5) Peso Colombiano -> Dólar" +
                "\n 6) Peso Argentino -> Dólar" +
                "\n 0) Sair");
    }
    private static boolean validarOpcao(int opcao){
        return !(opcao <= 0 || opcao >= 6);
    }
}