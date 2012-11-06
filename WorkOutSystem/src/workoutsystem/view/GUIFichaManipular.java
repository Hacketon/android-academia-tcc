package workoutsystem.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GUIFichaManipular extends Activity {

	private TabHost hostfichatreino;
	private TabSpec spectreino;
	private TabSpec specficha;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fichamanipular);
		criarTab();
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

}
