package workoutsystem.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GUIFicha extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.ficha);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case (R.id.btn_adicionarficha):
			startActivity(new Intent("workoutsystem.view.FICHAMANIPULAR"));
			break;
		case (R.id.btn_removerficha):
			break;
		case (R.id.btn_mudarficha):
			break;
		}
		
	}

	
	
}
