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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class GUIExercicio extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener, 
ListView.OnItemClickListener {

	private TabHost hospedeiro;
	private TabSpec tabpadrao;
	private TabSpec tabcriado;
	private ListView listapadrao;
	private ListView listacriado;
	private Spinner cbxExercicioPadrao;
	private Spinner cbxExercicioCriado;
	private Spinner cbxGrupo;
	private EditText editNomeExercicio;
	private EditText editDescricaoExercicio;
	private boolean [] selecaoexc;
	private String [] exercicios;
	private ArrayAdapter<String> adapter;
	private Dialog dialog;
	private Button btnSalvar;
	private Button btnCancelar;
	private ArrayList<String> listaGrupos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercicio);
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.criarexercicio);
		cbxExercicioCriado = (Spinner) findViewById(R.id.cbx_grupocriado);
		cbxExercicioPadrao = (Spinner) findViewById(R.id.cbx_grupopadrao);
		cbxGrupo= (Spinner) dialog.findViewById(R.id.cbx_grupo);
		listapadrao = (ListView) findViewById(R.id.listapadrao);
		listacriado = (ListView) findViewById(R.id.listacriado);
		editDescricaoExercicio = (EditText) dialog.findViewById(R.id.edt_descricaoExercicio);
		editNomeExercicio = (EditText) dialog.findViewById(R.id.edt_nomeExercicio);
		btnSalvar = (Button) dialog.findViewById(R.id.btn_criar);
		btnCancelar = (Button) dialog.findViewById(R.id.btn_voltar);

		btnCancelar.setOnClickListener(this);
		btnSalvar.setOnClickListener(this);
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
		listaGrupos = new ArrayList<String>();
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
		cbxGrupo.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.btn_add):
			criarCaixaDialog("Novo Exercicio");

		break;
		
		case (R.id.btn_criar):
			Exercicio e = criarExercicio();
			ControleExercicio ex = new ControleExercicio();
			Toast.makeText(this,ex.manipularExercicio(e),
			Toast.LENGTH_LONG).show();
			ArrayList<Exercicio> exercicios  = (ArrayList<Exercicio>)
			ex.listarExercicios(e.getGrupoMuscular().getNome(), e.getPersonalizado());
			createListView(exercicios, listacriado);
			dialog.dismiss();
		break;
		case (R.id.btn_voltar):
			dialog.dismiss();
		}


	}

	private void criarCaixaDialog(String titulo) {
		dialog.setTitle(titulo);
		editDescricaoExercicio.setText("");
		editNomeExercicio.setText("");
		dialog.show();

	}
	
	private void carregarExercicio(Exercicio exercicio) {
		int i = 0;
		if (exercicio != null){
			editNomeExercicio.setText(exercicio.getNomeExercicio());
			editDescricaoExercicio.setText(exercicio.getDescricao());
			for (String l : listaGrupos){
				if (l.equalsIgnoreCase(exercicio.getGrupoMuscular().getNome())){
					cbxGrupo.setSelection(i);
					break;
				}
				i++;
			}

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
		for (Exercicio e : exercicios){
			nomes.add(e.getNomeExercicio());
		}
		adapter = new ArrayAdapter<String>
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
		criarCaixaDialog("Alterar Exercicio",exercicio);
	}

	private void criarCaixaDialog(String titulo, Exercicio exercicio) {
		dialog.setTitle(titulo);
		carregarExercicio(exercicio);
		dialog.show();


	}

	public Exercicio criarExercicio(){
		Exercicio exercicio = new Exercicio();
		GrupoMuscular grupo = new GrupoMuscular();
		exercicio.setNomeExercicio(editNomeExercicio.getText().toString());
		grupo.setNome(cbxGrupo.getSelectedItem().toString());
		exercicio.setDescricao(editDescricaoExercicio.getText().toString());
		exercicio.setGrupoMuscular(grupo);
		return exercicio;
	}
	public Exercicio criarExercicio(Exercicio exercicio){
		GrupoMuscular grupo = new GrupoMuscular();
		exercicio.setNomeExercicio(editNomeExercicio.getText().toString());
		grupo.setNome(cbxGrupo.getSelectedItem().toString());
		exercicio.setDescricao(editDescricaoExercicio.getText().toString());
		exercicio.setGrupoMuscular(grupo);
		return exercicio;
		
		
	}


}
