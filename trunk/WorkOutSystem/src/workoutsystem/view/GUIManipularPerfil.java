package workoutsystem.view;

import workoutsystem.model.Perfil;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
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
		// TODO Auto-generated method stub

	}

	public void criaManipulaPerfil(){
		Perfil perfil = new Perfil();
		perfil.setNome(String.valueOf(editNome.getText()));
		if (radioMasculino.isChecked()){
			perfil.setSexo(true);
		}else {
			perfil.setSexo(false);
		}
		
	}

}
