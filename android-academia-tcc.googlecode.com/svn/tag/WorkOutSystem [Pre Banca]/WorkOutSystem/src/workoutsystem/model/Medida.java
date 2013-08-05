package workoutsystem.model;

import java.util.Date;
import java.util.List;

public class Medida {

	private int codigo;
	private String nome;
	private String lado;
	private String unidade;
	private List<Medicao> medicao;



	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setMedicao(List<Medicao> medicao) {
		this.medicao = medicao;
	}
	public List<Medicao> getMedicao() {
		return medicao;
	}
	public String getLado() {
		return lado;
	}
	public void setLado(String lado) {
		this.lado = lado;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}


}
