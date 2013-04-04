package workoutsystem.model;

import javax.validation.constraints.NotNull;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;






public class Usuario {
	private int codigo;
	
	@NotNull
	@NotEmpty
	@Length(max=20)
	private String nome;
	

	@NotNull
	@NotEmpty
	@Length(max=30)
	private String senha;
	
	private int logado;
	
	public Usuario (){
		nome = "";
		senha = "";
		logado = 0;
	}
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
	public void setLogado(int logado) {
		this.logado = logado;
	}
	public int getLogado() {
		return logado;
	}
	
	
}
