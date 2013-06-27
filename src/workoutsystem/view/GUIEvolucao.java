package workoutsystem.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import workoutsystem.control.ControleMedida;
import workoutsystem.control.ControlePerfil;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;
import workoutsystem.model.Perfil;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.AdaptadorHistorico;
import workoutsystem.utilitaria.ItemListaHistorico;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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


	//	private ListView listView; - ok
	//    private AdapterListView adapterListView; - 
	//    private ArrayList<ItemListView> itens;




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
		txtmedidas = (TextView) findViewById(R.id.txt_medidas);
		indice = 0 ;
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
				Toast.makeText(this,"Seu perfil não possui medições!",
						Toast.LENGTH_LONG).show();
				finish();

			}else{
				exibirAnterior();
			}

		}catch (Exception e) {
			Toast.makeText(this,"Primeiro crie o seu perfil e adicione medições!",
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	public void criarTabs(){

		hostEvolucao = (TabHost) findViewById(R.id.hostevolucao);
		hostEvolucao.setup();

		tabEvolucao = hostEvolucao.newTabSpec("tabEvolucao");
		tabEvolucao.setContent(R.id.tabEvolucao);
		tabEvolucao.setIndicator("Evolução");
		hostEvolucao.addTab(tabEvolucao);

		tabHistorico = hostEvolucao.newTabSpec("tabHistorico");
		tabHistorico.setContent(R.id.tabHistorico);
		tabHistorico.setIndicator("Historico");
		hostEvolucao.addTab(tabHistorico);


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
		try{
			
		
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


		// criando ListView
		listaMedicoes = controlemedida.buscarListaMedicaoes(medida.getCodigo(),perfil.getCodigo());

		createListView(listaMedicoes, listahistorico, medida);
		}catch (Exception e) {
			
		}

	}

	private void calcularProgresso(Medida medida) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int maior = 70;
		int medio = 50;
		int menor = 30;
		int contador = 0;
		int naux = 1;
		HashMap<Integer, Integer> valores = 
			new HashMap<Integer, Integer>();
		List<Medicao> list = medida.getMedicao();
		Collections.sort(list,new ControleMedida());

		for (Medicao m : list){
			if(contador == 0){
				valores.put(list.get(contador).getCodigo(),menor);
			}else if (contador == 1){
				valores.put(list.get(contador).getCodigo(),medio);
			}else if (contador == 2){
				valores.put(list.get(contador).getCodigo(), maior);
			}
			contador = contador + 1;
		}

		contador = 0;

		while (naux <= list.size()){
			if (naux == 2){
				if (list.get(contador).getValor()
						== list.get(contador+1).getValor()){
					valores.remove(list.get(contador).getCodigo());
					valores.put(list.get(contador).getCodigo(), medio);
				} 
			}else if (naux== 3){
				if (list.get(contador+1).getValor()
						== list.get(contador+2).getValor()){
					valores.remove(list.get(contador+2).getCodigo());
					valores.put(list.get(contador+2).getCodigo(),medio);
				}
			}
			naux++;

		}
		contador = 0;

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




	}




	private void createListView(List <Medicao> medicoes, ListView lista, Medida medida) {




		ArrayList<Medicao> listaMedicoes = new ArrayList<Medicao>();

		itens = new ArrayList<ItemListaHistorico>();



		for (Medicao m : medicoes){



			String data = sdf.format(m.getDataMedicao());
			//String valor = String.valueOf(m.getValor()+ " "+medida.getUnidade());
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



