package workoutsystem.view;

import workoutsystem.control.ControlePerfil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class GUIPerfil extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfil);
	}

	@Override
	public void onClick(View evento) {
		ControlePerfil controle = new ControlePerfil();
		switch (evento.getId()) {
		case R.id.btn_manipularperfil:
			startActivity(new Intent("workoutsystem.view.MANIPULARPERFIL"));
			
			break;
		case R.id.btn_medidas:
			startActivity(new Intent("workoutsystem.view.MEDIDAS"));
			break;
		case R.id.btn_status:
			startActivity(new Intent("workoutsystem.view.STATUS"));
			break;
		case R.id.btn_avatar:
			startActivity(new Intent ("workoutsystem.view.AVATAR"));
			break;

		}
	}


}
