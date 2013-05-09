package workoutsystem.model;

import java.io.Serializable;

public class Especificacao implements Serializable {
	
	private int codigoTreino;
	private int  codigoExercicio;
	private double carga;
	private String unidade;
	private int ordem;
	private int quantidade;
	// unidade pode ser repetições ou tempo
	
	public double getCarga() {
		return carga;
	}
	public String getUnidade() {
		return unidade;
	}
	public int getOrdem() {
		return ordem;
	}
	
	public void setCarga(double carga) {
		this.carga = carga;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public void setCodigoExercicio(int codigoExercicio) {
		this.codigoExercicio = codigoExercicio;
	}
	public int getCodigoExercicio() {
		return codigoExercicio;
	}
	public void setCodigoTreino(int codigoTreino) {
		this.codigoTreino = codigoTreino;
	}
	public int getCodigoTreino() {
		return codigoTreino;
	}
	public int getQuantidade() {
		return quantidade;
	}

}
