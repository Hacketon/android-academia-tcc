package workoutsystem.view;

import workoutsystem.control.ControleExercicio;
import workoutsystem.control.ControlePerfil;
import workoutsystem.dao.PerfilDao;
import workoutsystem.interfaces.IPerfilDao;
import workoutsystem.model.Perfil;
import workoutsystem.model.Usuario;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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
	private RadioGroup radioSexo;
	private RadioButton radioMasculino;
	private RadioButton radioFeminino;
	private CheckBox frequenciaSegunda;
	private CheckBox frequenciaTerca;
	private CheckBox frequenciaQuarta;
	private CheckBox frequenciaQuinta;
	private CheckBox frequenciaSexta;
	private CheckBox frequenciaSabado;
	private CheckBox frequenciaDomingo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manipularperfil);
		criarTab();
		editNome = (EditText) findViewById(R.id.etNome);

		radioSexo = (RadioGroup) findViewById(R.id.radio_group_sexo);
		radioMasculino = (RadioButton) findViewById(R.id.rb_masculino);
		radioFeminino = (RadioButton) findViewById(R.id.rb_feminino);

		frequenciaSegunda = (CheckBox) findViewById(R.id.check_segunda);
		frequenciaTerca = (CheckBox) findViewById(R.id.check_terca);
		frequenciaQuarta = (CheckBox) findViewById(R.id.check_quarta);
		frequenciaQuinta = (CheckBox) findViewById(R.id.check_quinta);
		frequenciaSexta = (CheckBox) findViewById(R.id.check_sexta);
		frequenciaSabado = (CheckBox) findViewById(R.id.check_sabado);
		frequenciaDomingo = (CheckBox) findViewById(R.id.check_domingo);

		carregarPerfil(new ControlePerfil().buscarPerfil());
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
		Perfil perfil = null;
		switch (v.getId()){
		case R.id.btn_cadperfil:
			perfil = criaManipulaPerfil();
			Toast.makeText(this, controle.cadastrarPerfil(perfil),
					Toast.LENGTH_LONG).show();
			break;
		case R.id.btn_excperfil:
			limparCampos();
			perfil = criaManipulaPerfil();
			Toast.makeText(this, controle.excluirPerfil(perfil),
					Toast.LENGTH_LONG).show();
			break;
		}
	}


	//corrigir codigo , deixando as verificações no controle 
	public Perfil criaManipulaPerfil(){
		Perfil perfil = new Perfil();
		if(verificarUsuario()){
			perfil.setNome(String.valueOf(editNome.getText()));
			if (radioMasculino.isChecked()){
				perfil.setSexo(true);
			}else {
				perfil.setSexo(false);
			}
			return perfil;
		}else {
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
					, Toast.LENGTH_SHORT).show();
			return false;
		}else{
			return true;
		}
	}

}
