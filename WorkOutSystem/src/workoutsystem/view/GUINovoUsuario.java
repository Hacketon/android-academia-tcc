package workoutsystem.view;

import workoutsystem.control.ControleUsuario;
import workoutsystem.model.Usuario;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


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
		ControleUsuario controle = new ControleUsuario();
		switch (view.getId()){
		case R.id.btn_cadastrar:
			Usuario usuario = criarUsuario();
			if (usuario != null && controle.cadastrarUsuario
					(usuario, String.valueOf(editConfirmarSenha.getText()))){
				Toast.makeText(this, "Usuario cadastrado com sucesso", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, "Usuario não pode ser cadastrado", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_mostrar:
			break;
		}
		
	}
	
/**
 * Metodo responsavel pela criação de objeto usuario
 * por meio dos campos preenchindo
 * @return usuario , se a verificação estiver correta 
 * caso contrario null
 */
	private Usuario criarUsuario() {
		Usuario usuario = new Usuario();
		if (verificarDados()){
			usuario.setNome(String.valueOf(editUsuario.getText()));
			usuario.setSenha(String.valueOf(editSenha.getText()));
			return usuario;
		}else {
			return null;
		}
		
	}
	
	public boolean verificarDados(){
		if (String.valueOf(editConfirmarSenha.getText()).isEmpty() 
			|| String.valueOf(editSenha.getText()).isEmpty()
			|| String.valueOf(editUsuario.getText()).isEmpty()){
			Toast.makeText(this, "Digite os campos obrigatorios"
					, Toast.LENGTH_LONG).show();
			return false;
		}else{
			return true;
		}
	}
}
