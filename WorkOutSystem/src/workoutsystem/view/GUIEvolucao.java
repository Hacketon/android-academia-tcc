package workoutsystem.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import workoutsystem.control.ControleMedida;
import workoutsystem.control.ControlePerfil;
import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;
import workoutsystem.model.Perfil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class GUIEvolucao extends Activity  {

	private TextView txtmedidas;
	private TextView data1;
	private TextView data2;
	private TextView data3;
	private ProgressBar barra1;
	private ProgressBar barra2;
	private ProgressBar barra3;
	private List<Medida> listaMedidas;
	private List<Medicao> listaMedicoes;
	private ImageButton btnProximo;
	private ImageButton btnAnterior;
	private int indice;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evolucao);
		data1 = (TextView) findViewById(R.id.txtData1);
		data2 = (TextView) findViewById(R.id.txtData2);
		data3 = (TextView) findViewById(R.id.txtData3);
		barra1 = (ProgressBar) findViewById(R.id.progressBarMedida1);
		barra2 = (ProgressBar) findViewById(R.id.progressBarMedida2);
		barra3 = (ProgressBar) findViewById(R.id.progressBarMedida3);
		btnAnterior = (ImageButton) findViewById(R.id.btn_proximamedida);
		btnProximo = (ImageButton) findViewById(R.id.btn_anteriormedida);
		
		txtmedidas = (TextView) findViewById(R.id.txt_medidas);
		indice = 0 ;
		listaMedicoes = new ArrayList<Medicao>();
		listaMedicoes = new ArrayList<Medicao>();
		inicializarEvolucao();
	}

	private void inicializarEvolucao() {
		ControleMedida controleMed = new ControleMedida();
		ControlePerfil controlePerfil = new ControlePerfil();
	
		listaMedidas = controleMed.buscarMedidas();
		Perfil perfil = controlePerfil.buscarPerfil();
		if(perfil == null ){
			Toast.makeText(this,"Primeiro crie o seu perfil e adicione medições!",
					Toast.LENGTH_SHORT).show();
			finish();
		}else if(!controleMed.verificarMedicao(perfil.getCodigo())){
				Toast.makeText(this,"Seu perfil não possui medições!",
						Toast.LENGTH_LONG).show();
				finish();
					
		}else{
			exibirAnterior();
		}
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
		Medida medida = null;
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
		Medida medida = null;
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

	public void carregaMedida(Medida medida){
		ControlePerfil controleperfil = new ControlePerfil();
		ControleMedida controlemedida = new ControleMedida();
		Perfil perfil = controleperfil.buscarPerfil();
		String nome = medida.getNome();
		String ndata= "Data";
		String ndata1 = "Data";
		String ndata2 = "Data";
		int contador = 0 ;

		if (medida.getLado()!= null){
			nome+= " " + medida.getLado();
		}

		txtmedidas.setText(nome);
		medida.setMedicao(controlemedida.ultimasMedicoes
				(perfil.getCodigo(), medida.getCodigo())); 

		data1.setText(ndata);
		data2.setText(ndata);
		data3.setText(ndata);
		barra1.setProgress(0);
		barra2.setProgress(0);
		barra3.setProgress(0);

		calcularProgresso(medida);

	}

	private void calcularProgresso(Medida medida) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		int maior = 70;
		int medio = 50;
		int menor = 30;
		int contador = 0;
		int naux = 2;

		Collections.sort(medida.getMedicao(),new ControleMedida());


		while (naux <= medida.getMedicao().size()){
			if (naux == 2){
				if (medida.getMedicao().get(contador).getValor()== medida.getMedicao().get(contador+1).getValor()){
					medio = maior;
					} 
				}else if (naux== 3){
					if (medida.getMedicao().get(contador+1).getValor() == medida.getMedicao().get(contador+2).getValor()){
						medio = menor;
					}
					if (medida.getMedicao().get(contador).getValor() == medida.getMedicao().get(contador +1).getValor() &&
							medida.getMedicao().get(contador+1).getValor()== medida.getMedicao().get(contador+2).getValor()){
						menor = medio;
						maior = medio;
					}

			}
			naux++;

		}
		Collections.reverse(medida.getMedicao());


		while (contador != medida.getMedicao().size()){
			Medicao m = medida.getMedicao().get(contador);
			String aux  =  sdf.format(m.getDataMedicao())+
			" \n" + m.getValor()
			+" "+ medida.getUnidade();
			if (contador == 0){
				data1.setText(aux);
				barra1.setProgress(maior);
			}else if (contador == 1){
				data2.setText(aux);
				barra2.setProgress(medio);
			}else if (contador == 2){
				data3.setText(aux);
				barra3.setProgress(menor);
			}
			contador ++;
		}
	}

}


