package workoutsystem.view;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleExercicio;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SyncStateContract.Constants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class GUIExercicio extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener, ListView.OnItemClickListener{

	private TabHost hospedeiro;
	private TabSpec tabpadrao;
	private TabSpec tabcriado;
	private ListView listapadrao;
	private ListView listacriado;
	private Spinner cbxExercicioPadrao;
	private Spinner cbxExercicioCriado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.exercicio);

		cbxExercicioCriado = (Spinner) findViewById(R.id.cbx_grupocriado);
		cbxExercicioPadrao = (Spinner) findViewById(R.id.cbx_grupopadrao);
		listapadrao = (ListView) findViewById(R.id.listapadrao);
		listacriado = (ListView) findViewById(R.id.listacriado);

		cbxExercicioCriado.setOnItemSelectedListener(this);
		cbxExercicioPadrao.setOnItemSelectedListener(this);
		listacriado.setOnItemClickListener(this);
		listapadrao.setOnItemClickListener(this);

		criarTabs();
		criarCombo();
	}

	private void criarTabs(){
		hospedeiro = (TabHost) findViewById(R.id.hospedeiro);
		hospedeiro.setup();

		tabpadrao = hospedeiro.newTabSpec("tabpadrao");
		tabpadrao.setContent(R.id.tabpadrao);
		tabpadrao.setIndicator("Padrão");
		hospedeiro.addTab(tabpadrao);

		tabcriado = hospedeiro.newTabSpec("tabcriado");
		tabcriado.setContent(R.id.tabcriado);
		tabcriado.setIndicator("Criado");
		hospedeiro.addTab(tabcriado);

	}

	private void criarCombo(){
		ArrayList<String> listaGrupos = new ArrayList<String>();
		ControleExercicio controle = new ControleExercicio();
		ArrayList<GrupoMuscular> grupos = 
			(ArrayList<GrupoMuscular>) controle.listarGrupos(); 

		for (GrupoMuscular grupo : grupos){
			listaGrupos.add(grupo.getNome());
		}

		ArrayAdapter<String> adapter =
			new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listaGrupos);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
		cbxExercicioCriado.setAdapter(adapter);
		cbxExercicioPadrao.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.btn_add):
			startActivity(new Intent("workoutsystem.view.CRIAREXERCICIO"));
		break;

		case (R.id.tabcriado):
			System.out.print("Clicou criado");
		break;
		default:
			break;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		ControleExercicio controle = new ControleExercicio();
		List<Exercicio> listaExercicios = null;
		if (parent.getId() == R.id.cbx_grupocriado){
			listaExercicios = controle.listarExercicios
			(parent.getItemAtPosition(pos).toString(),1);
			createListView(listaExercicios,listacriado);

		}else if (parent.getId()== R.id.cbx_grupopadrao){
			listaExercicios = controle.listarExercicios
			(parent.getItemAtPosition(pos).toString(),0);
			createListView(listaExercicios,listapadrao);

		}
	}



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}


	private void createListView(List <Exercicio> exercicios,ListView lista) {
		ArrayList<String> nomes = new ArrayList<String>();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.itens_simple_lista,nomes);
		lista.setAdapter(adapter);
		
		for (Exercicio e : exercicios){
			nomes.add(e.getNomeExercicio());
		}
		adapter = null;
		adapter = new ArrayAdapter<String>(this,R.layout.itens_simple_lista,nomes);
		lista.setAdapter(adapter);
		lista.setCacheColorHint(Color.BLUE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		ControleExercicio controle = new ControleExercicio();
		
		if (parent.getId() == R.id.listacriado){
			Exercicio exercicio = controle.buscarExercicio(parent.getItemAtPosition(pos).toString());
			Intent i = new Intent(this, GUICriarExercicio.class);
			i.putExtra("exercicio",exercicio);
			startActivity(i);
			
		}else if (parent.getId() == R.id.listapadrao){

		}

		
	}



}
