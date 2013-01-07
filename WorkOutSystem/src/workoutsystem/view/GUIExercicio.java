package workoutsystem.view;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleExercicio;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class GUIExercicio extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener, 
ListView.OnItemClickListener , DialogInterface.OnMultiChoiceClickListener,DialogInterface.OnClickListener{

	private TabHost hospedeiro;
	private TabSpec tabpadrao;
	private TabSpec tabcriado;
	private ListView listapadrao;
	private ListView listacriado;
	private Spinner cbxExercicioPadrao;
	private Spinner cbxExercicioCriado;
	private boolean [] selecaoexc;
	private String [] exercicios;

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
		case (R.id.btn_exc):
			if (exercicios != null && selecaoexc != null){
				showDialog(0);
			}
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
			criarExclusao(listaExercicios);

		}else if (parent.getId()== R.id.cbx_grupopadrao){
			listaExercicios = controle.listarExercicios
			(parent.getItemAtPosition(pos).toString(),0);
			createListView(listaExercicios,listapadrao);

		}
	}

	private void criarExclusao(List<Exercicio> listaExercicios) {
		int i = 0 ;
		exercicios = null;
		selecaoexc = null;
		exercicios = new String[listaExercicios.size()]; 
		for (Exercicio e : listaExercicios){
			exercicios[i] = e.getNomeExercicio();
			i++;
		}
		selecaoexc = new boolean[exercicios.length];
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}


	private void createListView(List <Exercicio> exercicios,ListView lista) {
		ArrayList<String> nomes = new ArrayList<String>();
		for (Exercicio e : exercicios){
			nomes.add(e.getNomeExercicio());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>
		(this,R.layout.itens_simple_lista,nomes);
		adapter.notifyDataSetChanged();
		lista.setAdapter(adapter);
		lista.setCacheColorHint(Color.BLUE);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		ControleExercicio controle = new ControleExercicio();
		Exercicio exercicio = 
			controle.buscarExercicio(parent.getItemAtPosition(pos).toString());
		Intent i = null ;
		if (parent.getId() == R.id.listacriado){
			i = new Intent(this, GUICriarExercicio.class);
		}else if (parent.getId() == R.id.listapadrao){
			i = new Intent(this,GUIPasso.class);
		}
		i.putExtra("exercicio",exercicio);
		startActivity(i);
		
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return new AlertDialog.Builder(this)
		.setTitle("Exercicios")
		.setMultiChoiceItems(exercicios, selecaoexc, this)
		.setPositiveButton("Deletar",this)
		.setNeutralButton("Cancelar",this)
		.create();
		
	}

	@Override
	public void onClick(DialogInterface dialog, int clicked) {
		switch (clicked) {
		case DialogInterface.BUTTON_POSITIVE:
			Toast.makeText(this, deletarExercicios(),Toast.LENGTH_SHORT).show();
		break;

		}
		
	}

	private String deletarExercicios() {
		ControleExercicio controle = new ControleExercicio();
		int i = 0 ;
		int contador = 0;
		ArrayList<String> opc = new ArrayList<String>();
		
		for (boolean b : selecaoexc){
			if (b == true){
				contador++;
			}else{
				opc.add(exercicios[i]);
			}
			i++;
		}
		i = 0;
		contador = exercicios.length - contador;
		exercicios = null;
		selecaoexc = null;
		if (contador != 0){
			exercicios = new String[contador];
			selecaoexc = new boolean[contador];
			for (String o : opc ){
				exercicios[i] = o;
				i++;
			}

		}

		return controle.excluirExercicio(exercicios);
	}

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		// TODO Auto-generated method stub
		selecaoexc[which]= isChecked;
	}

	
	
	



}
