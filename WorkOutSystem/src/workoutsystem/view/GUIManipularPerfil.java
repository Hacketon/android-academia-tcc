package workoutsystem.view;

import workoutsystem.control.ControleExercicio;
import workoutsystem.control.ControlePerfil;
import workoutsystem.dao.IPerfilDao;
import workoutsystem.dao.PerfilDao;
import workoutsystem.model.Perfil;
import workoutsystem.model.Usuario;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class GUIManipularPerfil extends Activity implements View.OnClickListener {


	private TabHost tabperfil;
	private TabSpec specfrequencia;
	private TabSpec specpessoal;
	private EditText editNome;
	//radio sexo
	private RadioGroup radioSexo;
	private RadioButton radioMasculino;
	private RadioButton radioFeminino;
	ControlePerfil controle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manipularperfil);
		criarTab();
		editNome = (EditText) findViewById(R.id.etNome);
		//radio sexo
		radioSexo = (RadioGroup) findViewById(R.id.radio_group_sexo);
		radioMasculino = (RadioButton) findViewById(R.id.rb_masculino);
		radioFeminino = (RadioButton) findViewById(R.id.rb_feminino);

		IPerfilDao dao = new PerfilDao();
		Perfil perfil = dao.buscarPerfil();

		//Perfil perfil = controle.buscarPerfil();
		carregarPerfil(perfil);
		//		carregarPerfil(controle.buscarPerfil());

	}



	public void criarTab(){

		// pega o objeto hospedeiro de tab
		tabperfil = (TabHost) findViewById(R.id.tabperfil);
		tabperfil.setup();

		//criação de cada tab
		specpessoal = tabperfil.newTabSpec("tabpessoal");
		specpessoal.setContent(R.id.tabpessoal);
		specpessoal.setIndicator("Principais");
		tabperfil.addTab(specpessoal);

		specfrequencia = tabperfil.newTabSpec("tabfrequencia");
		specfrequencia.setContent(R.id.tabfrequencia);
		specfrequencia.setIndicator("Frequencia");
		tabperfil.addTab(specfrequencia);

	}


	@Override
	public void onClick(View v) {
		ControlePerfil controle = new ControlePerfil();
		switch (v.getId()){
		case R.id.btn_cadperfil:
			Perfil perfil = criaManipulaPerfil();
			Toast.makeText(this, controle.cadastrasPerfil(perfil),
					Toast.LENGTH_LONG).show();
			break;
		case R.id.btn_excperfil:
			limparCampos();
			Toast.makeText(this, controle.excluirPerfil(),
					Toast.LENGTH_LONG).show();

			break;
		}
	}


	//corrigir codigo , deixando as verificações no controle 
	public Perfil criaManipulaPerfil(){
		Usuario usuario = new Usuario(); 
		Perfil perfil = new Perfil();
		if(verificarUsuario()){
			perfil.setNome(String.valueOf(editNome.getText()));
			if (radioMasculino.isChecked()){
				perfil.setSexo(true);
			}else {
				perfil.setSexo(false);
			}
			perfil.setCodigousuario(usuario.getCodigo());
			return perfil;
		}else 
		{
			return null;
		}
	}

	public void carregarPerfil(Perfil p){
		if (p != null){
			editNome.setText(p.getNome());
			if(p.getSexo()){
				radioMasculino.setChecked(true);
			}else{
				radioFeminino.setChecked(true);
			}
		}

	}

	public void limparCampos(){
		editNome.setText("");
		radioMasculino.setChecked(false);
		radioFeminino.setChecked(false);
	}
	private boolean verificarUsuario() {
		if (String.valueOf(editNome.getText()).isEmpty() 
				|| radioSexo.isClickable()){
			Toast.makeText(this, "Digite os campos obrigatorios"
					, Toast.LENGTH_LONG).show();
			return false;
		}else{
			return true;
		}
	}

}
