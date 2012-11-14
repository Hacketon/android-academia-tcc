package workoutsystem.model;

import java.util.Date;

public class Medicao {
	

	private double valor;
	private int lado;
	private String unidade;
	private	Date dataMedicao;
	
	public double getValor() {
		return valor;
	}
	public int getLado() {
		return lado;
	}
	public String getUnidade() {
		return unidade;
	}
	public Date getDataMedicao() {
		return dataMedicao;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	public void setLado(int lado) {
		this.lado = lado;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public void setDataMedicao(Date dataMedicao) {
		this.dataMedicao = dataMedicao;
	}
	
	
	

}
