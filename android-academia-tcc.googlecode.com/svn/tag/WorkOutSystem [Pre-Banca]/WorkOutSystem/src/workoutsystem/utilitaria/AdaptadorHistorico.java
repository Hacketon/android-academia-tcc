package workoutsystem.utilitaria;

import java.util.List;

import workoutsystem.view.R;
import workoutsystem.view.R.drawable;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorHistorico  extends BaseAdapter {
	//Lista de pessoas!
	private List<ItemListaHistorico> item;
	private LayoutInflater mInflater;
	private ViewHolder holder;


	static class ViewHolder{
		private TextView valor;
		private TextView data;
		private ImageView img;
	}
	
	public AdaptadorHistorico(Context context, List<ItemListaHistorico> item) {
		mInflater = LayoutInflater.from(context);
		this.item = item;
	}

	public int getCount() {
		return item.size();
	}

	public Object getItem(int index) {
		return item.get(index);
	}

	public long getItemId(int index) {
		//return pessoas(index).getImgRes();
		return index;
	}

	public View getView(int position, View view, ViewGroup parent)
	{
		//Pega o item de acordo com a posção.
		ItemListaHistorico i = item.get(position);
		ItemListaHistorico i2;
		
		if( position + 1 == getCount() ){
			i2 = item.get(position);
		}else{
			i2 = item.get(position + 1);
		}


		//infla o layout para podermos preencher os dados
		view = mInflater.inflate(R.layout.item_lista_historico, null);

		//atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
		//ao item e definimos as informações.
		((TextView) view.findViewById(R.id.valor_medida_historico)).setText("Valor:  " + i.getValor() + " " + i.getUnidade());
		((TextView) view.findViewById(R.id.data_medida_historico)).setText("Data:  " + i.getData());

		float aux = Float.parseFloat(i2.getValor());
		float valor =  Float.parseFloat(i.getValor());
		if(aux > valor){
			((ImageView) view.findViewById(R.id.img_historico_medida)).setImageResource(drawable.ic_seta_baixo);

		}else if(aux < valor){
			((ImageView) view.findViewById(R.id.img_historico_medida)).setImageResource(drawable.ic_seta_alto);


		}else if(aux == valor){
			((ImageView) view.findViewById(R.id.img_historico_medida)).setImageResource(drawable.ic_trace);

		}


		return view;
	}
}

