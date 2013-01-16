package workoutsystem.view;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleEvolucao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class GUIEvolucao extends Activity {

	private TextView txtmedidas;
	List<String> listaMedidas = new ArrayList<String>();
	int indice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evolucao);
		indice = 0 ;
		
		txtmedidas = (TextView) findViewById(R.id.txt_medidas);
		ControleEvolucao controleEvo = new ControleEvolucao();
		listaMedidas = controleEvo.buscarMedidas();
		
		exibirAnterior();
	}
	
	public void onClick(View e) {
		switch (e.getId()) {
		
		case (R.id.btn_proximamedida):
			exibirProximo();
		break;
		case (R.id.btn_anteriormedida):
			exibirAnterior();			
		break;

		}

	}
	
	public void exibirProximo(){
		String medida = null;
		if(!listaMedidas.isEmpty()){
			if(indice < listaMedidas.size()-1 ){
				medida = listaMedidas.get(indice + 1);
				indice ++;
			}if(indice == listaMedidas.size() -1){
				medida = listaMedidas.get(listaMedidas.size() - 1);
			}
			
		}
		if(medida != null){
			carregaMedida(medida);
		}
	}
	
	public void exibirAnterior(){
		String medida = null;

		if(!listaMedidas.isEmpty()){
			if(indice > 0){
				medida = listaMedidas.get(indice - 1);
				indice--;
			}else if (indice == 0){
				medida = listaMedidas.get(0);
			}
		}
		if( medida != null){
			carregaMedida(medida);
		}
	}

	public void carregaMedida(String medida){
		txtmedidas.setText(medida);
	}
}
