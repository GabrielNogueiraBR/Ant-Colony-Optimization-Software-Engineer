package aco;

import java.util.ArrayList;
import java.util.List;

public class Formiga {
    private List<Rota> rotasPercorridas = new ArrayList<Rota>();
    private Cidade cidadeOrigem;
    private Double distanciaPercorrida;
    private Double constanteAtualizacaoFeromonio;
    
    public Formiga(Cidade cidadeOrigem, Double constanteAtualizacaoFeromonio) {
        this.cidadeOrigem = cidadeOrigem;
        this.constanteAtualizacaoFeromonio = constanteAtualizacaoFeromonio;
    }

    public Cidade getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(Cidade cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

    public Double getConstanteAtualizacaoFeromonio() {
        return constanteAtualizacaoFeromonio;
    }

    public void setConstanteAtualizacaoFeromonio(Double constanteAtualizacaoFeromonio) {
        this.constanteAtualizacaoFeromonio = constanteAtualizacaoFeromonio;
    }

    public Double quantidadeFeromonioDepositado(){
        return (constanteAtualizacaoFeromonio/distanciaPercorrida);
    }

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

    

    public String exibeRotaPercorrida(){
        String stringRota = "" ;
        int ultimaRota = 1;

        for (Rota rota : rotasPercorridas) {
            stringRota += rota.getCidadeOrigem().getNumeroCidade() + "-";
            if(ultimaRota == rotasPercorridas.size()){
                stringRota += rota.getCidadeDestino().getNumeroCidade();
            }
            ultimaRota++;  
        }
        return stringRota;
    }

    

    public Double getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    //com base na lista de rotas percorridas, atualiza a distancia percorrida pela formiga
    public void defineDistanciaPercorrida() {
        Double somatorioDistancia = 0.0;


        for (Rota rota : rotasPercorridas) {
            somatorioDistancia += rota.getDistancia();
        }

        this.distanciaPercorrida = somatorioDistancia;

    }

    //retorna true caso a rota tenha sido percorrida pela formiga
    public Boolean isRotaPercorrida(Rota rota){

        for (Rota rotaPercorrida : rotasPercorridas) {
            if(rotaPercorrida.equals(rota)){
                return true;
            }
        }

        return false;
    }

    public List<Rota> getRotasPercorridas() {
        return rotasPercorridas;
    }

    public void setRotasPercorridas(List<Rota> rotasPercorridas) {
        this.rotasPercorridas = rotasPercorridas;
    }

    
}
