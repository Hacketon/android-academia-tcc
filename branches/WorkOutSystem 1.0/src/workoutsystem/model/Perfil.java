package workoutsystem.model;

import java.util.List;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;

public class Perfil {

	private int codigo;
	@NotEmpty(message = "O campo nome é obrigatorio")
	@Length(max=20,message = "Nome deve ter no maximo 20 caracteres")
	private String nome;
	private boolean sexo;
	private List<Frequencia> frequencia;
	
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
	public void setFrequencia(List<Frequencia> frequencia) {
		this.frequencia = frequencia;
	}
	public List<Frequencia> getFrequencia() {
		return frequencia;
	}
	
	
}
