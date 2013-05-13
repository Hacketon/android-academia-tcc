package workoutsystem.utilitaria;

public class ItemListaHistorico {
	
	private String valor;
	private String data;
	
	public ItemListaHistorico(String valor, String data) {
	
		this.valor = valor;
		this.data = data;
	
	}

	
	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
