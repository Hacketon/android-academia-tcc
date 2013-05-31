package workoutsystem.view;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleMedida;
import workoutsystem.control.ControlePerfil;
import workoutsystem.model.Frequencia;
import workoutsystem.model.Perfil;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class GUIPerfil extends Activity implements View.OnClickListener {


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
		setContentView(R.layout.perfil);
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

		ControlePerfil controlePerfil = new ControlePerfil();
		try {
			if(controlePerfil.buscarPerfil()!=null){
				carregarPerfil(controlePerfil.buscarPerfil());
				inicializarFrequencia();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		ControleMedida controleMed = new ControleMedida();
		Perfil perfil = null;
		switch (v.getId()){
		case R.id.btn_cadperfil:
			try{
				perfil = criaManipulaPerfil();
				if(verificarCampos()){
					if(controle.buscarPerfil()!= null){
						Toast.makeText(this, controle.atualizarPerfil(perfil),
								Toast.LENGTH_LONG).show();
					}else{
						String mensagem;
						try {
							mensagem = controle.cadastrarPerfil(perfil);
						} catch (Exception e) {
							mensagem = e.getMessage();
						}
						Toast.makeText(this, mensagem,
								Toast.LENGTH_LONG).show();

					}
					finish();
				}else{
					Toast.makeText(this, "Digite os campos (Principais / Frequencia)",
							Toast.LENGTH_LONG).show();

				}

			}catch (Exception e) {
				// TODO: handle exception
			}
						break;
		case R.id.btn_excperfil:
			try{

				limparCampos();
				perfil = criaManipulaPerfil();
				Toast.makeText(this, controleMed.excluirMedicoes(perfil.getCodigo()),
						Toast.LENGTH_SHORT).show();
				Toast.makeText(this, controle.excluirPerfil(perfil),
						Toast.LENGTH_LONG).show();
			}catch (Exception e) {
				// TODO: handle exception
			}
			break;
		}
	}


	//corrigir codigo , deixando as verificações no controle 
	public Perfil criaManipulaPerfil(){
		Perfil perfil = null;
		try{
			ControlePerfil controle = new ControlePerfil();
			
			if(controle.buscarPerfil() != null){
				perfil = controle.buscarPerfil();
			}else{
				perfil = new Perfil();	
			}

			perfil.setNome(String.valueOf(editNome.getText()).trim());
			if (radioMasculino.isChecked()){
				perfil.setSexo(true);
			}else {
				perfil.setSexo(false);
			}

			carregarFrequencia(perfil);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return perfil;
	}

	public void carregarPerfil(Perfil p){
		if (p != null){
			editNome.setText(p.getNome());
			if(p.getSexo()){
				radioMasculino.setChecked(true);
			}else{
				radioFeminino.setChecked(true);
			}
			carregarFrequencia(p);
		}



	}
	public Perfil carregarFrequencia(Perfil perfil){
		ControlePerfil controlePerf = new ControlePerfil();
		List<Frequencia> listaDias = new ArrayList<Frequencia>();

		if(frequenciaDomingo.isChecked()== true ){
			Frequencia dia = new Frequencia();
			dia.setDiaSemana("Domingo");
			dia.setCodigo(controlePerf.codigoFrequencia(dia.getDiaSemana()));
			listaDias.add(dia);

		}
		if(frequenciaSegunda.isChecked()== true ){
			Frequencia dia = new Frequencia();
			dia.setDiaSemana("Segunda");
			dia.setCodigo(controlePerf.codigoFrequencia(dia.getDiaSemana()));
			listaDias.add(dia);

		}
		if(frequenciaTerca.isChecked()== true ){
			Frequencia dia = new Frequencia();
			dia.setDiaSemana("Terça");
			dia.setCodigo(controlePerf.codigoFrequencia(dia.getDiaSemana()));
			listaDias.add(dia);

		}
		if(frequenciaQuarta.isChecked()== true ){
			Frequencia dia = new Frequencia();
			dia.setDiaSemana("Quarta");
			dia.setCodigo(controlePerf.codigoFrequencia(dia.getDiaSemana()));
			listaDias.add(dia);

		}
		if(frequenciaQuinta.isChecked()== true ){
			Frequencia dia = new Frequencia();
			dia.setDiaSemana("Quinta");
			dia.setCodigo(controlePerf.codigoFrequencia(dia.getDiaSemana()));
			listaDias.add(dia);

		}
		if(frequenciaSexta.isChecked()== true ){
			Frequencia dia = new Frequencia();
			dia.setDiaSemana("Sexta");
			dia.setCodigo(controlePerf.codigoFrequencia(dia.getDiaSemana()));
			listaDias.add(dia);

		}
		if(frequenciaSabado.isChecked()== true ){
			Frequencia dia = new Frequencia();
			dia.setDiaSemana("Sabado");
			dia.setCodigo(controlePerf.codigoFrequencia(dia.getDiaSemana()));
			listaDias.add(dia);

		}

		perfil.setFrequencia(listaDias);

		return perfil;
	}


	public void limparCampos(){
		editNome.setText("");
		radioMasculino.setChecked(false);
		radioFeminino.setChecked(false);
		frequenciaSegunda.setChecked(false);
		frequenciaTerca.setChecked(false);
		frequenciaQuarta.setChecked(false);
		frequenciaQuinta.setChecked(false);
		frequenciaSexta.setChecked(false);
		frequenciaSabado.setChecked(false);
		frequenciaDomingo.setChecked(false);
	}

	public boolean verificarCampos(){
		boolean verifica = false;
		if(!editNome.getText().toString().equals("")){
			if(radioFeminino.isChecked()== true || radioMasculino.isChecked() == true ){
				if(frequenciaDomingo.isChecked()== true || 	frequenciaSegunda.isChecked() == true ||
						frequenciaTerca.isChecked()== true || frequenciaQuarta.isChecked()== true ||
						frequenciaQuinta.isChecked() == true || 	frequenciaSexta.isChecked() == true||
						frequenciaSabado.isChecked() == true){
					
					verifica = true;

				}
			}
		}
		return verifica;

	}


	public void inicializarFrequencia(){
		try{
			ControlePerfil controlePerfil = new ControlePerfil();
			Perfil perfil = controlePerfil.buscarPerfil();
			if (perfil != null){
				perfil.setFrequencia(controlePerfil.buscarFrequencia(perfil));

				Frequencia diaPadrao = new Frequencia();


				for(Frequencia d: perfil.getFrequencia()){

					diaPadrao.setDiaSemana("Domingo");
					diaPadrao.setCodigo(controlePerfil.codigoFrequencia(diaPadrao.getDiaSemana()));

					if(d.getCodigo() == diaPadrao.getCodigo() ){
						frequenciaDomingo.setChecked(true);
					}

					diaPadrao.setDiaSemana("Segunda");
					diaPadrao.setCodigo(controlePerfil.codigoFrequencia(diaPadrao.getDiaSemana()));

					if(d.getCodigo() == diaPadrao.getCodigo() ){
						frequenciaSegunda.setChecked(true);
					}

					diaPadrao.setDiaSemana("Terça");
					diaPadrao.setCodigo(controlePerfil.codigoFrequencia(diaPadrao.getDiaSemana()));

					if(d.getCodigo() == diaPadrao.getCodigo() ){
						frequenciaTerca.setChecked(true);
					}

					diaPadrao.setDiaSemana("Quarta");
					diaPadrao.setCodigo(controlePerfil.codigoFrequencia(diaPadrao.getDiaSemana()));

					if(d.getCodigo() == diaPadrao.getCodigo() ){
						frequenciaQuarta.setChecked(true);
					}

					diaPadrao.setDiaSemana("Quinta");
					diaPadrao.setCodigo(controlePerfil.codigoFrequencia(diaPadrao.getDiaSemana()));

					if(d.getCodigo() == diaPadrao.getCodigo() ){
						frequenciaQuinta.setChecked(true);
					}

					diaPadrao.setDiaSemana("Sexta");
					diaPadrao.setCodigo(controlePerfil.codigoFrequencia(diaPadrao.getDiaSemana()));

					if(d.getCodigo() == diaPadrao.getCodigo() ){
						frequenciaSexta.setChecked(true);
					}

					diaPadrao.setDiaSemana("Sabado");
					diaPadrao.setCodigo(controlePerfil.codigoFrequencia(diaPadrao.getDiaSemana()));

					if(d.getCodigo() == diaPadrao.getCodigo() ){
						frequenciaSabado.setChecked(true);
					}


				}}

		}catch (Exception e) {
			// TODO: handle exception
		}
		
		}


	}




