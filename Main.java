import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aco.AntColonyOptimization;
import aco.Cidade;

public class Main {
    

    public static void main(String[] args) {
        int op;
        String nomeArquivo, nomeArquivoMelhorRota;
        Scanner ler = new Scanner(System.in);
        Long tempoInicio, tempoFinal;
        List<Integer> ordemMelhoresRotas = new ArrayList<Integer>();
        

        //Dados para o AntColonyOptimization
        int numeroMaximoIteracao;
        Double influenciaFeromonio; //alfa
        Double influenciaDistancia; //beta
        Double taxaEvaporacaoFeromonio;
        Double valorInicialFeromonio;
        Double constanteAtualizacaoFeromonio;
        List<Cidade> listaCidade = new ArrayList<Cidade>();


        System.out.println("Infome o documento que deseja ler: ");
        System.out.println("[1] - att48.tsp");
        System.out.println("[2] - bays29.tsp");
        System.out.println("[3] - eil51.tsp");

        System.out.print("Digite a opcao desejada: ");
        op = Integer.parseInt(ler.nextLine());

        switch(op){

            case 1:
                nomeArquivo = "att48-sem-cabecalho.tsp";
                nomeArquivoMelhorRota = "att48.opt.tour-sem-cabecalho.txt";
                break;
            case 2:
                nomeArquivo = "bays29-sem-cabecalho.tsp";
                nomeArquivoMelhorRota = "bays29.opt.tour-sem-cabecalho.txt";
                break;
            case 3:
                nomeArquivo = "eil51-sem-cabecalho.tsp";
                nomeArquivoMelhorRota = "eil51.opt.tour-sem-cabecalho.txt";
                break;
            default:
                nomeArquivo = "";
                nomeArquivoMelhorRota = "";
                break;
        }

        try{
            FileReader arquivo = new FileReader(nomeArquivo);
            FileReader arquivoMelhorRota = new FileReader(nomeArquivoMelhorRota);

            BufferedReader lerArquivo = new BufferedReader(arquivo);
            BufferedReader lerArquivoMelhorRota = new BufferedReader(arquivoMelhorRota);

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

            //le a primeira linha do arquivo
            String linhaMelhorRota = lerArquivoMelhorRota.readLine();

            //adiciona a lista de melhor rota, os numeros das cidades que formam a melhor rota
            while(lerArquivoMelhorRota.ready()){

                //leitura do numero da cidade
                int numeroCidade = Integer.parseInt(linhaMelhorRota);
                if(numeroCidade != -1){
                    ordemMelhoresRotas.add(numeroCidade);
                }

                linhaMelhorRota = lerArquivoMelhorRota.readLine();
            }
            lerArquivoMelhorRota.close();
            arquivoMelhorRota.close();
        }
        catch(IOException e){
            System.err.printf("\n\n\n\nErro na abertura do arquivo: %s.\n\n", e.getMessage());
        }
        
        System.out.println("\n\n");

        System.out.print("Digite o numero de iteracoes desejado:");
        numeroMaximoIteracao = Integer.parseInt(ler.nextLine());
        //numeroMaximoIteracao = 5;

        System.out.print("Digite o valor de influencia do feromonio (padrao = 1): ");
        influenciaFeromonio = Double.parseDouble(ler.nextLine());
        // influenciaFeromonio = 1.0;


        System.out.print("Digite o valor de influencia da distancia (padrão = 1): ");
        influenciaDistancia = Double.parseDouble(ler.nextLine());
        // influenciaDistancia = 1.0;

        System.out.print("Digite o valor da taxa de evaporacao do feromonio (padrao = 0.01): ");
        taxaEvaporacaoFeromonio = Double.parseDouble(ler.nextLine());
        // taxaEvaporacaoFeromonio = 0.01;

        System.out.print("Digite o valor inicial do feromonio para todas as rotas (padrao = 0.1): ");
        valorInicialFeromonio = Double.parseDouble(ler.nextLine());
        // valorInicialFeromonio = 0.1;

        System.out.print("Digite o valor da constante de atualizacao do feromonio (padrao = 10): ");
        constanteAtualizacaoFeromonio = Double.parseDouble(ler.nextLine());
        // constanteAtualizacaoFeromonio = 10.0;
        
        ler.close();

        //registra o tempo de inicio de execucao
        tempoInicio = System.currentTimeMillis();

        AntColonyOptimization.run(listaCidade, numeroMaximoIteracao, influenciaFeromonio, influenciaDistancia, taxaEvaporacaoFeromonio, valorInicialFeromonio, constanteAtualizacaoFeromonio, ordemMelhoresRotas);

        //registra o tempo final de execucao
        tempoFinal = System.currentTimeMillis();

        System.out.println("Tempo decorrido: " + (tempoFinal - tempoInicio)/1000 + " (s)");
        
    }   
}
