package aco;

public class Cidade {
    private int numeroCidade;
    private Double coordenadaX;
    private Double coordenadaY;
	private Formiga formiga;
	
    public int getNumeroCidade() {
		return numeroCidade;
	}
	public void setNumeroCidade(int numeroCidade) {
		this.numeroCidade = numeroCidade;
	}
	public Double getCoordenadaX() {
		return coordenadaX;
	}
	public void setCoordenadaX(Double coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	public Double getCoordenadaY() {
		return coordenadaY;
	}
	public void setCoordenadaY(Double coordenadaY) {
		this.coordenadaY = coordenadaY;
    }
    
	public Cidade(int numeroCidade, Double coordenadaX, Double coordenadaY) {
		this.numeroCidade = numeroCidade;
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
	}
	
	public Double distanciaEntreCidades(Cidade cidadeFinal){

		Double distanciaEmX, distanciaEmY, somaAoQuadrado, distancia;

		distanciaEmX = Math.abs(cidadeFinal.getCoordenadaX() - this.coordenadaX);
		distanciaEmY = Math.abs(cidadeFinal.getCoordenadaY() - this.coordenadaY);

		somaAoQuadrado = Math.pow(distanciaEmX, 2) + Math.pow(distanciaEmY, 2);
		distancia = Math.sqrt(somaAoQuadrado);

		return distancia;

	}

	public Boolean isDiferente(Cidade cidade){
		return !(this == cidade);
	}

    @Override
	public String toString() {
		return "Cidade [coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", numeroCidade=" + numeroCidade
				+ "]";
	}

	public Formiga getFormiga() {
		return formiga;
	}

	//adicionando a formiga e definindo sua cidade de origem
	public void adicionaFormiga() {
		this.formiga = new Formiga(this);
	}

    
}
