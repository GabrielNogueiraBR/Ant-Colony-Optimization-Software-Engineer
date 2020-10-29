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
        String nomeArquivo;
        Scanner ler = new Scanner(System.in);
        List<Cidade> listaCidade = new ArrayList<Cidade>();
        

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
        numeroMaximoIteracao = 100;

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
        
        ler.close();

        AntColonyOptimization.run(listaCidade, numeroMaximoIteracao, influenciaFeromonio, influenciaDistancia, taxaEvaporacaoFeromonio, valorInicialFeromonio, constanteAtualizacaoFeromonio);

        
    }   
}
