package workoutsystem.model;

import java.util.List;

import javax.validation.constraints.NotNull;

public class Perfil {

	private int codigo;
	@NotNull
	private String nome;
	private boolean sexo;
	private int codigousuario;
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
	public int getCodigousuario() {
		return codigousuario;
	}
	public void setCodigousuario(int codigousuario) {
		this.codigousuario = codigousuario;
	}
	public void setFrequencia(List<DiaSemana> frequencia) {
		this.frequencia = frequencia;
	}
	public List<DiaSemana> getFrequencia() {
		return frequencia;
	}
	
}
