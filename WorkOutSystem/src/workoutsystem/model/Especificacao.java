package workoutsystem.model;

import java.io.Serializable;

public class Especificacao implements Serializable {
	
	private long codigoTreino;
	private double carga;
	private String unidade;
	private int ordem;
	private int quantidade;
	private Exercicio exercicio;
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
	
	
	public void setCodigoTreino(long codigoTreino) {
		this.codigoTreino = codigoTreino;
	}
	public long getCodigoTreino() {
		return codigoTreino;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setExercicio(Exercicio exercicio) {
		this.exercicio = exercicio;
	}
	public Exercicio getExercicio() {
		return exercicio;
	}

}
