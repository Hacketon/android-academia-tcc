package workoutsystem.model;

import java.util.Date;
import java.util.List;

public class Treino {
	
	private long codigoTreino;
	private String nomeTreino;
	private String grupoMusculares;
	private Date dataRealizacao;
	private List <Exercicio> exercicios;
	
	public long getCodigoTreino() {
		return codigoTreino;
	}
	public String getNomeTreino() {
		return nomeTreino;
	}
	public String getGrupoMusculares() {
		return grupoMusculares;
	}
	public Date getDataRealizacao() {
		return dataRealizacao;
	}
	public void setCodigoTreino(long codigoTreino) {
		this.codigoTreino = codigoTreino;
	}
	public void setNomeTreino(String nomeTreino) {
		this.nomeTreino = nomeTreino;
	}
	public void setGrupoMusculares(String grupoMusculares) {
		this.grupoMusculares = grupoMusculares;
	}
	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}
	public void setExercicios(List <Exercicio> exercicios) {
		this.exercicios = exercicios;
	}
	public List <Exercicio> getExercicios() {
		return exercicios;
	}
	

}
