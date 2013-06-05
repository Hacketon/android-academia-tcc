package workoutsystem.model;

import java.io.Serializable;

import net.sf.oval.constraint.Min;

public class Especificacao implements Serializable {
	
	private long codigoTreino;
	private double carga;
	private String unidade;
	private int ordem;
	@Min(value=1,message="Valor minimos para quantidade é 1")
	private int quantidade;
	private Exercicio exercicio;
	
	
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
