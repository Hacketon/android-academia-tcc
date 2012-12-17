package workoutsystem.view;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleExercicio;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class GUIExercicio extends Activity implements View.OnClickListener,Spinner.OnItemSelectedListener{

	private TabHost hospedeiro;
	private TabSpec tabpadrao;
	private TabSpec tabcriado;
	private Spinner cbxExercicioPadrao;
	private Spinner cbxExercicioCriado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercicio);

		cbxExercicioCriado = (Spinner) findViewById(R.id.cbx_grupocriado);
		cbxExercicioPadrao = (Spinner) findViewById(R.id.cbx_grupopadrao);
		cbxExercicioCriado.setOnItemSelectedListener(this);
		cbxExercicioPadrao.setOnItemSelectedListener(this);
		criarTabs();
		criarCombo();
	}



	public void criarTabs(){
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

	public void criarCombo(){
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
		switch (parent.getId()) {
		case R.id.cbx_grupocriado:
			listaExercicios = controle.listarExercicios(parent.getItemAtPosition(pos).toString(),true);
			
			break;
		case R.id.cbx_grupopadrao:
			listaExercicios = controle.listarExercicios(parent.getItemAtPosition(pos).toString(),false);
			break;
		}

	}



	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}





}
