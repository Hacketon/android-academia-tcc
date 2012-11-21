package workoutsystem.view;



import workoutsystem.controller.ControleUsuario;
import workoutsystem.model.Usuario;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GUILogin extends Activity implements View.OnClickListener{

	private EditText editLogin;
	private EditText editPassword;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		editLogin = (EditText) findViewById(R.id.edLogin);
		editPassword = (EditText) findViewById(R.id.edSenha);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}
	@Override
	public void onClick(View v) {
		ControleUsuario controle = new ControleUsuario(this);
		switch (v.getId()){
		case R.id.btn_novousuario:
			startActivity(new Intent("workoutsystem.view.NOVOUSUARIO"));
			break;
		case R.id.btn_login:
			Usuario usuario = criarLogin();
			if (usuario != null){
				if (controle.realizarLogin(usuario)){
					startActivity(new Intent("workoutsystem.view.PRINCIPAL"));
					limparCampos();
				}else{
					Toast.makeText(this, "Usuario Invalido"
							,Toast.LENGTH_LONG).show();
				}
			}

			break;
		}
	}
	
	public void limparCampos(){
		editLogin.setText("");
		editPassword.setText("");
	}

	public Usuario criarLogin(){
		if (verificarUsuario()){

			Usuario usuario = new Usuario();
			usuario.setNome(String.valueOf(editLogin.getText()));
			usuario.setSenha(String.valueOf(editPassword.getText()));

			return usuario;
		}else{
			return null;
		}


	}
	
	private boolean verificarUsuario() {
		if (String.valueOf(editLogin.getText()).isEmpty() 
				|| String.valueOf(editPassword.getText()).isEmpty()){

			Toast.makeText(this, "Digite os campos obrigatorios"
					, Toast.LENGTH_LONG).show();
			return false;
		}else{
			return true;
		}
	}
}
