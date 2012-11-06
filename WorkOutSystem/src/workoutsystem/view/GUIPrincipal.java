package workoutsystem.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class GUIPrincipal extends Activity implements View.OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);
	}






	@Override
	public void onClick(View v) {
		switch (v.getId()){

		case R.id.btn_perfil:
			startActivity( new Intent("workoutsystem.view.PERFIL"));
			break;


		case R.id.btn_exercicio:
			startActivity(new Intent("workoutsystem.view.EXERCICIO"));
			break;

		case R.id.btn_rotina:
			startActivity(new Intent("workoutsystem.view.ROTINA"));
			break;

		case R.id.btn_ficha:
			startActivity(new Intent("workoutsystem.view.FICHA"));
			break;

		case R.id.btn_evolucao:
			startActivity(new Intent("workoutsystem.view.EVOLUCAO"));
			break;

		case R.id.btn_ajuda:
			startActivity(new Intent("workoutsystem.view.AJUDA"));
			break;
		case R.id.btn_sobre:
			startActivity(new Intent("workoutsystem.view.SOBRE"));
			break;
		}


	}




}
