package workoutsystem.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import workoutsystem.control.ControleMedida;
import workoutsystem.control.ControlePerfil;
import workoutsystem.control.ControleUsuario;
import workoutsystem.dao.MedidaDao;
import workoutsystem.dao.PerfilDao;
import workoutsystem.dao.UsuarioDao;
import workoutsystem.interfaces.IMedidaDao;
import workoutsystem.interfaces.IPerfilDao;
import workoutsystem.interfaces.IUsuarioDao;
import workoutsystem.model.DiaSemana;
import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;
import workoutsystem.model.Perfil;
import workoutsystem.model.Usuario;
import android.app.Activity;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class GUIMedida extends Activity implements View.OnClickListener{

	private TabHost tabmedidas;
	private TabSpec specsuperior;
	private TabSpec specinferior;
	private TabSpec specprincipal;

	private EditText editAltura;
	private EditText editPeso;
	private EditText editCintura;
	private EditText editQuadril;
	private EditText editBracoEsq;
	private EditText editBracoDir;
	private EditText editPeito;
	private EditText editCoxaDir;
	private EditText editCoxaEsq;
	private EditText editPantuDir;
	private EditText editPantuEsq;


	private TextView textAltura;
	private TextView textPeso;
	private TextView textCintura;
	private TextView textQuadril; 
	private TextView textBracoDir ;
	private TextView textBracoEsq;
	private TextView textPeito ;
	private TextView textCoxaDir ;
	private TextView textCoxaEsq ;
	private TextView textPantuDir;
	private TextView textPantuEsq ;

	private Button btnSalvar;
	private Button btnAlterar;
	private Button btnCancelar;
	private Button btnNovo;


	ControleMedida controleMed = new ControleMedida();
	ControlePerfil controle = new ControlePerfil();
	List<Medicao> lista = new ArrayList<Medicao>();
	Perfil perfil = new Perfil();

	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.medidas);
		criarTab();

		btnSalvar = (Button) findViewById(R.id.btn_salvar);
		btnAlterar = (Button) findViewById(R.id.btn_alterar);
		btnCancelar = (Button) findViewById(R.id.btn_cancelar);
		btnNovo = (Button) findViewById(R.id.btn_novo);

		editAltura = (EditText) findViewById(R.id.ed_altura);
		editPeso = (EditText) findViewById(R.id.ed_peso);
		editCintura = (EditText) findViewById(R.id.ed_cintura);
		editQuadril = (EditText) findViewById(R.id.ed_quadril);
		editBracoDir = (EditText) findViewById(R.id.ed_bracodireito);
		editBracoEsq = (EditText) findViewById(R.id.ed_bracoesquerdo);
		editPeito = (EditText) findViewById(R.id.ed_peito);
		editCoxaDir = (EditText) findViewById(R.id.ed_coxadireita);
		editCoxaEsq = (EditText) findViewById(R.id.ed_coxaesquerda);
		editPantuDir = (EditText) findViewById(R.id.ed_panturrilhadireita);
		editPantuEsq = (EditText) findViewById(R.id.ed_panturilhaesquerda);


		perfil = controle.buscarPerfil();

		if(perfil != null){
			lista = controleMed.buscarMedicao(perfil.getCodigo());
			carregarCampos(lista);
			iniciarBotao(lista);
		}else{

			Toast.makeText(this,"Antes de Adicionar as medidas, crie seu PERFIL primeiro !",
					Toast.LENGTH_LONG).show();

		}
	}


	public void criarTab(){
		// pega o objeto hospedeiro de tab
		tabmedidas = (TabHost) findViewById(R.id.tabmedidas);
		tabmedidas.setup();

		//criação de cada tab
		specprincipal = tabmedidas.newTabSpec("tabprincipais");
		specprincipal.setContent(R.id.tabprincipais);
		specprincipal.setIndicator("Principais");
		tabmedidas.addTab(specprincipal);

		specsuperior = tabmedidas.newTabSpec("tabsuperiores");
		specsuperior.setContent(R.id.tabsuperiores);
		specsuperior.setIndicator("Superiores");
		tabmedidas.addTab(specsuperior);


		specinferior = tabmedidas.newTabSpec("tabinferiores");
		specinferior.setContent(R.id.tabinferiores);
		specinferior.setIndicator("Inferiores");
		tabmedidas.addTab(specinferior);

	}
	@Override
	public void onClick(View v) {

		perfil = controle.buscarPerfil();

		switch (v.getId()){
		case R.id.btn_salvar:
			criaMedida();
			btnAlterar.setEnabled(true);
			btnNovo.setEnabled(true);
			btnSalvar.setEnabled(false);
			btnCancelar.setEnabled(false);

			break;

		case R.id.btn_cancelar:
			btnAlterar.setEnabled(true);
			btnNovo.setEnabled(true);
			btnSalvar.setEnabled(false);
			btnCancelar.setEnabled(false);


			lista = controleMed.buscarMedicao(perfil.getCodigo());
			carregarCampos(lista);

			break;

		case R.id.btn_novo:

			btnAlterar.setEnabled(false);
			btnNovo.setEnabled(false);
			btnSalvar.setEnabled(true);
			btnCancelar.setEnabled(true);
			break;

		case R.id.btn_alterar:
			lista = controleMed.buscarMedicao(perfil.getCodigo());
			List<Medicao> listaUltimosValores = new ArrayList<Medicao>();
			listaUltimosValores = ObterUltimosValores(lista);


			Toast.makeText(this,controleMed.alterarUltimasMedicoes(listaUltimosValores),
					Toast.LENGTH_LONG).show();


			//			btnAlterar.setEnabled(false);
			//			btnNovo.setEnabled(false);
			//			btnSalvar.setEnabled(true);
			//			btnCancelar.setEnabled(true);


			break;
		}
	}

	public void criaMedida(){
		ControleMedida controleMed = new ControleMedida();
		ControlePerfil controlePerf = new ControlePerfil();

		List<Medicao> listaMedicao = new ArrayList<Medicao>();

		java.util.Date data = new java.util.Date(); 


		Perfil perfil = controlePerf.buscarPerfil();

		if(perfil != null){

			//Altura
			if(!editAltura.getText().toString().equalsIgnoreCase("")){


				Medida medida = new Medida();
				Medicao medicao = new Medicao();

				medicao.setValor(Double.parseDouble(editAltura.getText().toString()));
				medicao.setDataMedicao(data);
				medicao.setCodigoPerfil(perfil.getCodigo());
				medida.setCodigo(controleMed.buscarMedida("Altura", "a"));
				medicao.setCodigoMedida(medida.getCodigo());

				listaMedicao.add(medicao);

			}

			//Peso
			if(!editPeso.getText().toString().equalsIgnoreCase("")){

				Medida medida = new Medida();
				Medicao medicao = new Medicao();

				medicao.setDataMedicao(data);
				medicao.setCodigoPerfil(perfil.getCodigo());
				medicao.setValor(Double.parseDouble(editPeso.getText().toString()));
				medida.setCodigo(controleMed.buscarMedida("Peso", "a"));
				medicao.setCodigoMedida(medida.getCodigo());

				listaMedicao.add(medicao);

			}

			//Cintura
			if(!editCintura.getText().toString().equalsIgnoreCase("")){
				Medida medida = new Medida();
				Medicao medicao = new Medicao();


				medicao.setDataMedicao(data);
				medicao.setCodigoPerfil(perfil.getCodigo());
				medicao.setValor(Double.parseDouble(editCintura.getText().toString()));
				medida.setCodigo(controleMed.buscarMedida("Cintura", "a"));
				medicao.setCodigoMedida(medida.getCodigo());

				listaMedicao.add(medicao);
			}

			//Quadril
			if(!editQuadril.getText().toString().equalsIgnoreCase("")){
				Medida medida = new Medida();
				Medicao medicao = new Medicao();

				medicao.setDataMedicao(data);
				medicao.setCodigoPerfil(perfil.getCodigo());
				medicao.setValor(Double.parseDouble(editQuadril.getText().toString()));
				medida.setCodigo(controleMed.buscarMedida("Quadril", "a"));
				medicao.setCodigoMedida(medida.getCodigo());

				listaMedicao.add(medicao);

			}

			//Braço Direito
			if(!editBracoDir.getText().toString().equalsIgnoreCase("")){
				Medida medida = new Medida();
				Medicao medicao = new Medicao();

				medicao.setDataMedicao(data);
				medicao.setCodigoPerfil(perfil.getCodigo());
				medicao.setValor(Double.parseDouble(editBracoDir.getText().toString()));
				medida.setCodigo(controleMed.buscarMedida("Braco", "d"));
				medicao.setCodigoMedida(medida.getCodigo());

				listaMedicao.add(medicao);
			}

			//Braço Esquerdo
			if(!editBracoEsq.getText().toString().equalsIgnoreCase("")){
				Medida medida = new Medida();
				Medicao medicao = new Medicao();

				medicao.setDataMedicao(data);
				medicao.setCodigoPerfil(perfil.getCodigo());
				medicao.setValor(Double.parseDouble(editBracoEsq.getText().toString()));
				medida.setCodigo(controleMed.buscarMedida("Braco", "e"));
				medicao.setCodigoMedida(medida.getCodigo());

				listaMedicao.add(medicao);
			}

			//Peito
			if(!editPeito.getText().toString().equalsIgnoreCase("")){
				Medida medida = new Medida();
				Medicao medicao = new Medicao();

				medicao.setDataMedicao(data);
				medicao.setCodigoPerfil(perfil.getCodigo());
				medicao.setValor(Double.parseDouble(editPeito.getText().toString()));
				medida.setCodigo(controleMed.buscarMedida("Peito", "a"));
				medicao.setCodigoMedida(medida.getCodigo());

				listaMedicao.add(medicao);
			}

			//Coxa Direito
			if(!editCoxaDir.getText().toString().equalsIgnoreCase("")){
				Medida medida = new Medida();
				Medicao medicao = new Medicao();

				medicao.setDataMedicao(data);
				medicao.setCodigoPerfil(perfil.getCodigo());
				medicao.setValor(Double.parseDouble(editCoxaDir.getText().toString()));
				medida.setCodigo(controleMed.buscarMedida("Coxa", "d"));
				medicao.setCodigoMedida(medida.getCodigo());

				listaMedicao.add(medicao);
			}

			//Coxa Esquerda

			if(!editCoxaEsq.getText().toString().equalsIgnoreCase("")){
				Medida medida = new Medida();
				Medicao medicao = new Medicao();

				medicao.setDataMedicao(data);
				medicao.setCodigoPerfil(perfil.getCodigo());
				medicao.setValor(Double.parseDouble(editCoxaEsq.getText().toString()));
				medida.setCodigo(controleMed.buscarMedida("Coxa", "e"));
				medicao.setCodigoMedida(medida.getCodigo());

				listaMedicao.add(medicao);
			}

			//Panturrilha Direita

			if(!editPantuDir.getText().toString().equalsIgnoreCase("")){
				Medida medida = new Medida();
				Medicao medicao = new Medicao();

				medicao.setDataMedicao(data);
				medicao.setCodigoPerfil(perfil.getCodigo());
				medicao.setValor(Double.parseDouble(editPantuDir.getText().toString()));
				medida.setCodigo(controleMed.buscarMedida("Panturrilha", "d"));
				medicao.setCodigoMedida(medida.getCodigo());


				listaMedicao.add(medicao);
			}

			//Panturrilha Esuerda

			if(!editPantuEsq.getText().toString().equalsIgnoreCase("")){
				Medida medida = new Medida();
				Medicao medicao = new Medicao();

				medicao.setDataMedicao(data);
				medicao.setCodigoPerfil(perfil.getCodigo());
				medicao.setValor(Double.parseDouble(editPantuEsq.getText().toString()));
				medida.setCodigo(controleMed.buscarMedida("Panturrilha", "e"));
				medicao.setCodigoMedida(medida.getCodigo());


				listaMedicao.add(medicao);
			}


			Toast.makeText(this,controleMed.adicionarMedicao(listaMedicao),
					Toast.LENGTH_LONG).show();


		}else{

			Toast.makeText(this,"Primeiro crie seu Perfil !",
					Toast.LENGTH_LONG).show();
		}
	}


	public void carregarCampos(List<Medicao> medicoes){
		ControleMedida controle = new ControleMedida();
		for(Medicao m : medicoes){

			//Altura
			if(m.getCodigoMedida() == controle.buscarMedida("Altura", "a") ){
				editAltura.setText(Double.toString(m.getValor()));
			}
			//Peso
			if(m.getCodigoMedida() == controle.buscarMedida("Peso", "a")){
				editPeso.setText(Double.toString(m.getValor()));
			}
			//Cintura
			if(m.getCodigoMedida() == controle.buscarMedida("Cintura", "a")){
				editCintura.setText(Double.toString(m.getValor()));
			}
			//Quadril
			if(m.getCodigoMedida() == controle.buscarMedida("Quadril", "a")){
				editQuadril.setText(Double.toString(m.getValor()));
			}
			//Braço Direito
			if(m.getCodigoMedida() == controle.buscarMedida("Braco", "d")){
				editBracoDir.setText(Double.toString(m.getValor()));
			}
			//Braço Esquerdo
			if(m.getCodigoMedida() == controle.buscarMedida("Braco", "e")){
				editBracoEsq.setText(Double.toString(m.getValor()));
			}
			//Peito
			if(m.getCodigoMedida() == controle.buscarMedida("Peito", "a")){
				editPeito.setText(Double.toString(m.getValor()));
			}
			//Coxa Direito
			if(m.getCodigoMedida() == controle.buscarMedida("Coxa", "d")){
				editCoxaDir.setText(Double.toString(m.getValor()));
			}
			//Coxa Esquerda
			if(m.getCodigoMedida() == controle.buscarMedida("Coxa", "e")){
				editCoxaEsq.setText(Double.toString(m.getValor()));
			}
			//Panturrilha Direita
			if(m.getCodigoMedida() == controle.buscarMedida("Panturrilha", "d")){
				editPantuDir.setText(Double.toString(m.getValor()));
			}
			//Panturrilha Esuerda
			if(m.getCodigoMedida() == controle.buscarMedida("Panturrilha", "e")){
				editPantuEsq.setText(Double.toString(m.getValor()));
			}

		}
	}

	public void iniciarBotao(List<Medicao> lista){

		if( lista.size() == 0 ){

			btnAlterar.setEnabled(false);
			btnNovo.setEnabled(true);
			btnSalvar.setEnabled(false);
			btnCancelar.setEnabled(false);
		}else{
			btnAlterar.setEnabled(true);
			btnNovo.setEnabled(true);
			btnSalvar.setEnabled(false);
			btnCancelar.setEnabled(false);

		}


	}

	public List<Medicao> ObterUltimosValores(List<Medicao> medicoes){
		List<Medicao> listaAux = new ArrayList<Medicao>();
		ControleMedida controle = new ControleMedida();
		Medicao mAltura = new Medicao();
		Medicao mPeso = new Medicao();
		Medicao mCintura = new Medicao();
		Medicao mBracoE = new Medicao();
		Medicao mPantuE = new Medicao();
		Medicao mQuadril = new Medicao();
		Medicao mPeito = new Medicao();
		Medicao mCoxaD = new Medicao();
		Medicao mBracoD = new Medicao();
		Medicao mCoxaE = new Medicao();
		Medicao mPantuD = new Medicao();



		for(Medicao m : medicoes){

			//Altura
			if(m.getCodigoMedida() == controle.buscarMedida("Altura", "a") ){
				mAltura.setValor(Double.parseDouble(editAltura.getText().toString()));
				mAltura.setCodigio(m.getCodigo());

			}
			//Peso
			if(m.getCodigoMedida() == controle.buscarMedida("Peso", "a")){
				mPeso.setValor(Double.parseDouble(editPeso.getText().toString()));
				mPeso.setCodigio(m.getCodigo());

			}
			//Cintura
			if(m.getCodigoMedida() == controle.buscarMedida("Cintura", "a")){
				mCintura.setValor(Double.parseDouble(editCintura.getText().toString()));
				mCintura.setCodigio(m.getCodigo());
			}
			//Quadril
			if(m.getCodigoMedida() == controle.buscarMedida("Quadril", "a")){
				mQuadril.setValor(Double.parseDouble(editQuadril.getText().toString()));
				mQuadril.setCodigio(m.getCodigo());
			}
			//Braço Direito
			if(m.getCodigoMedida() == controle.buscarMedida("Braco", "d")){
				mBracoD.setValor(Double.parseDouble(editBracoDir.getText().toString()));
				mBracoD.setCodigio(m.getCodigo());
			}
			//Braço Esquerdo
			if(m.getCodigoMedida() == controle.buscarMedida("Braco", "e")){
				mBracoE.setValor(Double.parseDouble(editBracoEsq.getText().toString()));
				mBracoE.setCodigio(m.getCodigo());
			}
			//Peito
			if(m.getCodigoMedida() == controle.buscarMedida("Peito", "a")){
				mPeito.setValor(Double.parseDouble(editPeito.getText().toString()));
				mPeito.setCodigio(m.getCodigo());
			}
			//Coxa Direito
			if(m.getCodigoMedida() == controle.buscarMedida("Coxa", "d")){
				mCoxaD.setValor(Double.parseDouble(editCoxaDir.getText().toString()));
				mCoxaD.setCodigio(m.getCodigo());
			}
			//Coxa Esquerda
			if(m.getCodigoMedida() == controle.buscarMedida("Coxa", "e")){
				mCoxaE.setValor(Double.parseDouble(editCoxaEsq.getText().toString()));
				mCoxaE.setCodigio(m.getCodigo());
			}
			//Panturrilha Direita
			if(m.getCodigoMedida() == controle.buscarMedida("Panturrilha", "d")){
				mPantuD.setValor(Double.parseDouble(editPantuDir.getText().toString()));
				mPantuD.setCodigio(m.getCodigo());
			}
			//Panturrilha Esuerda
			if(m.getCodigoMedida() == controle.buscarMedida("Panturrilha", "e")){
				mPantuE.setValor(Double.parseDouble(editPantuEsq.getText().toString()));
				mPantuE.setCodigio(m.getCodigo());
			}

		}

		if(mAltura.getCodigo() != 0){

			listaAux.add(mAltura);		
		}
		if(mPeso.getCodigo() != 0){

			listaAux.add(mPeso);	
		}
		if(mCintura.getCodigo() != 0){

			listaAux.add(mCintura);	
		}
		if(mBracoE.getCodigo() != 0){

			listaAux.add(mBracoE);	
		}
		if(mPantuE.getCodigo() != 0){

			listaAux.add(mPantuE);	
		}
		if(mQuadril.getCodigo() != 0){

			listaAux.add(mQuadril);	
		}
		if(mPeito.getCodigo() != 0){

			listaAux.add(mPeito);		
		}
		if(mCoxaD.getCodigo() != 0){

			listaAux.add(mCoxaD);	
		}
		if(mBracoD.getCodigo() != 0){

			listaAux.add(mBracoD);	
		}
		if(mCoxaE.getCodigo() != 0){

			listaAux.add(mCoxaE);	
		}
		if(mPantuD.getCodigo() != 0){

			listaAux.add(mPantuD);	
		}

		return listaAux;
	}
}