package workoutsystem.model;

import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.guard.Guarded;

@Guarded()
public class Usuario {
	private int codigo;
	// annotations
	
	@NotEmpty()
	@NotNull(message = "Nome não pode ser vazio")
	private String nome;
	
	@NotNull(message = "Senha não pode ser vazia")
	@NotEmpty()
	private String senha;
	
	private int logado;
	
	public Usuario (){
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
