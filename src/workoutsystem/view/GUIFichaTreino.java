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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fichatreino);
		
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
