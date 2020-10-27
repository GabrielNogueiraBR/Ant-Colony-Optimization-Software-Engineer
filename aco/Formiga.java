package aco;



import java.util.ArrayList;
import java.util.List;

public class Formiga {
    private Cidade cidadeOrigem;
    private List<Rota> listaRota;
    private List<Rota> rotasPercorridas;
    

    public Formiga(Cidade cidadeOrigem, List<Rota> listaRota) {
        this.cidadeOrigem = cidadeOrigem;
        this.listaRota = listaRota;
    }
   
    public Formiga(){
        this.rotasPercorridas = new ArrayList<Rota>();
    }
    
  
    public void exibeRotaPercorrida(){

        System.out.print("\n\nTamanho da lista: "+ rotasPercorridas.size() + "\n\nA melhor rota encontrada foi: ");
        for (Rota rota : rotasPercorridas) {
            System.out.print( rota.getCidade1().getNumeroCidade() + " - ");
        }
    }

    public Cidade getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(Cidade cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

	public List<Rota> percorreRota(List<Rota> rotasCidade) {

        exibeRotaPercorrida();
        Rota rotaEscolhida = rotasCidade.get(sorteiaRotaPorCidade(cidadeOrigem, rotasCidade));
        rotasPercorridas.add(rotaEscolhida);
        listaRota = deletaRotasDaCidadeDeOrigem(listaRota, cidadeOrigem);
        atualizaCidadeOrigem(rotaEscolhida);

        return listaRota;
    
    }

    public void atualizaCidadeOrigem(Rota rotaEscolhida){
        
        Cidade proximaCidade;
        
        if(rotaEscolhida.getCidade1() == cidadeOrigem)
            proximaCidade = rotaEscolhida.getCidade2();
        else
            proximaCidade = rotaEscolhida.getCidade1();

        this.setCidadeOrigem(proximaCidade);
    }

    //Essa funcao deleta as rotas que possuem a cidade de onde partiu a formiga. Isso porque, na proxima execucao do codigo, essa cidade nao estara mais disponivel como uma possibilidade.
    private List<Rota> deletaRotasDaCidadeDeOrigem(List<Rota> listaRota2, Cidade cidadeOrigem2) {

        List<Rota> rotas = new ArrayList<Rota>();

        for (Rota rota : listaRota2) {
            
            if ( (rota.getCidade1() == cidadeOrigem2) || (rota.getCidade2() == cidadeOrigem2)){
                
            }
            else
                rotas.add(rota);

        }
        return rotas;
    }

    // retorna o indice da rota sorteada
    private int sorteiaRotaPorCidade(Cidade cidade, List<Rota> rotasDaCidade){
        Double porcentagemTotal = 0.0;
        Double sorteio = Math.random(); //valor entre 0.0 e 1.0
        int retorno = 0, indice = 0;

        //faz uma agregacao na porcentagem para podermos sortear um numero
        for (Rota rota : rotasDaCidade) {
            porcentagemTotal += rota.getProbabilidadeEscolha();
            rota.setProbabilidadeEscolha(porcentagemTotal);

            //definimos aquele valor 0.0001 para ser uma aproximacao
            if(rota.getProbabilidadeEscolha() <= (sorteio + 0.0001)){
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

	public Cidade novaOrigem() {
		return null;
	}

    public List<Rota> getListaRota() {
        return listaRota;
    }

    public void setListaRota(List<Rota> listaRota) {
        this.listaRota = listaRota;
    }

}
