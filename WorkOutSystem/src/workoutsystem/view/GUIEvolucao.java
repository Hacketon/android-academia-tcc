package workoutsystem.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
	private List<Medida> listaMedidas;
	private List<Medicao> listaMedicoes;
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
			Toast.makeText(this,"Primeiro crie o seu perfil !",
					Toast.LENGTH_SHORT).show();

		}else{
			
			if(controleMed.verificarMedicao(perfil.getCodigo())){
				Toast.makeText(this,"Seu perfil não possui Medições !",
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
		ControlePerfil controleperfil = new ControlePerfil();
		ControleMedida controlemedida = new ControleMedida();
		Perfil perfil = controleperfil.buscarPerfil();
		String nome = medida.getNome();
		if (medida.getLado()!= null){
			nome+= " " + medida.getLado();
		}
		
		listaMedicoes = controlemedida.ultimasMedicoes(perfil.getCodigo(), medida.getCodigo()); 

//
//		if(lista.size()==0){
//			data1.setText(("Data1"));
//			data2.setText(("Data2"));
//			data3.setText(("Data3"));
//			barra1.setProgress(0);
//			barra2.setProgress(0);
//			barra3.setProgress(0);
//
//		}else{
//			
//		}
//			Medicao maior1 = null;
//			Medicao maior2 = null;
//			Medicao maior3 = null;
//
//		String unidade;
//		unidade = medida.getUnidade();
//			for(Medicao m2 : lista){
//
//				if(maior1 == null ){
//					maior1 = m2;	
//				}
//				if(maior2 == null){
//					maior2 = m2;
//				}
//				if(maior3 == null){
//					maior3 = m2;
//				}
//				if(m2.getDataMedicao().after(maior1.getDataMedicao())){
//					maior1 = m2;
//				}else if(m2.getDataMedicao().after(maior2.getDataMedicao())){
//					maior2 = m2;
//				}else if(m2.getDataMedicao().after(maior2.getDataMedicao())){
//					maior3 = m2;
//				}
//
//			}
//				// lógica de exibição (em teste) 
////				double media;
//				int valor1 = 0, valor2 = 0, valor3 = 0;
////				media = (maior1.getValor() + maior2.getValor() + maior3.getValor()) / 3;
//				
//				//caso1
//				if(maior1.getValor()>maior2.getValor()&& maior1.getValor()>=maior3.getValor()){
//					if(maior1.getValor()== maior3.getValor()){
//						valor3=80;
//						valor1 = 80;
//						valor2= 50;
//					}else{
//						valor1 = 80;
//					
//					if(maior2.getValor() > maior3.getValor()){
//						valor2 = 50;
//						valor3 = 20;
//					}else if (maior2.getValor()< maior3.getValor()){
//						valor3 = 50;
//						valor2 = 20;
//					}
//					else if (maior2.getValor()== maior3.getValor()){
//						valor3 = 50;
//						valor2 = 50;
//					}
//					}
//					//caso2
//				}else if(maior2.getValor()>maior1.getValor()&& maior2.getValor()>=maior3.getValor()){
//					if(maior2.getValor()== maior3.getValor()){
//						valor3 = 80;
//						valor2 = 80;
//						valor1 = 50;
//					}else{
//						valor2 = 80;
//					
//					if(maior1.getValor() > maior3.getValor()){
//						valor1 = 50;
//						valor3 = 20;
//					}else if (maior1.getValor()< maior3.getValor()){
//						valor3 = 50;
//						valor1 = 20;
//					}else if (maior1.getValor()== maior3.getValor()){
//						valor3 = 50;
//						valor1 = 50;
//					}
//					}
//					//caso3
//				}else if(maior3.getValor()>maior1.getValor()&& maior3.getValor()>=maior2.getValor()){
//					if(maior2.getValor()== maior3.getValor()){
//						valor3 = 80;
//						valor2 = 80;
//						valor1 = 50;
//					}else{
//						valor3 = 80;
//					
//					
//					if(maior2.getValor() > maior1.getValor()){
//						valor2 = 50;
//						valor1 = 20;
//					}else if (maior2.getValor()< maior1.getValor()){
//						valor1 = 50;
//						valor2 = 20;
//					}else if(maior2.getValor()== maior1.getValor()){
//						valor1 = 50;
//						valor2 = 50;
//					}
//					}
//				}
//				//caso4
//				else if(maior3.getValor()==maior1.getValor()&& maior3.getValor()==maior2.getValor()){
//					valor3 = 50;
//					valor2 = 50;
//					valor1 = 50;
//				}
//				
//				
//				
////				if(media > 0 && media <= 5 ){
////					
////				}else if(media > 0 && media <= 5 ){
////					
////				}else if(media > 5 && media <= 10){
////					
////				}else if(media > 10 && media <= 30){
////					
////				}else if(media > 30 && media <= 60){
////					
////				}else if(media > 60 && media <= 100){
////					
////				}else if(media > 100 && media <= 200){
////					
////				}else if(media > 200){
////					
////				}
//				
//				
//				data1.setText(sdf.format(maior1.getDataMedicao())+ " \n" + maior1.getValor()+ unidade);
//				data2.setText(sdf.format(maior2.getDataMedicao())+ " \n" + maior2.getValor()+ unidade);
//				data3.setText(sdf.format(maior3.getDataMedicao())+ " \n" + maior3.getValor()+ unidade);
//				barra1.setProgress(valor1);
//				barra2.setProgress(valor2);
//				barra3.setProgress(valor3);
//
//			
//
//		}
	}
}


