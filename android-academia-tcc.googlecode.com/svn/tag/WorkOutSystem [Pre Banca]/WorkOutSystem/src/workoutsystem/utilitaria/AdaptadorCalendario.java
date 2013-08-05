package workoutsystem.utilitaria;

import java.util.ArrayList;
import java.util.Calendar;

import workoutsystem.view.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdaptadorCalendario extends BaseAdapter{

	private Calendar mes;
	private Calendar diaSelecionado;
	private Context mview;
	private ArrayList<String> diasMes ;
	private static final String[] diasSemana={"D","S","T","Q","Q","S","S"};

	public AdaptadorCalendario(Calendar mesAtual,Context contexto){
		mes = mesAtual;
		mview = contexto;
		diasMes = new ArrayList<String>();
		diaSelecionado = Calendar.getInstance();
		atualizarDias();

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return diasMes.size();
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
	public View getView(int position, View v, ViewGroup parent) {
		TextView dia;
		TextView text;
		if (v == null) {  // if it's not recycled, initialize some attributes
			LayoutInflater vi = (LayoutInflater)mview.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.dias_calendario, null);
		}

		dia = (TextView) v.findViewById(R.id.text_dia);
		text = (TextView) v.findViewById(R.id.text_frequencia);
		dia.setText(diasMes.get(position));
		text.setText("a");

		dia.setTextColor(Color.parseColor(verificarDiaMes(dia,position)));

		if (verificarDiasInvalidos(dia, text, position)){
			dia.setTextColor(Color.WHITE);
			dia.setClickable(false);
			dia.setEnabled(false);
			text.setText("");
			text.setClickable(false);
			text.setEnabled(false);

		}



		return v;
	}

	public String verificarDiaMes(TextView dia,int posicao){
		String cor;
		if (mes.get(Calendar.YEAR) == diaSelecionado.get(Calendar.YEAR)	&& 
				mes.get(Calendar.MONTH) == diaSelecionado.get(Calendar.MONTH) && 
				diasMes.get(posicao).equalsIgnoreCase(String.valueOf(diaSelecionado.get(Calendar.DAY_OF_MONTH)))){

			cor = "#81C4EB";


		}else{
			//cor = "#DD6823";
			cor = "#ffffff";
		}

		return cor ;
	}

	public boolean verificarDiasInvalidos(TextView dia,TextView texto, int posicao){

		boolean ver = false;
		char [] verificacao = diasMes.get(posicao).toCharArray();
		if (verificacao.length != 0){
			if (Character.isLetter(verificacao[0])){
				ver = true;
			}

		}
		if (diasMes.get(posicao).equalsIgnoreCase("") || ver ){
			return true;
		}else{
			return false;
		}


	}

	public void atualizarDias(){
		diasMes.clear();
		diasSemana();
		primeiraSemana();
		int ultimodiaMes = mes.getActualMaximum(Calendar.DAY_OF_MONTH);
		int dia = 1;
		while (dia<=ultimodiaMes){
			diasMes.add(String.valueOf(dia));
			dia++;
		}
	}


	public void diasSemana(){

		for(String dia : diasSemana)
			diasMes.add(dia);
	}

	public void primeiraSemana(){
		Calendar dia = mes;
		int mesAtual = mes.get(Calendar.MONTH);
		int anoAtual = mes.get(Calendar.YEAR);
		dia.set(anoAtual, mesAtual, 1);
		int primeiroDia = dia.get(Calendar.DAY_OF_WEEK);

		for (int i=1; i != primeiroDia; i++){
			diasMes.add("");
		}


	}




}
