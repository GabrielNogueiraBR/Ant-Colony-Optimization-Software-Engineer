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

    //Com base em uma lista de formigas, realizar a atualizacao da concentracao de feromonios
    public void atualizaConcentracaoFeromonio(List<Formiga> formigas){

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
    
    public void atualizaProbabilidadeEscolha(List<Rota> todasRotas){
        
        List<Rota> rotasCidadeOrigem = getAllRotasCidadeOrigem(todasRotas);
        Double somatorio = 0.0;

        for (Rota rota : rotasCidadeOrigem) {
            somatorio += rota.produtoFeromonioInversoDistancia();
        }

        this.probabilidadeEscolha = this.produtoFeromonioInversoDistancia() / somatorio;
    }

    public List<Rota> getAllRotasCidadeOrigem (List<Rota> todasRotas){

        List<Rota> rotasCidadeOrigem = new ArrayList<Rota>();

        for (Rota rota : todasRotas) {
            if(rota.isCidadeOrigem(this.cidadeOrigem)){
                rotasCidadeOrigem.add(rota);
            }
        }

        return rotasCidadeOrigem;
    }

    public Boolean isCidadeOrigem(Cidade cidade){
        if(this.cidadeOrigem.equals(cidade))
            return true;
        return false;
    }
    
}
