package workoutsystem.model;

import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Size;



public class Usuario {
	private int codigo;
	
	@NotEmpty(message = "Campo não pode ser vazio")
	@Size(max= 20,message = "Maximo para o campo são 20 caracteres")
	private String nome;

	@NotEmpty(message = "Campo não pode ser vazio")
	@Size(max = 12,message = "Maximo para o campo são 20 caracteres")
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
