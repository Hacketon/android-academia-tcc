package workoutsystem.model;

import java.io.Serializable;
import java.util.Date;

public class Rotina implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int codigo;
	private Date dataRealizacao;
	private Treino treino;
	private Ficha ficha;
	private int completa;
	
	public Rotina(){
		completa = 0;
		treino = new Treino();
		ficha = new Ficha();
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Date getDataRealizacao() {
		return dataRealizacao;
	}
	public void setDataRealizacao(Date data) {
		this.dataRealizacao = data;
	}
	
	public Treino getTreino() {
		return treino;
	}
	public void setTreino(Treino treino) {
		this.treino = treino;
	}
	public Ficha getFicha() {
		return ficha;
	}
	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}
	public int getCompleta() {
		return completa;
	}
	public void setCompleta(int completa) {
		this.completa = completa;
	}

}
