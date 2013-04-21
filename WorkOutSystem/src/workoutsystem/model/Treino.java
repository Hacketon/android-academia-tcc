package workoutsystem.model;

import java.util.Date;
import java.util.List;

import net.sf.oval.constraint.Future;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;

public class Treino {
	
	private long codigoTreino;
	@NotEmpty(message = "O treino deve ter um nome")
	@Length(message = "Escolha um nome com até 10 caracteres")
	private String nomeTreino;
	private String grupoMusculares;
	private List <Exercicio> exercicios;
	private int ordem;
	private int codigoFicha;
//	@Future(message = "Não é permitida datas antigas")
//	private Date dataRealizacao;
	
	public long getCodigoTreino() {
		return codigoTreino;
	}
	public String getNomeTreino() {
		return nomeTreino;
	}
	public String getGrupoMusculares() {
		return grupoMusculares;
	}
//	public Date getDataRealizacao() {
//		return dataRealizacao;
//	}
	public void setCodigoTreino(long codigoTreino) {
		this.codigoTreino = codigoTreino;
	}
	public void setNomeTreino(String nomeTreino) {
		this.nomeTreino = nomeTreino;
	}
	public void setGrupoMusculares(String grupoMusculares) {
		this.grupoMusculares = grupoMusculares;
	}
//	public void setDataRealizacao(Date dataRealizacao) {
//		this.dataRealizacao = dataRealizacao;
//	}
	public void setExercicios(List <Exercicio> exercicios) {
		this.exercicios = exercicios;
	}
	public List <Exercicio> getExercicios() {
		return exercicios;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setCodigoFicha(int codigoFicha) {
		this.codigoFicha = codigoFicha;
	}
	public int getCodigoFicha() {
		return codigoFicha;
	}
	

}
