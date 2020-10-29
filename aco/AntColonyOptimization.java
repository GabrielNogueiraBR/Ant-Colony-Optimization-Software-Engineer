package aco;


import java.util.ArrayList;
import java.util.List;

//Classe Ant Colony Optimization
public class AntColonyOptimization {
     
     public static void run(List<Cidade> listaCidade, int numeroMaximoIteracao, Double influenciaFeromonio, Double influenciaDistancia, Double taxaEvaporacaoFeromonio, Double valorInicialFeromonio, Double constanteAtualizacaoFeromonio, List<Integer> ordemMelhoresRotas) {
        
        List<Rota> listaRota, melhoresRotas;
        int iteracao = 0, iteracaoDaMelhorRota = 1;
        Double melhorDistanciaEncontrada = 0.0;
        Formiga formigaComMenorPercurso = new Formiga();

        //definir a tabela de rotas
        listaRota = constroiRotas(listaCidade,valorInicialFeromonio);
        
        //construindo a lista com as melhores rotas ja encontradas para solucionar o problema com base em uma lista de rotas
        melhoresRotas = melhoresRotas(listaRota, ordemMelhoresRotas);

        //definindo a melhor distancia ja encontrada para o problema em questao
        melhorDistanciaEncontrada = Rota.distanciaTotalEntreRotas(melhoresRotas);

        //atualizando a tabela de probabilidades de escolha
        for (Rota rota : listaRota) {
            rota.atualizaProbabilidadeEscolha(listaRota);
        }
        
        //colocar um formiga em cada cidade
        for (Cidade cidade : listaCidade) {
            cidade.adicionaFormiga(constanteAtualizacaoFeromonio);           
        }

        //(do-while)faca enquanto nao ultrapassar o numero maximo de iteracoes e enquanto a distancia percorrida pela formiga partindo da cidade um for maior que a melhor distancia encontrada (mais curta), o loop vai continuar acontecendo
        do{

            for (Cidade cidade : listaCidade) {
                
                //faz a formiga percorrer todas as rotas com base na cidade de origem
                cidade.getFormiga().run(listaRota, cidade.getFormiga().getCidadeOrigem());

                //inverte a lista de rotas percorridas, pois ao utilizar recursividade em formiga.run() a lista criada está invertida da correta
                cidade.getFormiga().setRotasPercorridas(Rota.inverteRota(cidade.getFormiga().getRotasPercorridas()));

                //atualiza a distancia percorrida por cada formiga
                cidade.getFormiga().defineDistanciaPercorrida();
            }

            //realizar a evaporacao do feromonio em todas as rotas e retorna a lista de rotas atualizadas
            listaRota = Rota.realizaEvaporacaoFeromonioRotas(listaRota, taxaEvaporacaoFeromonio, listaCidade);

            //atualizando a tabela de probabilidades de escolha
            for (Rota rota : listaRota) {
                rota.atualizaProbabilidadeEscolha(listaRota);
            }

            //definindo a melhor rota percorrida pela formiga na primeira iteracao
            if(iteracao == 0)
                formigaComMenorPercurso = listaCidade.get(0).getFormiga();
            //definindo a melhor rota percorrida pela formiga nas demais iteracoes
            else if(listaCidade.get(0).getFormiga().getDistanciaPercorrida() < formigaComMenorPercurso.getDistanciaPercorrida()){
                formigaComMenorPercurso = listaCidade.get(0).getFormiga();
                iteracaoDaMelhorRota = iteracao + 1;
            }

            iteracao++;
        }while(iteracao < numeroMaximoIteracao);  //forcando o numero de iteracoes informado
        //}while(iteracao < numeroMaximoIteracao && listaCidade.get(0).getFormiga().getDistanciaPercorrida() > melhorDistanciaEncontrada); //para encontrar uma solucao melhor do que a da literatura
        
        System.out.println("\n\nTotal de iteracoes: " + iteracao);
        System.out.println("Iteracao com o melhor resultado: " + iteracaoDaMelhorRota);
        System.out.print("RotaFinal: ");
        System.out.println(formigaComMenorPercurso.exibeRotaPercorrida());
        System.out.println("Distancia Percorrida na rota: " + formigaComMenorPercurso.getDistanciaPercorrida());
        System.out.println("Melhor caminho encontrado pela literatura: " + melhorDistanciaEncontrada);
        
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

    public static List<Rota> melhoresRotas(List<Rota> rotas, List<Integer> ordemMelhoresRotas){
        List<Rota> novasRotas = new ArrayList<Rota>();
        int indiceAuxiliar = 0;

        for (Integer numeroCidade : ordemMelhoresRotas) {
            for (Rota rota : rotas) {
                
                //nesse caso ja mapeou todas as rotas (chegou na ultima cidade mapeada)
                if((indiceAuxiliar+1) == ordemMelhoresRotas.size()){
                    //nao realizamos nada pois na ultima rota ele ja mapeou a cidade de destino
                }

                //se a cidade de origem da rota for igual ao numero da cidade
                else if(rota.getCidadeOrigem().getNumeroCidade() == numeroCidade){

                    //se a cidade de destino da rota for igual ao numero da proxima cidade
                    if(rota.getCidadeDestino().getNumeroCidade() == ordemMelhoresRotas.get(indiceAuxiliar+1) ){
                        novasRotas.add(rota);
                    }

                }
            }
            indiceAuxiliar++;
        }
        return novasRotas;
    }

}
