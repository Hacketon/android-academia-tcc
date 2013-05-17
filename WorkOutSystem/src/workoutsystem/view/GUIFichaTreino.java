package workoutsystem.view;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.model.Exercicio;
import workoutsystem.model.Treino;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GUIFichaTreino extends Activity{

	private TabHost host;
	private TabSpec tabEspecificacao;
	private TabSpec  tabExercicio;
	private TabSpec tabTreino;
	private Treino treino ; 
	private EditText txtTreino;
	private ListView listaBusca;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fichatreino);
		createTabs();
		treino = (Treino) getIntent().getExtras().getSerializable("treino");
		txtTreino = (EditText) findViewById(R.id.edt_nomeTreino);
		listaBusca = (ListView) findViewById(R.id.list_busca);
		preencherTreino(treino);
	}

	private void preencherTreino(Treino t) {
		txtTreino.setText(t.getNomeTreino());
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
		tabTreino.setIndicator("Treino");
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

}
