package workoutsystem.view;

import java.util.ArrayList;

import workoutsystem.utilitaria.TipoExercicio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GUIExercicio extends Activity implements View.OnClickListener{

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
		
		ArrayList<String> tipoExercicio = new ArrayList<String>();
		
		for (TipoExercicio t : TipoExercicio.values())
			tipoExercicio.add(t.getTipoExercicio());
		
		
		ArrayAdapter<String> adapter =
		new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tipoExercicio);

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





}
