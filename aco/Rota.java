package aco;

//Classe que vai dizer a rota entre duas cidades, trazendo informações como a sua distancia e a taxa de feromonios presente na rota
public class Rota {
    private Cidade cidadeOrigem;
    private Cidade cidadeDestino;
    private Double distancia;
    private Double valorFeromonio;
    private Double probabilidadeEscolha;

    public Cidade getCidade1() {
        return cidadeOrigem;
    }

    public void setCidade1(Cidade cidade1) {
        this.cidadeOrigem = cidade1;
    }

    public Cidade getCidade2() {
        return cidadeDestino;
    }

    public void setCidade2(Cidade cidade2) {
        this.cidadeDestino = cidade2;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Double getValorFeromonio() {
        return valorFeromonio;
    }

    public void setValorFeromonio(Double valorFeromonio) {
        this.valorFeromonio = valorFeromonio;
    }

    public Double inversoDistancia(){
        return (1/this.distancia);
    }

    public Double produtoFeromonioInversoDistancia(){
        return (inversoDistancia() * valorFeromonio);
    }

    public Rota(Cidade cidade1, Cidade cidade2, Double valorInicialFeromonio) {
        this.cidadeOrigem = cidade1;
        this.cidadeDestino = cidade2;
        this.valorFeromonio =valorInicialFeromonio;
        this.distancia = cidade1.distanciaEntreCidadeFinal(cidade2);
    }

    @Override
    public String toString() {
        return "Rota [cidadeDestino=" + cidadeDestino.getNumeroCidade() + ", cidadeOrigem=" + cidadeOrigem.getNumeroCidade() + ", distancia=" + distancia
                + ", probabilidadeEscolha=" + probabilidadeEscolha + ", valorFeromonio=" + valorFeromonio + "]";
    }

    public Double getProbabilidadeEscolha() {
        return probabilidadeEscolha;
    }

    public void atualizaProbabilidadeEscolha(Double somatorioDeTodasInfluencias) {
        
        this.probabilidadeEscolha = produtoFeromonioInversoDistancia()/ somatorioDeTodasInfluencias;

    }

    public void setProbabilidadeEscolha(Double probabilidadeEscolha) {
        this.probabilidadeEscolha = probabilidadeEscolha;
    }
    
    
}
