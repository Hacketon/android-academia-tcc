package workoutsystem.view;

import workoutsystem.model.Usuario;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class GUINovoUsuario extends Activity implements View.OnClickListener{

	private EditText editUsuario;
	private EditText editSenha;
	private EditText editConfirmarSenha;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.novousuario);
		editConfirmarSenha = (EditText) findViewById(R.id.pwConfirmarSenha);
		editSenha = (EditText) findViewById(R.id.pwSenha);
		editUsuario = (EditText) findViewById(R.id.edUsuario);
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_cadastrar:
			Usuario usuario = criarUsuario();
			break;

		case R.id.btn_mostrar:
			break;
		}
		
	}

	private Usuario criarUsuario() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
	

}
