package workoutsystem.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GUIRealizarExercicio extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.realizarexercicio);
	}
	
	public void onClick(View evento) {
		switch (evento.getId()) {
		case R.id.btn_como_fazer:
			startActivity(new Intent("workoutsystem.view.PASSO"));
			break;
		}
	}

}
