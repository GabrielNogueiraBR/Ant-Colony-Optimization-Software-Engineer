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
        
        ler.close();

        // //exibe todas as cidades - apenas demonstracao
        // for (Cidade cidade : listaCidade) {
        //     System.out.println(cidade.toString());
        // }
            
        AntColonyOptimization.run(listaCidade);



        
    }

}
