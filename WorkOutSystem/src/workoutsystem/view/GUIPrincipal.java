package workoutsystem.view;

import workoutsystem.control.ControleUsuario;
import workoutsystem.model.Usuario;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class GUIPrincipal extends Activity implements View.OnClickListener{
	private TextView txtUsuario;
	private Dialog dialogAlteracao;
	private Button btnAlterar;
	private Button btnCancelar;
	private EditText edtSenhaNova;
	private EditText edtSenhaAtual;
	private EditText edtConfirmacaoSenha;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);
		dialogAlteracao = new Dialog(this);
		dialogAlteracao.setContentView(R.layout.troca_senha_personalizada);
		
		btnAlterar = (Button) dialogAlteracao.findViewById(R.id.botao_alterar_senha);
		btnCancelar= (Button) dialogAlteracao.findViewById(R.id.botao_cancel_alteracao);
		txtUsuario = (TextView) dialogAlteracao.findViewById(R.id.txt_usuariocad);
		edtSenhaAtual = (EditText) dialogAlteracao.findViewById(R.id.edt_senhaatual);
		edtConfirmacaoSenha = (EditText) dialogAlteracao.findViewById(R.id.edt_confirmar_senha);
		edtSenhaNova = (EditText) dialogAlteracao.findViewById(R.id.edt_nova_senha);
		
		btnAlterar.setOnClickListener(this);
		btnCancelar.setOnClickListener(this);
		
	}






	@Override
	public void onClick(View v) {
		ControleUsuario controle = new ControleUsuario();
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
		case R.id.botao_alterar_senha:
			alterarSenha();
			break;
		case R.id.botao_cancel_alteracao:
			dialogAlteracao.dismiss();
			break;
		}


	}






	private void alterarSenha() {
		ControleUsuario controle = new ControleUsuario();
		String mensagem = "Verifique os campos digitados";
		if (verificarCampos()){
			Usuario u = controle.buscarUsuario();
			String senhaAtual = edtSenhaAtual.getText().toString();
			String senhaNova = edtSenhaNova.getText().toString();
			String confSenha = edtConfirmacaoSenha.getText().toString();
			
			if (controle.alterarSenha(u, senhaNova, confSenha, senhaAtual)){
				mensagem = "Senha alterada com sucesso";
				dialogAlteracao.dismiss();
			}else{
				mensagem = "Senha incorreta";
			}
		
		}
			Toast.makeText(this, mensagem, Toast.LENGTH_LONG);
		
	}






	private boolean verificarCampos() {
		String comparacao = "";
		if (!edtConfirmacaoSenha.toString().equalsIgnoreCase(comparacao) 
			&& !edtSenhaAtual.toString().equalsIgnoreCase(comparacao)
			&& !edtSenhaNova.toString().equalsIgnoreCase(comparacao)){
			return true;
		}else{
			return false;
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
			criarCaixaAlteracao();
			break;
		}
		return true;
	}
	
	public void criarCaixaAlteracao(){
		ControleUsuario controle = new ControleUsuario();
		Usuario usuario = controle.buscarUsuario();
				
		dialogAlteracao.setTitle("Troca de Senha");
		txtUsuario.setText(usuario.getNome());
		dialogAlteracao.show();
		
		
	}



}
