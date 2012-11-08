package workoutsystem.model;

import java.util.Date;

public class Medida {

	
	private String nome;
	private double valor;
	private int lado;
	private String unidade;
	private	Date dataMedicao;
	
	public String getNome() {
		return nome;
	}
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
	public void setNome(String nome) {
		this.nome = nome;
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
