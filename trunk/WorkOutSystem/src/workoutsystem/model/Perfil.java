package workoutsystem.model;

import java.util.Date;

public class Perfil {

	private String nome;
	private String sexo;
	private Date dataNascimento;
	
	public String getNome() {
		return nome;
	}
	public String getSexo() {
		return sexo;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
}