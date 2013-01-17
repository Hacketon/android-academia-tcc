package workoutsystem.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleEvolucao;
import workoutsystem.control.ControleMedida;
import workoutsystem.control.ControlePerfil;
import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;
import workoutsystem.model.Perfil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class GUIEvolucao extends Activity {

	private TextView txtmedidas;
	private TextView data1;
	private TextView data2;
	private TextView data3;
	private ProgressBar barra1;
	private ProgressBar barra2;
	private ProgressBar barra3;

	List<Medida> listaMedidas = new ArrayList<Medida>();
	List<Medicao> listaMedicoes = new ArrayList<Medicao>();

	int indice;
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");



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

		indice = 0 ;
		ControleMedida controleMed = new ControleMedida();
		ControleEvolucao controleEvo = new ControleEvolucao();
		ControlePerfil controlePerfil = new ControlePerfil();
		txtmedidas = (TextView) findViewById(R.id.txt_medidas);
		listaMedidas = controleEvo.buscarMedidas();
		Perfil perfil = controlePerfil.buscarPerfil();
		if(perfil == null ){
			Toast.makeText(this,"Primeiro crie o seu perfil !",
					Toast.LENGTH_SHORT).show();

		}else{
			listaMedicoes = controleMed.buscarMedicao(perfil.getCodigo());
			if(listaMedicoes.size() == 0){

				Toast.makeText(this,"Seu perfil n�o possui Medi��es !",
						Toast.LENGTH_LONG).show();

			}
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
		txtmedidas.setText(medida.getNome());
		List<Medicao> lista = new ArrayList<Medicao>();


		for(Medicao m : listaMedicoes){
			if(m.getCodigoMedida() == medida.getCodigo()){
				lista.add(m);
			}


		}

		if(lista.size()==0){
			data1.setText(("Data1"));
			data2.setText(("Data2"));
			data3.setText(("Data3"));
			barra1.setProgress(0);
			barra2.setProgress(0);
			barra3.setProgress(0);

		}else{
			Medicao maior1 = null;
			Medicao maior2 = null;
			Medicao maior3 = null;

			for(Medicao m2 : lista){

				if(maior1 == null ){
					maior1 = m2;	
				}
				if(maior2 == null){
					maior2 = m2;
				}
				if(maior3 == null){
					maior3 = m2;
				}
				if(m2.getDataMedicao().before(maior1.getDataMedicao())){
					maior1 = m2;
				}else if(m2.getDataMedicao().before(maior2.getDataMedicao())){
					maior2 = m2;
				}else if(m2.getDataMedicao().before(maior2.getDataMedicao())){
					maior3 = m2;
				}

				//criar l�gica de porceitagem !!
				
				data1.setText(sdf.format(maior1.getDataMedicao()));
				data2.setText(sdf.format(maior2.getDataMedicao()));
				data3.setText(sdf.format(maior3.getDataMedicao()));
				barra1.setProgress((int) maior1.getValor());
				barra2.setProgress((int) maior2.getValor());
				barra3.setProgress((int)maior3.getValor());

			}

		}
	}
}


