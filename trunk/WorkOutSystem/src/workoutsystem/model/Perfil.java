package workoutsystem.model;

import java.util.Date;

public class Perfil {

	private String nome;
	private boolean sexo;
	private int codigousuario;
	
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
	
}
