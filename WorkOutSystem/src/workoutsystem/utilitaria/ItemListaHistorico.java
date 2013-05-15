package workoutsystem.utilitaria;

import android.widget.ImageView;

public class ItemListaHistorico {
	
	private String valor;
	private String data;
	private String unidade;
	private int icone;
	
	public ItemListaHistorico(String valor, String data, String unidade) {
	
		this.valor = valor;
		this.data = data;
		this.unidade = unidade;
		
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


	public int getIcone() {
		return icone;
	}


	public void setIcone(int icone) {
		this.icone = icone;
	}


	public String getUnidade() {
		return unidade;
	}


	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}



}
