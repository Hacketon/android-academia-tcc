package workoutsystem.view;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleExercicio;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import workoutsystem.model.Treino;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GUIFichaTreino extends Activity
implements
AdapterView.OnItemSelectedListener,
ListView.OnItemClickListener{

	private TabHost host;
	private TabSpec tabEspecificacao;
	private TabSpec  tabExercicio;
	private TabSpec tabTreino;
	private Treino treino ; 
	private EditText txtTreino;
	private ListView listaBusca;
	private Spinner cbxGrupo;
	private List<String> listaGrupos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fichatreino);
		createTabs();
		cbxGrupo = (Spinner) findViewById(R.id.cbx_buscaexercicio);
		treino = (Treino) getIntent().getExtras().getSerializable("treino");
		listaBusca = (ListView) findViewById(R.id.list_busca);
		preencherTreino(treino);
		cbxGrupo.setOnItemSelectedListener(this);
		listaBusca.setOnItemClickListener(this);
		criarCombo();
	}

	private void criarCombo() {

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
		cbxGrupo.setAdapter(adapter);
	}

	private void preencherTreino(Treino t) {
		//txtTreino.setText(t.getNomeTreino());
		List<Exercicio> exercicios = t.getExercicios();
	}

	private void createListView(){
		List<String> nome = new ArrayList<String>();

	}

	private void createTabs() {
		host = (TabHost) findViewById(R.id.host_treino);
		host.setup();

		tabTreino = host.newTabSpec("tab_treino");
		tabTreino.setContent(R.id.tab_treino);
		tabTreino.setIndicator("Busca");
		host.addTab(tabTreino);


		tabExercicio = host.newTabSpec("tab_exercicio_treino");
		tabExercicio.setContent(R.id.tab_exercicio_treino);
		tabExercicio.setIndicator("Exercicios");
		host.addTab(tabExercicio);

		tabEspecificacao = host.newTabSpec("tab_especificacao");
		tabEspecificacao.setContent(R.id.tab_especificacao);
		tabEspecificacao.setIndicator("Especificação");
		host.addTab(tabEspecificacao);



	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_treino_ficha, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.novo_treinos:

			break;
		case R.id.finalizar_edicaos:

			break;
		}
		return true;
	}



	@Override
	public void onBackPressed() {
		super.onBackPressed();


	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		String item = parent.getItemAtPosition(pos).toString();
		ControleExercicio controle = new ControleExercicio();
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		// multiple choice da list busca
		
	}

}
