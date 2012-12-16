package workoutsystem.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Usuario {
	private int codigo;
	@NotNull(message = "Nome não pode ser vazio")
	private String nome;
	@NotNull(message = "Senha não pode ser vazia")
	private String senha;
	
	public String getNome() {
		
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
}
