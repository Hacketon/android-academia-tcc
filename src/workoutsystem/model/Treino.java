package workoutsystem.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.sf.oval.constraint.Future;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;

public class Treino implements Serializable{
	
	private int codigoTreino;
	@NotEmpty(message = "O treino deve ter um nome")
	@Length(max = 10 ,message = "Escolha um nome com até 10 caracteres")
	private String nomeTreino;
	private List<Serie> listaSerie;
	private int ordem;
	private long codigoFicha;
	
	public long getCodigo() {
		return codigoTreino;
	}
	public String getNome() {
		return nomeTreino;
	}
	
	public void setCodigo(int codigoTreino) {
		this.codigoTreino = codigoTreino;
	}
	public void setNome(String nomeTreino) {
		this.nomeTreino = nomeTreino;
	}

	public void setSerie(List<Serie> especificacao) {
		this.listaSerie = especificacao;
	}
	public List<Serie> getSerie() {
		return listaSerie;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setCodigoFicha(long codigoFicha) {
		this.codigoFicha = codigoFicha;
	}
	public long getCodigoFicha() {
		return codigoFicha;
	}
	
	

}
