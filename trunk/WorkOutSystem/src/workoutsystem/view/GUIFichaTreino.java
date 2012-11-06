package workoutsystem.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GUIFichaTreino extends Activity{

	private TabHost hospedeiro;
	private TabSpec spectreino;
	private TabSpec specexercicio;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fichatreino);
		criarTab();
	}
	
	public void criarTab(){
		hospedeiro = (TabHost) findViewById(R.id.hostfichatreino);
		hospedeiro.setup();
		
		spectreino = hospedeiro.newTabSpec("tabfichatreino");
		spectreino.setContent(R.id.tabfichastreino);
		spectreino.setIndicator("Treinos");
		hospedeiro.addTab(spectreino);
		
		specexercicio = hospedeiro.newTabSpec("tabfichaexercicio");
		specexercicio.setContent(R.id.tabfichaexercicio);
		specexercicio.setIndicator("Exercicios");
		hospedeiro.addTab(specexercicio);
	}
	

}
