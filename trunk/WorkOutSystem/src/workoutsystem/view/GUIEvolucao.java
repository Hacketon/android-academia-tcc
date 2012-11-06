package workoutsystem.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GUIEvolucao extends Activity {

	private TabHost host;
	private TabSpec specmedidas;
	private TabSpec specexercicio;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evolucao);
		criarHost();
	}
	
	public void criarHost(){
		host = (TabHost) findViewById(R.id.hostevolucao);
		host.setup();
		
		specmedidas = host.newTabSpec("tabmedidas");
		specmedidas.setContent(R.id.tabmedidas);
		specmedidas.setIndicator("Medidas");
		host.addTab(specmedidas);
		
		specexercicio = host.newTabSpec("tabexercicio");
		specexercicio.setContent(R.id.tabexercicio);
		specexercicio.setIndicator("Exercicios");
		host.addTab(specexercicio);
		
		
	}
	
	

}
