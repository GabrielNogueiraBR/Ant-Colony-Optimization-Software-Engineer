package aco;

//Classe que vai dizer a rota entre duas cidades, trazendo informações como a sua distancia e a taxa de feromonios presente na rota
public class Rota {
    private Cidade cidade1;
    private Cidade cidade2;
    private Double distancia;
    private Double valorFeromonio;

    public Cidade getCidade1() {
        return cidade1;
    }

    public void setCidade1(Cidade cidade1) {
        this.cidade1 = cidade1;
    }

    public Cidade getCidade2() {
        return cidade2;
    }

    public void setCidade2(Cidade cidade2) {
        this.cidade2 = cidade2;
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
        this.cidade1 = cidade1;
        this.cidade2 = cidade2;
        this.valorFeromonio =valorInicialFeromonio;
        this.distancia = cidade1.distanciaEntreCidadeFinal(cidade2);
    }

    @Override
    public String toString() {
        return "Rota [cidade1=" + cidade1 + ", cidade2=" + cidade2 + ", distancia=" + distancia + ", valorFeromonio="
                + valorFeromonio + "]";
    }
    
    
}
