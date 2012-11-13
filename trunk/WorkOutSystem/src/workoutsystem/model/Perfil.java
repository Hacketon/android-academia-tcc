package workoutsystem.model;

import java.util.Date;

public class Perfil {

	private String nome;
	
	private boolean sexo;
	private Date dataNascimento;
	
	public String getNome() {
		return nome;
	}
	public boolean getSexo() {
		return sexo;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setSexo(boolean b) {
		this.sexo = b;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
}
