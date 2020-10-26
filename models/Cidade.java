package models;

public class Cidade {
    private int numeroCidade;
    private Double coordenadaX;
    private Double coordenadaY;
    
    
    
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
    
    @Override
	public String toString() {
		return "Cidade [coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", numeroCidade=" + numeroCidade
				+ "]";
	}

    
}
