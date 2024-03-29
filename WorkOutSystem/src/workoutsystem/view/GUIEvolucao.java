package workoutsystem.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import workoutsystem.control.ControleMedida;
import workoutsystem.control.ControlePerfil;
import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;
import workoutsystem.model.Perfil;
import workoutsystem.utilitaria.AdaptadorHistorico;
import workoutsystem.utilitaria.ItemListaHistorico;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

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
	private int indice;
	private ListView listahistorico;

	private TabHost hostEvolucao;
	private TabSpec tabEvolucao;
	private TabSpec tabHistorico;

	private ArrayAdapter<AdaptadorHistorico> adapter;
	private AdaptadorHistorico adapterListView;
	private List<ItemListaHistorico> itens;
	private SimpleDateFormat sdf;

	ControlePerfil controleperfil = new ControlePerfil();
	ControleMedida controlemedida = new ControleMedida();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		listaMedicoes = new ArrayList<Medicao>();
		listaMedicoes = new ArrayList<Medicao>();
		listahistorico = (ListView) findViewById(R.id.listahistorico);
		inicializarEvolucao();
		criarTabs();
	}

	private void inicializarEvolucao() {
		ControleMedida controleMed = new ControleMedida();
		ControlePerfil controlePerfil = new ControlePerfil();
		try{
			listaMedidas = controleMed.buscarMedidas();
			Perfil perfil = controlePerfil.buscarPerfil();
			if(!controleMed.verificarMedicao(perfil.getCodigo())){
				Toast.makeText(this,"Seu perfil n�o possui medi��es!",
						Toast.LENGTH_LONG).show();
				finish();

			}else{
				exibirAnterior();
			}

		}catch (Exception e) {
			Toast.makeText(this,"Primeiro crie o seu " +
					" perfil e adicione medi��es!",
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	public void criarTabs(){
		hostEvolucao = (TabHost) findViewById(R.id.hostevolucao);
		hostEvolucao.setup();
		tabEvolucao = hostEvolucao.newTabSpec("tabEvolucao");
		tabEvolucao.setContent(R.id.tabEvolucao);
		tabEvolucao.setIndicator("Evolu��o");
		hostEvolucao.addTab(tabEvolucao);
		tabHistorico = hostEvolucao.newTabSpec("tabHistorico");
		tabHistorico.setContent(R.id.tabHistorico);
		tabHistorico.setIndicator("Historico");
		hostEvolucao.addTab(tabHistorico);
	}

	public void onClick(View e) {
		switch (e.getId()) {
		case (R.id.btn_proximamedida):
			try {
				exibirProximo();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			break;

		case (R.id.btn_anteriormedida):
			try {
				exibirAnterior();
			} catch (Exception e1) {
				e1.printStackTrace();
			}			
			break;

		}
	}
	public void exibirProximo() throws Exception{
		Perfil perfil = controleperfil.buscarPerfil();
		Medida medida = null;
		if(!listaMedidas.isEmpty()){
			if(indice < listaMedidas.size()-1 ){
				medida = listaMedidas.get(indice + 1);
				indice++;
			}
			if(indice == listaMedidas.size() -1){
				medida = listaMedidas.get(listaMedidas.size() - 1);
			}
		}

		medida.setMedicao(controlemedida.ultimasMedicoes(perfil.getCodigo(), medida.getCodigo()));
		if(!medida.getMedicao().isEmpty()){
			carregaMedida(medida,perfil);
		}else{
			indice--;
		}

	}

	public void exibirAnterior() throws Exception{
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
			Perfil perfil = controleperfil.buscarPerfil();
			medida.setMedicao(controlemedida.ultimasMedicoes(perfil.getCodigo(), medida.getCodigo()));

			carregaMedida(medida, perfil);
		}
	}

	public void carregaMedida(Medida medida, Perfil perfil){
		String mensagem = "";
		try{
			int contador = 0;
			String nome = medida.getNome();
			String ndata= "Data";
			HashMap<Integer, Integer> valores = new HashMap<Integer, Integer>();

			if (medida.getLado()!= null){
				nome+= " " + medida.getLado();
			}
			
			txtmedidas.setText(nome);
			data1.setText(ndata);
			data2.setText(ndata);
			data3.setText(ndata);
			barra1.setProgress(0);
			barra2.setProgress(0);
			barra3.setProgress(0);
			
			valores = controlemedida.calcularProgresso(medida.getMedicao());
 			medida.setMedicao(controlemedida.ultimasMedicoes
					(perfil.getCodigo(), medida.getCodigo()));
			
			while (contador != medida.getMedicao().size()){
				Medicao m = medida.getMedicao().get(contador);
				String aux  =  sdf.format(m.getDataMedicao())+
				" \n" + m.getValor()
				+" "+ medida.getUnidade();
				int valor = valores.get(m.getCodigo());
				if (contador == 0){
					data1.setText(aux);
					barra1.setProgress(valor);
				}else if (contador == 1){
					data2.setText(aux);
					barra2.setProgress(valor);
				}else if (contador == 2){
					data3.setText(aux);
					barra3.setProgress(valor);
				}
				contador ++;
			}
			// criando ListView
			listaMedicoes = controlemedida.buscarListaMedicaoes(medida.getCodigo(),perfil.getCodigo());
			createListView(listaMedicoes, listahistorico, medida);
			
			

		}catch (Exception e) {
			mensagem = e.getMessage();
			Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
		}

	}


	private void calcularProgresso(Medida medida) {
		SimpleDateFormat sdf = 
			new SimpleDateFormat("dd/MM/yyyy");
		int maior = 70;
		int medio = 50;
		int menor = 30;
		int contador = 0;
		int naux = 1;
		HashMap<Integer, Integer> valores = 
			new HashMap<Integer, Integer>();
		List<Medicao> list = medida.getMedicao();
		//Collections.sort(list,new ControleMedida());
		contador = 0;

		
	}

	private void createListView
	(List <Medicao> medicoes, ListView lista, Medida medida) {
		ArrayList<Medicao> listaMedicoes = new ArrayList<Medicao>();
		itens = new ArrayList<ItemListaHistorico>();
		for (Medicao m : medicoes){
			String data = sdf.format(m.getDataMedicao());
			String valor = String.valueOf(m.getValor());
			String unidade = medida.getUnidade();
			ItemListaHistorico item = new ItemListaHistorico(valor, data, unidade);
			itens.add(item);
		}
		adapterListView = new AdaptadorHistorico(this, itens);
		lista.setAdapter(adapterListView);
		lista.setCacheColorHint(Color.TRANSPARENT);

	}


}


/*
for (Medicao m : list){
	if(contador == 0){
		valores.put(list.get(contador).getCodigo(),menor);

	}else if (contador == 1){

		if(list.get(contador).getValor() < list.get(contador - 1).getValor()){

			valores.remove(list.get(contador-1).getCodigo());
			valores.put(list.get(contador-1).getCodigo(),medio);
			valores.put(list.get(contador).getCodigo(),menor);

		}else if(list.get(contador).getValor() == list.get(contador - 1).getValor()){
			valores.remove(list.get(contador-1).getCodigo());
			valores.put(list.get(contador-1).getCodigo(),medio);
			valores.put(list.get(contador).getCodigo(),medio);

		}else{
			valores.put(list.get(contador).getCodigo(),medio);
		}


	}else if (contador == 2){
		
		
		// 3 menor elemento
		if(list.get(contador).getValor() < list.get(contador - 1).getValor()){
			if(list.get(contador).getValor() < list.get(contador - 2).getValor()){
				
				if(list.get(contador - 1).getValor() < list.get(contador - 2).getValor()){
					
					//3 menor /2 medio /1maior
					valores.put(list.get(contador-2).getCodigo(),maior);
					valores.put(list.get(contador-1).getCodigo(),medio);
					valores.put(list.get(contador).getCodigo(),menor);
				}else if(list.get(contador - 1).getValor() > list.get(contador - 2).getValor()){
					
					//3 menor /1 medio /2 maior
					valores.put(list.get(contador-2).getCodigo(),medio);
					valores.put(list.get(contador-1).getCodigo(),maior);
					valores.put(list.get(contador).getCodigo(),menor);
				}else{
					//3 menor / 1 e 2 iguais
					valores.put(list.get(contador-2).getCodigo(),medio);
					valores.put(list.get(contador-1).getCodigo(),medio);
					valores.put(list.get(contador).getCodigo(),menor);
				}
				
			}else if(list.get(contador).getValor() == list.get(contador - 1).getValor()){
				valores.put(list.get(contador-2).getCodigo(),medio);
				valores.put(list.get(contador-1).getCodigo(),menor);
				valores.put(list.get(contador).getCodigo(),menor);
			}
			
			
			else{
				//3 menor /
				valores.put(list.get(contador-2).getCodigo(),menor);
				valores.put(list.get(contador-1).getCodigo(),medio);
				valores.put(list.get(contador).getCodigo(),menor);

			}
			// 2 menor elemento
			
		}else if(list.get(contador - 1).getValor() < list.get(contador).getValor()){
				if(list.get(contador - 1).getValor() < list.get(contador - 2).getValor()){
					if(list.get(contador).getValor() < list.get(contador - 2).getValor()){
						
						//2 menor /3 medio /1maior
						valores.put(list.get(contador-2).getCodigo(),maior);
						valores.put(list.get(contador).getCodigo(),medio);
						valores.put(list.get(contador - 1).getCodigo(),menor);
					}else if(list.get(contador).getValor() > list.get(contador - 2).getValor()){
						
						//2 menor /1 medio /3 maior
						valores.put(list.get(contador-2).getCodigo(),medio);
						valores.put(list.get(contador).getCodigo(),maior);
						valores.put(list.get(contador - 1).getCodigo(),menor);
					}else{
						//2 menor / 1 e 3 iguais
						valores.put(list.get(contador-2).getCodigo(),medio);
						valores.put(list.get(contador).getCodigo(),medio);
						valores.put(list.get(contador -1).getCodigo(),menor);
					}
				}else if(list.get(contador - 1).getValor() == list.get(contador - 2).getValor()){
					valores.put(list.get(contador-2).getCodigo(),menor);
					valores.put(list.get(contador-1).getCodigo(),menor);
					valores.put(list.get(contador).getCodigo(),medio);
				}
				
				// 1 menor elemento
		}else if(list.get(contador - 2).getValor() < list.get(contador).getValor()){
			if(list.get(contador - 2).getValor() < list.get(contador - 1).getValor()){
				
				if(list.get(contador).getValor() < list.get(contador - 1).getValor()){
					//1 menor /3 medio /2maior
					valores.put(list.get(contador-1).getCodigo(),maior);
					valores.put(list.get(contador).getCodigo(),medio);
					valores.put(list.get(contador - 2).getCodigo(),menor);
				}else if(list.get(contador).getValor() > list.get(contador - 1).getValor()){
					//1 menor /2 medio /3 maior
					valores.put(list.get(contador-1).getCodigo(),medio);
					valores.put(list.get(contador).getCodigo(),maior);
					valores.put(list.get(contador - 2).getCodigo(),menor);
				}else{
					//1 menor / 2 e 3 iguais
					valores.put(list.get(contador-1).getCodigo(),medio);
					valores.put(list.get(contador).getCodigo(),medio);
					valores.put(list.get(contador -2).getCodigo(),menor);
				}
			
			}else if(list.get(contador - 2).getValor() == list.get(contador - 1).getValor()){
				valores.put(list.get(contador-2).getCodigo(),menor);
				valores.put(list.get(contador-1).getCodigo(),menor);
				valores.put(list.get(contador).getCodigo(),medio);
			}
			
			
		}else{

			valores.put(list.get(contador-1).getCodigo(),medio);
			valores.put(list.get(contador).getCodigo(),medio);
			valores.put(list.get(contador -2).getCodigo(),medio);

			
		}
			
		
		


	}
	contador = contador + 1;
}

//contador = 0;
//
//while (naux <= list.size()){
//	if (naux == 2){
//		if (list.get(contador).getValor()
//				== list.get(contador+1).getValor()){
//			valores.remove(list.get(contador).getCodigo());
//			valores.put(list.get(contador).getCodigo(), medio);
//		} 
//	}else if (naux== 3){
//		if (list.get(contador+1).getValor()
//				== list.get(contador+2).getValor()){
//			valores.remove(list.get(contador+2).getCodigo());
//			valores.put(list.get(contador+2).getCodigo(),medio);
//		}
//	}
//	naux++;
//
//}*/




