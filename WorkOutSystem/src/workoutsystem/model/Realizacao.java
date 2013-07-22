package workoutsystem.model;

import java.io.Serializable;
import java.util.Date;

public class Realizacao implements Serializable {
	
	private int codigo;
	private Date data;
	private Serie serie;
	private Treino treino;
	private Ficha ficha;
	
	public Realizacao(){
	
		
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Serie getSerie() {
		return serie;
	}
	public void setSerie(Serie serie) {
		this.serie = serie;
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

}
