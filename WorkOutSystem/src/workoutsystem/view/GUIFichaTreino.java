package workoutsystem.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GUIFichaTreino extends Activity{

	private TabHost host;
	private TabSpec tabEspecificacao;
	private TabSpec  tabExercicio;
	private TabSpec tabTreino;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fichatreino);
		createTabs();
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
	public boolean onOptionsItemSelected(MenuItem item) {
		
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.novo_treinos:
			
			break;

		case R.id.adicionar_exercicio:
			startActivity(new Intent("workoutsystem.view.BUSCAREXERCICIO"));
			break;
		case R.id.remover_exercicio:
			
			break;
		case R.id.finalizar_edicaos:
			
			break;
		}
		return true;
	}
	


}
