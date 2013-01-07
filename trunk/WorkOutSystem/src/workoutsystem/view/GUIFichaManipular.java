package workoutsystem.view;

import workoutsystem.model.Ficha;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GUIFichaManipular extends Activity {

	private TabHost hostfichatreino;
	private TabSpec spectreino;
	private TabSpec specficha;
	private EditText editNomeFicha;
	private EditText editDuracaoFicha;
	private EditText editObjetivoFicha;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fichamanipular);
		criarTab();
		editNomeFicha = (EditText) findViewById(R.id.edt_nomeFicha);
		editDuracaoFicha = (EditText) findViewById(R.id.edt_duracaodias);
		editObjetivoFicha = (EditText) findViewById(R.id.edt_objetivoFicha);
	}

	public void criarTab(){
		hostfichatreino = (TabHost) findViewById(R.id.hostfichatreino);
		hostfichatreino.setup();

		specficha = hostfichatreino.newTabSpec("tabfichas");
		specficha.setContent(R.id.tabfichas);
		specficha.setIndicator("Fichas");
		hostfichatreino.addTab(specficha);

		spectreino = hostfichatreino.newTabSpec("tabfichatreinos");
		spectreino.setContent(R.id.tabfichatreinos);
		spectreino.setIndicator("Treinos");
		hostfichatreino.addTab(spectreino);
	}

	
	public void criarFicha(){
		Ficha ficha = new Ficha();
		ficha.setNomeFicha(editNomeFicha.getText().toString());
		ficha.setDuracaoDias(Integer.parseInt((editDuracaoFicha.getText().toString())));
		ficha.setObjetivo(editObjetivoFicha.getText().toString());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_manipular_ficha, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.novo_treino:
			startActivity(new Intent("workoutsystem.view.FICHATREINO"));
			break;
		case R.id.remover_treino:

			break;
		case R.id.existente_treino:
			break;
		case R.id.finalizar_edicao:
			break;
		}
		return true;

	}



}
