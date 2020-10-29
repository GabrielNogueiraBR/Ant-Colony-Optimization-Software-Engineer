package aco;

import java.util.ArrayList;
import java.util.List;

//Classe que vai dizer a rota entre duas cidades, trazendo informações como a sua distancia e a taxa de feromonios presente na rota
public class Rota {
    private Cidade cidadeOrigem;
    private Cidade cidadeDestino;
    private Double distancia;
    private Double valorFeromonio;
    private Double probabilidadeEscolha;
    private Double somaProbabilidadeEscolha;

    public Cidade getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(Cidade cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

    public Cidade getCidadeDestino() {
        return cidadeDestino;
    }

    public void setCidadeDestino(Cidade cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
    }

    public Double getDistancia() {
        return distancia;
    }

    public Double getValorFeromonio() {
        return valorFeromonio;
    }

    public Double inversoDistancia(){
        return (1/this.distancia);
    }

    public Double produtoFeromonioInversoDistancia(){
        return (inversoDistancia() * valorFeromonio);
    }

    public Rota(Cidade cidadeOrigem, Cidade cidadeDestino, Double valorInicialFeromonio) {
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
        this.valorFeromonio =valorInicialFeromonio;
        this.distancia = cidadeOrigem.distanciaEntreCidades(cidadeDestino);
    }

    @Override
    public String toString() {
        return "Rota [cidadeDestino=" + cidadeDestino.getNumeroCidade() + ", cidadeOrigem=" + cidadeOrigem.getNumeroCidade() + ", distancia=" + distancia
                + ", probabilidadeEscolha=" + probabilidadeEscolha + ", valorFeromonio=" + valorFeromonio + "]";
    }

    public Double getProbabilidadeEscolha() {
        return probabilidadeEscolha;
    }

    //realiza a evaporacao do feromonio, com base na taxa de evaporacao
    public void evaporacaoFeromonio(Double taxaEvaporacaoFeromonio){
        this.valorFeromonio = (1 - taxaEvaporacaoFeromonio) * valorFeromonio;
    }

    public Boolean isRotaEquivalente(Rota rotaEquivalente){
        if(this.distancia == rotaEquivalente.getDistancia()){
            
            if(this.cidadeDestino == rotaEquivalente.getCidadeDestino())
                if(this.cidadeOrigem == rotaEquivalente.getCidadeOrigem())
                    return true;
            else if(this.cidadeDestino == rotaEquivalente.getCidadeOrigem())
                if(this.cidadeOrigem == rotaEquivalente.getCidadeDestino())
                    return true;
        }

        return false;
    }

    private void adicionaFeromonioDeixadoFormiga(Double somatorioConcentracaoFeromonioFormigas) {
        this.valorFeromonio += somatorioConcentracaoFeromonioFormigas;
    }
    
    public void atualizaProbabilidadeEscolha(List<Rota> todasRotas){
        
        List<Rota> rotasCidadeOrigem = Rota.getAllRotasCidade(todasRotas,this.cidadeOrigem);
        Double somatorio = 0.0;

        for (Rota rota : rotasCidadeOrigem) {
            somatorio += rota.produtoFeromonioInversoDistancia();
        }

        this.probabilidadeEscolha = this.produtoFeromonioInversoDistancia() / somatorio;
    }

    public Boolean isCidadeOrigem(Cidade cidade){
        if(this.cidadeOrigem.equals(cidade))
            return true;
        return false;
    }

    public Boolean isCidadeDestino(Cidade cidade){
        if(this.cidadeDestino.equals(cidade))
            return true;
        return false;
    }

    public Double getSomaProbabilidadeEscolha() {
        return somaProbabilidadeEscolha;
    }

    public void setSomaProbabilidadeEscolha(Double somaProbabilidadeEscolha) {
        this.somaProbabilidadeEscolha = somaProbabilidadeEscolha;
    }

    public static List<Rota> getAllRotasCidade (List<Rota> todasRotas, Cidade cidadeBuscada){

        List<Rota> rotasCidadeOrigem = new ArrayList<Rota>();

        for (Rota rota : todasRotas) {
            if(rota.isCidadeOrigem(cidadeBuscada)){
                rotasCidadeOrigem.add(rota);
            }
        }

        return rotasCidadeOrigem;
    }

    //sorteia uma rota com base em uma lista
    public static int sorteiaRotaPorCidade(List<Rota> rotasDaCidadeOrigem){
        Double porcentagemTotal = 0.0;
        Double sorteio = Math.random(); //valor entre 0.0 e 1.0
        int retorno = 0, indice = 0;

        //faz uma agregacao na porcentagem para podermos sortear um numero
        for (Rota rota : rotasDaCidadeOrigem) {
            porcentagemTotal += rota.getProbabilidadeEscolha();
            rota.setSomaProbabilidadeEscolha(porcentagemTotal);

            //definimos aquele valor 0.0001 para ser uma aproximacao
            if(rota.getSomaProbabilidadeEscolha() <= (sorteio + 0.001)){
                retorno = indice; //indice da rota
            }

            indice++;
        }

        //Exemplo
        // soma[0] = 2.16
        // soma[1] = 2.16 + 1.76 = 3.92
        // soma[2] = 3.92 + 2.04 = 5.96

        return retorno;
    }


    //exclui todas as rotas de uma lista a partir da cidade de origem
    public static List<Rota> excluiRotasDaCidadeOrigem(List<Rota> rotas, Cidade cidadeOrigem){

        List<Rota> novasRotas = new ArrayList<Rota>();

        for (Rota rota : rotas) {
            if(rota.isCidadeOrigem(cidadeOrigem) == false){
                novasRotas.add(rota);
            }
        }
        return novasRotas;
    }
    
    //exclui todas as rotas de uma lista a partir da cidade de destino
    public static List<Rota> excluiRotasDaCidadeDestino(List<Rota> rotas, Cidade cidadeDestino){

        List<Rota> novasRotas = new ArrayList<Rota>();

        for (Rota rota : rotas) {
            if(rota.isCidadeDestino(cidadeDestino) == false){
                novasRotas.add(rota);
            }
        }
        return novasRotas;
    }

    public static List<Rota> realizaEvaporacaoFeromonioRotas(List<Rota> rotas, Double taxaEvaporacaoFeromonio, List<Cidade> cidades){
        
        List<Rota> novasRotas = new ArrayList<Rota>();
        Double somatorioConcentracaoFeromonioFormigas; //a soma da concentracao deixada por cada formiga em determinada rota

        for (Rota rota : rotas) {
            somatorioConcentracaoFeromonioFormigas = 0.0; //inicialmente essa concentracao e 0
            
            //varrer todas as cidades para analisar cada formiga
            for (Cidade cidade : cidades) {
                Formiga formiga = cidade.getFormiga();

                //realizar o somatorio de todas as concentracoes de feromonio deixados na rota, a partir das formigas
                if(formiga.isRotaPercorrida(rota) == true){
                    somatorioConcentracaoFeromonioFormigas += formiga.quantidadeFeromonioDepositado();
                }

            }

            //evaporacao dos feromonios na rota em questao
            rota.evaporacaoFeromonio(taxaEvaporacaoFeromonio);

            //soma as concentracoes de feromonio depositadas pelas formigas
            rota.adicionaFeromonioDeixadoFormiga(somatorioConcentracaoFeromonioFormigas);

            novasRotas.add(rota);
        }
        return novasRotas;
    }

    
    
}
