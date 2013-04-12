package workoutsystem.model;

import java.util.List;

public class Perfil {

	private int codigo;
	private String nome;
	private boolean sexo;
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
	
	
}