package it.polito.tdp.imdb.model;

public class Adiacenza {
	Director d1;
	Director d2;
	double peso;
	public Adiacenza(Director d1, Director d2, double peso) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.peso = peso;
	}
	public Director getD1() {
		return d1;
	}
	public Director getD2() {
		return d2;
	}
	public double getPeso() {
		return peso;
	}
	@Override
	public String toString() {
		return "Adiacenza [d1=" + d1 + ", d2=" + d2 + ", peso=" + peso + "]";
	}

}
