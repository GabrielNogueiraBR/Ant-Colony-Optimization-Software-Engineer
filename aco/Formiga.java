package aco;



import java.util.List;

public class Formiga {
    private Cidade cidadeOrigem;
    private List<Rota> rotasPossiveis;
    private List<Rota> rotasPercorridas;
    private List<Rota> rotasCidade;
    

    public Formiga(Cidade cidadeOrigem, List<Rota> rotasPossiveis) {
        this.cidadeOrigem = cidadeOrigem;
        this.rotasPossiveis = rotasPossiveis;
    }
   
    private Boolean isCidadePercorrida(Cidade cidade){
        for (Rota rota : rotasPercorridas) {
            if(rota.getCidade1() == cidade)
                return true;
        }
    }
  
    public void exibeRotaPercorrida(){

        System.out.print("\n\nA melhor rota encontrada foi: ");
        for (Rota rota : rotasPercorridas) {
            System.out.print( rota.getCidade1().toString() + " - ");
        }
    }

    public Cidade getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(Cidade cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }
}
