package aco;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Classe Ant Colony Optimization
public class AntColonyOptimization {
     
     public static void run(List<Cidade> listaCidade) {
        Scanner lerACO = new Scanner(System.in);
        int numeroMaximoIteracao, numeroIteracao = 0;
        Double influenciaFeromonio; //alfa
        Double influenciaDistancia; //beta
        Double taxaEvaporacaoFeromonio;
        Double valorInicialFeromonio;
        Double constanteAtualizacaoFeromonio;
        List<Rota> listaRota; //constroi as rotas entre todas as cidades

        System.out.println("\n\n\n\n\n\n\n\n\n");
        System.out.print("Digite o numero de iteracoes desejado: ");
        numeroMaximoIteracao = Integer.parseInt(lerACO.nextLine());

        System.out.print("Digite o valor de influencia do feromonio (padrao = 1): ");
        influenciaFeromonio = Double.parseDouble(lerACO.nextLine());

        System.out.print("Digite o valor de influencia da distancia (padrão = 1): ");
        influenciaDistancia = Double.parseDouble(lerACO.nextLine());

        System.out.print("Digite o valor da taxa de evaporacao do feromonio (padrao = 0.1): ");
        taxaEvaporacaoFeromonio = Double.parseDouble(lerACO.nextLine());

        System.out.print("Digite o valor inicial do feromonio para todas as rotas (padrao = 0.1): ");
        valorInicialFeromonio = Double.parseDouble(lerACO.nextLine());

        System.out.print("Digite o valor da constante de atualizacao do feromonio (padrao = 10): ");
        constanteAtualizacaoFeromonio = Double.parseDouble(lerACO.nextLine());

        listaRota = constroiRotas(listaCidade,valorInicialFeromonio); 

        lerACO.close();
     }


     public static List<Rota> constroiRotas(List<Cidade> cidades, Double valorInicialFeromonio){
        List<Rota> rotas = new ArrayList<Rota>();
        List<Cidade> cidadesNaoMapeadas = new ArrayList<Cidade>();
        cidadesNaoMapeadas = cidades;

        for (Cidade cidade1 : cidadesNaoMapeadas) {
            
            for (Cidade cidade2 : cidadesNaoMapeadas) {
                if(cidade1.isDiferente(cidade2)){
                    Rota rota = new Rota(cidade1, cidade2, valorInicialFeromonio);
                    rotas.add(rota);
                }
            }

            cidadesNaoMapeadas.remove(cidade1); //faz com que a combinacao entre cada cidade ocorra apenas uma vez

        }

        return rotas;
     }

}
