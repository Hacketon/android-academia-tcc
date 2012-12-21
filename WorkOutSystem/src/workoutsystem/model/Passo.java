package workoutsystem.model;

import java.io.Serializable;

public class Passo implements Serializable{
	
	private int sequencia;
	private String explicacao;
	
	public int getSequencia() {
		return sequencia;
	}
	public String getExplicacao() {
		return explicacao;
	}
	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}
	public void setExplicacao(String explicacao) {
		this.explicacao = explicacao;
	}

}
