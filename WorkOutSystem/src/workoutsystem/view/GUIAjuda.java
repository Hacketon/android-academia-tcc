package workoutsystem.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class GUIAjuda extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajuda);
	}

	@Override
	public void onClick(View evento) {
		
		switch (evento.getId()){
		
		case R.id.btn_ajudaevolucao:

			break;
		case R.id.btn_ajudaexercicio:
			break;

		case R.id.btn_ajudaficha:
			break;
		case R.id.btn_ajudaperfil:
			break;
		case R.id.btn_ajudarotina:
			break;
		
		}

	}
	





}
