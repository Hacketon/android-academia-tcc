package workoutsystem.model;

public class Especificacao {
	private int carga;
	private String unidade;
	private int ordem;
	private int quantidade;
	// pode ser repetições ou tempo
	public int getCarga() {
		return carga;
	}
	public String getUnidade() {
		return unidade;
	}
	public int getOrdem() {
		return ordem;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setCarga(int carga) {
		this.carga = carga;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

}
