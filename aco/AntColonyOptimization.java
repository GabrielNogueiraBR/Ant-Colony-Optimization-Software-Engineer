package aco;

import java.util.ArrayList;
import java.util.List;

//Classe Ant Colony Optimization
public class AntColonyOptimization {
     
     public static void run(List<Cidade> listaCidade, int numeroMaximoIteracao, Double influenciaFeromonio, Double influenciaDistancia, Double taxaEvaporacaoFeromonio, Double valorInicialFeromonio, Double constanteAtualizacaoFeromonio) {
        
        List<Rota> listaRota; //constroi as rotas entre todas as cidades

        listaRota = constroiRotas(listaCidade,valorInicialFeromonio); 

        //exibe as rotas criadas
        for (Rota rota : listaRota) {
            System.out.println(rota.getCidade1().getNumeroCidade() + "-" + rota.getCidade2().getNumeroCidade());
        }
        

     }


     public static List<Rota> constroiRotas(List<Cidade> cidades, Double valorInicialFeromonio){
        List<Rota> rotas = new ArrayList<Rota>();
        List<Cidade> cidadesNaoMapeadas = new ArrayList<Cidade>();
        
        for (Cidade cidade : cidades) {
            cidadesNaoMapeadas.add(cidade);
        }

        for (Cidade cidade1 : cidades) {
            
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
