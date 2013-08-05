package workoutsystem.utilitaria;

import java.util.List;

import workoutsystem.view.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorIcones extends BaseAdapter {
	
	private Context context;
	private List<Integer> listaImagem;
	private List<Integer> listaTexto;
	
	public AdaptadorIcones(Context contexto, List<Integer> im, List<Integer> txt){
		context = contexto;
		listaImagem = im;
		listaTexto = txt;
	}
	@Override
	public int getCount() {
		return listaImagem.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int posicao, View view, ViewGroup parent) {
		if (view == null){
			LayoutInflater infl = (LayoutInflater) 
								context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infl.inflate(R.layout.icon_design, null);
		}

		ImageView imagem = (ImageView) view.findViewById(R.id.img_icone);
		TextView texto = (TextView) view.findViewById(R.id.texto_icone);
		imagem.setImageResource(listaImagem.get(posicao));
		texto.setText(listaTexto.get(posicao));
		texto.setTextColor(Color.rgb(255, 255, 255));
		return  view;
	}

}
