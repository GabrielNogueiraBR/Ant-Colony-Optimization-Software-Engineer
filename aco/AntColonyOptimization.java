package aco;

import java.util.ArrayList;
import java.util.List;

//Classe Ant Colony Optimization
public class AntColonyOptimization {
     
     public static void run(List<Cidade> listaCidade, int numeroMaximoIteracao, Double influenciaFeromonio, Double influenciaDistancia, Double taxaEvaporacaoFeromonio, Double valorInicialFeromonio, Double constanteAtualizacaoFeromonio) {
        
        List<Rota> listaRota, rotasPercorridas; //constroi as rotas entre todas as cidades
        Formiga formiga;
        Cidade cidadeOrigem = listaCidade.get(0);
        int iteracao = 0;

        listaRota = constroiRotas(listaCidade,valorInicialFeromonio); 
        formiga = new Formiga(cidadeOrigem, listaRota);
        

        

        //do {

            List<Cidade> cidadesAtualizadas = new ArrayList<Cidade>();
            Boolean cidadeNaoAtualizada = true;
            List<Rota> rotasCidade;
            Double somatorioDeTodasInfluencias = 0.0;
            //Atualiza as probabilidades das rotas
            
            for (Cidade cidade : listaCidade) {
                
                cidadeNaoAtualizada = true;
                
                for (Cidade cidadeAtualizada : cidadesAtualizadas) {
                    if(cidade.equals(cidadeAtualizada) == true)
                        cidadeNaoAtualizada = false; //caso a cidade ja tenha sido atualizada
                }
                
                if(cidadeNaoAtualizada == true){
                    
                    rotasCidade = rotasPorCidade(listaRota, cidade);

                    //para definir o valor do somatorio de todas as influencias
                    //sera utilizado no atualizaProbabilidadeEscolha(somatorioDeTodasInfluencias) para representar a divisao
                    for (Rota rotaCidade : rotasCidade) {
                        somatorioDeTodasInfluencias += rotaCidade.produtoFeromonioInversoDistancia();
                    }
                    
                    //atualiza a probabilidade de todas as cidades
                    listaRota = atualizaRotasPorCidade(listaRota, somatorioDeTodasInfluencias, cidade);

                }

                
            }

            for (Rota rota : listaRota) {
                System.out.println(rota.toString());
            }

        
        //} while (iteracao < numeroMaximoIteracao);
        
        //Depois de sair do loop, exibir a melhor solucao com base na ultima iteracao da formiga (caminho percorrido)
        

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

     //Busca dentro de uma lista de rotas, todas as rotas de determinada cidade e retorna essa lista da cidade buscada
    private static List<Rota> rotasPorCidade(List<Rota> rotas, Cidade cidadeBuscada){
        List<Rota> rotasCidade = new ArrayList<Rota>();

        for (Rota rota : rotas) {
            if(rota.getCidade1() == cidadeBuscada || rota.getCidade2() == cidadeBuscada){
                rotasCidade.add(rota);
            }
        }

        return rotasCidade;
     }

    private static List<Rota> atualizaRotasPorCidade(List<Rota> rotas, Double somatorioDeTodasInfluencias, Cidade cidade){
        
        for (Rota rota : rotas) {
            if(rota.getCidade1() == cidade){
                rota.atualizaProbabilidadeEscolha(somatorioDeTodasInfluencias);
            }
        }

        return rotas;  
        
    }

}
