package aco;

import java.util.ArrayList;
import java.util.List;

public class Formiga {
    private List<Rota> rotasPercorridas = new ArrayList<Rota>();
    private Cidade cidadeOrigem;
    

    public List<Rota> run(List<Rota> rotasDisponiveis, Cidade cidadeAtual){

        if(rotasDisponiveis.size() <= 0){
            rotasPercorridas = new ArrayList<Rota>();
            return rotasPercorridas;
        }

        else{
            
            //Lista de rotas possiveis com base na cidadeAtual
            List<Rota> rotasCidadeAtual = Rota.getAllRotasCidade(rotasDisponiveis, cidadeAtual);

            //Escolher a proxima rota com base na cidadeAtual
            int indiceProximaRota = Rota.sorteiaRotaPorCidade(rotasCidadeAtual);
            Rota rotaSorteada = rotasCidadeAtual.get(indiceProximaRota);  

            //Remover todas as rotas que possuem destino na cidadeAtual da lista de rotasDisponiveis
            rotasDisponiveis = Rota.excluiRotasDaCidadeDestino(rotasDisponiveis, cidadeAtual);

            //Remover todas as rotas que possuem origem na cidadeAtual da lista de rotasDisponiveis
            rotasDisponiveis = Rota.excluiRotasDaCidadeOrigem(rotasDisponiveis, cidadeAtual);

            //Atualizar a tabela de probabilidades da lista de rotasDisponiveis
            for (Rota rota : rotasDisponiveis) {
                rota.atualizaProbabilidadeEscolha(rotasDisponiveis);
            }

            //Atualizar a cidadeAtual com base na rota sorteada (cidadeAtual = rota.getCidadeDestino())
            //return run(rotasDisponiveis, rotaSorteada.getCidadeDestino());

            //Adicionar a rota na Lista de rotaPercorrida
            rotasPercorridas = run(rotasDisponiveis, rotaSorteada.getCidadeDestino());
            rotasPercorridas.add(rotaSorteada);
            
            return rotasPercorridas;
        }

    }

    public Cidade getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(Cidade cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

    public String exibeRotaPercorrida(){
        String stringRota = "" ;

        for (Rota rota : rotasPercorridas) {
            stringRota += rota.getCidadeOrigem().getNumeroCidade() + "-";    
        }
        return stringRota;
    }





}
