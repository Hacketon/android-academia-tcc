package workoutsystem.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.sf.oval.constraint.Future;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;

public class Treino implements Serializable{
	
	private long codigoTreino;
	@NotEmpty(message = "O treino deve ter um nome")
	@Length(max = 10 ,message = "Escolha um nome com at� 10 caracteres")
	private String nomeTreino;
	private List<Especificacao> listaEspecificacao;
	private int ordem;
	private long codigoFicha;
	
	public long getCodigoTreino() {
		return codigoTreino;
	}
	public String getNome() {
		return nomeTreino;
	}
	
//	public Date getDataRealizacao() {
//		return dataRealizacao;
//	}
	public void setCodigoTreino(long codigoTreino) {
		this.codigoTreino = codigoTreino;
	}
	public void setNome(String nomeTreino) {
		this.nomeTreino = nomeTreino;
	}


//	public void setDataRealizacao(Date dataRealizacao) {
//		this.dataRealizacao = dataRealizacao;
//	}
	public void setEspecificacao(List<Especificacao> especificacao) {
		this.listaEspecificacao = especificacao;
	}
	public List<Especificacao> getEspecificacao() {
		return listaEspecificacao;
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
