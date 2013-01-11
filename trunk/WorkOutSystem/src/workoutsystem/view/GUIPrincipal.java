package workoutsystem.view;

import workoutsystem.control.ControleUsuario;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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






	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflate = getMenuInflater();
		inflate.inflate(R.menu.menu_principal, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		ControleUsuario controle = new  ControleUsuario();
		switch(item.getItemId()) {
		case R.id.sair:
			controle.desconectarUsuario();
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		case R.id.trocarusuario:
			controle.desconectarUsuario();
			Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage
			( getBaseContext().getPackageName() );
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			break;
		case R.id.trocarsenha:
			
			break;
		}
		return true;
	}




}
