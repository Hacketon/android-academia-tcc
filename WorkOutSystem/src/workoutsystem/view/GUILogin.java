package workoutsystem.view;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GUILogin extends Activity implements View.OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.btn_novousuario:
			startActivity(new Intent("workoutsystem.view.NOVOUSUARIO"));
			break;
		case R.id.btn_login:
			startActivity(new Intent("workoutsystem.view.PRINCIPAL"));
			break;
		}
	}
}
