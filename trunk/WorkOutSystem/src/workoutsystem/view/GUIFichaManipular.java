package workoutsystem.view;

import workoutsystem.model.Ficha;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.btn_adicionartreino:
			startActivity(new Intent("workoutsystem.view.FICHATREINO"));
			break;
		}
	}

	public void criarFicha(){
		Ficha ficha = new Ficha();
		ficha.setNomeFicha(editNomeFicha.getText().toString());
		ficha.setDuracaoDias(Integer.parseInt((editDuracaoFicha.getText().toString())));
		ficha.setObjetivo(editObjetivoFicha.getText().toString());
		
	}
	
}
