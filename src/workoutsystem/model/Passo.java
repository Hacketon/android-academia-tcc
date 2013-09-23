package workoutsystem.model;

import java.io.Serializable;

public class Passo implements Serializable{
	
	private int sequencia;
	private String explicacao;
	// possivel altera��o para inteiro , 
	//ou deixar como string o endere�o da imagem.
	private int imagem; 
	
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
	public int getImagem() {
		return imagem;
	}
	public void setImagem(int imagem) {
		this.imagem = imagem;
	}

}
