package aco;


import java.util.ArrayList;
import java.util.List;

//Classe Ant Colony Optimization
public class AntColonyOptimization {
     
     public static void run(List<Cidade> listaCidade, int numeroMaximoIteracao, Double influenciaFeromonio, Double influenciaDistancia, Double taxaEvaporacaoFeromonio, Double valorInicialFeromonio, Double constanteAtualizacaoFeromonio) {
        
        List<Rota> listaRota;
        int iteracao = 0;

        //definir a tabela de rotas
        listaRota = constroiRotas(listaCidade,valorInicialFeromonio);
        
        //atualizando a tabela de probabilidades de escolha
        for (Rota rota : listaRota) {
            rota.atualizaProbabilidadeEscolha(listaRota);
        }
        
        //colocar um formiga em cada cidade
        for (Cidade cidade : listaCidade) {
            cidade.adicionaFormiga(constanteAtualizacaoFeromonio);           
        }

        while(iteracao < numeroMaximoIteracao){
            //System.out.println("Tamanho da lista de Rota" + listaRota.size());

            for (Cidade cidade : listaCidade) {
                
                //faz a formiga percorrer todas as rotas com base na cidade de origem
                cidade.getFormiga().run(listaRota, cidade.getFormiga().getCidadeOrigem());

                //inverte a lista de rotas percorridas, pois ao utilizar recursividade em formiga.run() a lista criada está invertida da correta
                cidade.getFormiga().setRotasPercorridas(Rota.inverteRota(cidade.getFormiga().getRotasPercorridas()));

                //atualiza a distancia percorrida por cada formiga
                cidade.getFormiga().defineDistanciaPercorrida();

                //exibe a rota percorrida pela formiga
                // System.out.println(cidade.getFormiga().exibeRotaPercorrida());
                // System.out.println("Distancia Percorrida: " + cidade.getFormiga().getDistanciaPercorrida());

                //exibe a rota percorrida pela formiga da cidade 1
                // if(cidade.equals(listaCidade.get(0))){
                //     System.out.println(cidade.getFormiga().exibeRotaPercorrida());
                //     System.out.println("Distancia Percorrida: " + cidade.getFormiga().getDistanciaPercorrida());    
                // }
                
            }

            //realizar a evaporacao do feromonio em todas as rotas e retorna a lista de rotas atualizadas
            listaRota = Rota.realizaEvaporacaoFeromonioRotas(listaRota, taxaEvaporacaoFeromonio, listaCidade);

            //atualizando a tabela de probabilidades de escolha
            for (Rota rota : listaRota) {
                rota.atualizaProbabilidadeEscolha(listaRota);
            }
            
            iteracao++;
        }
        
        System.out.print("\n\n\nRotaFinal: ");
        System.out.println(listaCidade.get(0).getFormiga().exibeRotaPercorrida());
        System.out.println("Distancia Percorrida: " + listaCidade.get(0).getFormiga().getDistanciaPercorrida()); 


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
