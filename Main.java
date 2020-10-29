import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aco.AntColonyOptimization;
import aco.Cidade;
import aco.Formiga;
import aco.Rota;

public class Main {
    

    public static void main(String[] args) {
        int op;
        String nomeArquivo;
        Scanner ler = new Scanner(System.in);
        List<Cidade> listaCidade = new ArrayList<Cidade>();
        List<Rota> listaRota;

        //Dados para o AntColonyOptimization
        int numeroMaximoIteracao;
        Double influenciaFeromonio; //alfa
        Double influenciaDistancia; //beta
        Double taxaEvaporacaoFeromonio;
        Double valorInicialFeromonio;
        Double constanteAtualizacaoFeromonio;

        System.out.println("Infome o documento que deseja ler: ");
        System.out.println("[1] - att48.tsp");
        System.out.println("[2] - bays29.tsp");
        System.out.println("[3] - eil51.tsp");

        System.out.print("Digite a opcao desejada: ");
        op = Integer.parseInt(ler.nextLine());

        switch(op){

            case 1:
                nomeArquivo = "att48-sem-cabecalho.tsp";
                break;
            case 2:
                nomeArquivo = "bays29-sem-cabecalho.tsp";
                break;
            case 3:
                nomeArquivo = "eil51-sem-cabecalho.tsp";
                break;
            default:
                nomeArquivo = "";
                break;
        }

        try{
            FileReader arquivo = new FileReader(nomeArquivo);
            BufferedReader lerArquivo = new BufferedReader(arquivo);

            //le a primeira linha do arquivo
            String linha = lerArquivo.readLine();
            // System.out.println(linha);

            while(lerArquivo.ready()){
                
                String temp[] = linha.split(" ");

                //leitura do numero da cidade
                int numeroCidade = Integer.parseInt(temp[0]); 
                //leitura da coordenada x da cidade
                Double coordenadaX = Double.parseDouble(temp[1]);
                //leitura da coordenada x da cidade
                Double coordenadaY = Double.parseDouble(temp[2]);

                Cidade cidade = new Cidade(numeroCidade, coordenadaX, coordenadaY);

                listaCidade.add(cidade);

                //comando para ler as demais linhas depois da primeira
                linha = lerArquivo.readLine();

            }//enquanto puder ler uma nova linha

            lerArquivo.close();
            arquivo.close();
        }
        catch(IOException e){
            System.err.printf("\n\n\n\nErro na abertura do arquivo: %s.\n\n", e.getMessage());
        }
        
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        System.out.print("Digite o numero de iteracoes desejado:");
        //numeroMaximoIteracao = Integer.parseInt(ler.nextLine());
        numeroMaximoIteracao = 1;

        System.out.print("Digite o valor de influencia do feromonio (padrao = 1): ");
        //influenciaFeromonio = Double.parseDouble(ler.nextLine());
        influenciaFeromonio = 1.0;


        System.out.print("Digite o valor de influencia da distancia (padrão = 1): ");
        //influenciaDistancia = Double.parseDouble(ler.nextLine());
        influenciaDistancia = 1.0;

        System.out.print("Digite o valor da taxa de evaporacao do feromonio (padrao = 0.01): ");
        //taxaEvaporacaoFeromonio = Double.parseDouble(ler.nextLine());
        taxaEvaporacaoFeromonio = 0.01;

        System.out.print("Digite o valor inicial do feromonio para todas as rotas (padrao = 0.1): ");
        //valorInicialFeromonio = Double.parseDouble(ler.nextLine());
        valorInicialFeromonio = 0.1;

        System.out.print("Digite o valor da constante de atualizacao do feromonio (padrao = 10): ");
        //constanteAtualizacaoFeromonio = Double.parseDouble(ler.nextLine());
        constanteAtualizacaoFeromonio = 10.0;

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        
        //definir a tabela de rotas
        listaRota = constroiRotas(listaCidade,valorInicialFeromonio);
        
        //atualizando a tabela de probabilidades de escolha
        for (Rota rota : listaRota) {
            rota.atualizaProbabilidadeEscolha(listaRota);
        }


        
        for (Cidade cidade : listaCidade) {
            System.out.println("\n\n\n\n");
            //colocar um formiga em cada cidade
            cidade.adicionaFormiga();
            
            //faz a formiga percorrer todas as rotas com base na cidade de origem
            cidade.getFormiga().run(listaRota, cidade.getFormiga().getCidadeOrigem());

            //atualiza a distancia percorrida por cada formiga
            cidade.getFormiga().defineDistanciaPercorrida();

            //exibe a rota percorrida pela formiga
            System.out.println(cidade.getFormiga().exibeRotaPercorrida());

            System.out.println("Distancia Percorrida: " + cidade.getFormiga().getDistanciaPercorrida());
        }


        // Formiga formiga = new Formiga();
        // formiga.setCidadeOrigem(listaCidade.get(0));

        // //realizar a primeira iteracao
        // formiga.run(listaRota, formiga.getCidadeOrigem());

        // //exibe a rota
        // System.out.println(formiga.exibeRotaPercorrida());

        //realizar a evaporacao do feromonio em todas as rotas

        //calcular o feromonio depositado por cada formiga em determinada rota

        //realizar o somatorio do feromonio depositado em cada rota

        //atualizar a taxa de feromonios com base nas formigas e nas rotas


        ler.close();
    }


    public static List<Rota> constroiRotas(List<Cidade> cidades, Double valorInicialFeromonio){
        List<Rota> novasRotas = new ArrayList<Rota>();

        for (Cidade cidadeOrigem : cidades) {
            for (Cidade cidadeDestino : cidades) {
                if(cidadeDestino != cidadeOrigem){
                    Rota novaRota = new Rota(cidadeOrigem, cidadeDestino, valorInicialFeromonio);
                    novasRotas.add(novaRota);
                }
                
            }
        }

        return novasRotas;
    }

}
