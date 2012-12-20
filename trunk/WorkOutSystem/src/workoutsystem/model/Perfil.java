package workoutsystem.model;

import java.util.List;

import javax.validation.constraints.NotNull;

public class Perfil {

	private int codigo;
	@NotNull
	private String nome;
	private boolean sexo;
	private int totalFrequencia;
	private List<DiaSemana> frequencia;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public boolean getSexo() {
		return sexo;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setSexo(boolean sexo) {
		this.sexo = sexo;
	}
	public void setFrequencia(List<DiaSemana> frequencia) {
		this.frequencia = frequencia;
	}
	public List<DiaSemana> getFrequencia() {
		return frequencia;
	}
	public int getTotalFrequencia() {
		return totalFrequencia;
	}
	public void setTotalFrequencia(int totalFrequencia) {
		this.totalFrequencia = totalFrequencia;
	}
	
}
