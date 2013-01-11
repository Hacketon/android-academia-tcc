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

	private Button btnSalvar;
	private Button btnAlterar;
	private Button btnCancelar;
	private Button btnNovo;


	ControleMedida controleMed = new ControleMedida();
	ControlePerfil controle = new ControlePerfil();
	Perfil perfil =  controle.buscarPerfil();

	List<Medicao> lista = controleMed.buscarMedicao(perfil.getCodigo());
	int x = 0;
	java.util.Date data = new java.util.Date(); 
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String dataFormat = sdf.format(data);  
	

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


		
		if(perfil != null){
//			lista = controleMed.buscarMedicao(perfil.getCodigo());
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

			lista = controleMed.buscarMedicao(perfil.getCodigo());
			List<Medicao> listaUltimosValores = new ArrayList<Medicao>();
			listaUltimosValores = ObterUltimosValores(lista);
			
			if(x==1){
								
				criaMedida(perfil, listaUltimosValores);
				carregarCampos(lista);

			}else if(x==2){

				Toast.makeText(this,controleMed.alterarUltimasMedicoes(listaUltimosValores),
						Toast.LENGTH_LONG).show();

			}


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
			x = 1;
			btnAlterar.setEnabled(false);
			btnNovo.setEnabled(false);
			btnSalvar.setEnabled(true);
			btnCancelar.setEnabled(true);

			desbloquearCampos();

			Toast.makeText(this,"Medidas serão registradas na data: \n " + dataFormat, Toast.LENGTH_SHORT).show();

			break;

		case R.id.btn_alterar:
			x =2;
			btnAlterar.setEnabled(false);
			btnNovo.setEnabled(false);
			btnSalvar.setEnabled(true);
			btnCancelar.setEnabled(true);
			//metodo para bloquear campos em branco 

//			carregarCampos(lista);
			break;
		}
	}

	public void criaMedida(Perfil perfil , List<Medicao> listaUltimosValores){

		List<Medicao> listaMedicao = new ArrayList<Medicao>();
		List<Medicao> listaAtualiza = new ArrayList<Medicao>();
		Double valor = null;


		if(perfil != null){

			// se os valores já foram digitados na mesma data somente irá atualizar o grupomuscular 
			boolean mAltura = false;
			boolean mPeso= false;
			boolean mCintura= false;
			boolean mQuadril= false;
			boolean mBracoD= false;
			boolean mBracoE= false;
			boolean mPeito= false;
			boolean mCoxaD= false;
			boolean mCoxaE= false;
			boolean mPantuD= false;
			boolean mPantuE= false;

 

			for(Medicao m : listaUltimosValores){
				
				String dataFormat2 = sdf.format(m.getDataMedicao());
				
				//Altura
//				if(m.getCodigoMedida() == controleMed.buscarMedida("Altura", "a")){
				if(m.getCodigoMedida() == 1){
					valor = Double.parseDouble(editAltura.getText().toString());
					if(dataFormat.equalsIgnoreCase(dataFormat2)){
						
						m.setValor(valor);
						listaAtualiza.add(m);
						mAltura = true;
					}
				}
				//Peso
//				if(m.getCodigoMedida() == controleMed.buscarMedida("Peso", "a")){
				if(m.getCodigoMedida() == 2){
					valor = Double.parseDouble(editPeso.getText().toString());
//					String dataFormat2 = sdf.format(m.getDataMedicao()); 
					if(dataFormat.equalsIgnoreCase(dataFormat2)){
						m.setValor(valor);
						listaAtualiza.add(m);
						mPeso = true;
					}
				}				

				//Cintura
//				if(m.getCodigoMedida() == controleMed.buscarMedida("Cintura", "a")){
				if(m.getCodigoMedida() == 3){
					valor = Double.parseDouble(editCintura.getText().toString());
//					String dataFormat2 = sdf.format(m.getDataMedicao()); 
					if(dataFormat.equalsIgnoreCase(dataFormat2)){
						m.setValor(valor);
						listaAtualiza.add(m);
						mCintura = true;
					}
				}

				//Quadril
//				if(m.getCodigoMedida() == controleMed.buscarMedida("Quadril", "a")){
				if(m.getCodigoMedida() == 4){
					valor = Double.parseDouble(editQuadril.getText().toString());
//					String dataFormat2 = sdf.format(m.getDataMedicao()); 
					if(dataFormat.equalsIgnoreCase(dataFormat2)){
						m.setValor(valor);
						listaAtualiza.add(m);
						mQuadril = true;
					}
				}				

				//Peito
//				if(m.getCodigoMedida() == controleMed.buscarMedida("Peito", "a")){
				if(m.getCodigoMedida() == 5){
					valor = Double.parseDouble(editPeito.getText().toString());
//					String dataFormat2 = sdf.format(m.getDataMedicao()); 
					if(dataFormat.equalsIgnoreCase(dataFormat2)){
						m.setValor(valor);
						listaAtualiza.add(m);
						mPeito = true;
					}
				}
				
				//Braço Direito

//				if(m.getCodigoMedida() == controleMed.buscarMedida("Braco", "d")){
				if(m.getCodigoMedida() == 6){
					valor = Double.parseDouble(editBracoDir.getText().toString());
//					String dataFormat2 = sdf.format(m.getDataMedicao()); 
					if(dataFormat.equalsIgnoreCase(dataFormat2)){
						m.setValor(valor);
						listaAtualiza.add(m);
						mBracoD = true;
					}
				}

				//Braço Esquerdo
//				if(m.getCodigoMedida() == controleMed.buscarMedida("Braco", "e")){
				if(m.getCodigoMedida() == 7){
					valor = Double.parseDouble(editBracoEsq.getText().toString());
//					String dataFormat2 = sdf.format(m.getDataMedicao()); 
					if(dataFormat.equalsIgnoreCase(dataFormat2)){
						m.setValor(valor);
						listaAtualiza.add(m);
						mBracoE = true;
					}
				}
				
				//Coxa Direito
//				if(m.getCodigoMedida() == controleMed.buscarMedida("Coxa", "d")){
				if(m.getCodigoMedida() == 8){
					valor = Double.parseDouble(editCoxaDir.getText().toString());
//					String dataFormat2 = sdf.format(m.getDataMedicao()); 
					if(dataFormat.equalsIgnoreCase(dataFormat2)){
						m.setValor(valor);
						listaAtualiza.add(m);
						mCoxaD = true;
					}
				}
				//Coxa Esquerda
//				if(m.getCodigoMedida() == controleMed.buscarMedida("Coxa", "e")){
				if(m.getCodigoMedida() == 9){
					valor = Double.parseDouble(editCoxaEsq.getText().toString());
//					String dataFormat2 = sdf.format(m.getDataMedicao()); 
					if(dataFormat.equalsIgnoreCase(dataFormat2)){
						m.setValor(valor);
						listaAtualiza.add(m);
						mCoxaE = true;
					}
				}
				//Panturrilha Direita

//				if(m.getCodigoMedida() == controleMed.buscarMedida("Panturrilha", "d")){
				if(m.getCodigoMedida() == 10){
					valor = Double.parseDouble(editPantuDir.getText().toString());
//					String dataFormat2 = sdf.format(m.getDataMedicao()); 
					if(dataFormat.equalsIgnoreCase(dataFormat2)){
						m.setValor(valor);
						listaAtualiza.add(m);
						mPantuD = true;
					}
				}
				//Panturrilha Esuerda

//				if(m.getCodigoMedida() == controleMed.buscarMedida("Panturrilha", "e")){
				if(m.getCodigoMedida() == 11){
					valor = Double.parseDouble(editPantuEsq.getText().toString());
//					String dataFormat2 = sdf.format(m.getDataMedicao()); 
					if(dataFormat.equalsIgnoreCase(dataFormat2)){
						m.setValor(valor);
						listaAtualiza.add(m);
						mPantuE = true;
					}
				}

			}




			// se o valor ainda não foi digita no dia ele criará uma nova medição para o grupo

			//Altura
			if(!editAltura.getText().toString().equalsIgnoreCase("")){
				if(mAltura == false){
//					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					medicao.setValor(Double.parseDouble(editAltura.getText().toString()));
					medicao.setDataMedicao(data);
					medicao.setCodigoPerfil(perfil.getCodigo());
//					medida.setCodigo(controleMed.buscarMedida("Altura", "a"));
//					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoMedida(1);

					listaMedicao.add(medicao);
				}
			}


			//Peso
			if(!editPeso.getText().toString().equalsIgnoreCase("")){
				if(mPeso == false){
//					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					medicao.setDataMedicao(data);
					medicao.setCodigoPerfil(perfil.getCodigo());
					medicao.setValor(Double.parseDouble(editPeso.getText().toString()));
//					medida.setCodigo(controleMed.buscarMedida("Peso", "a"));
//					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoMedida(2);
					
					listaMedicao.add(medicao);
				}
			}


			//Cintura
			if(!editCintura.getText().toString().equalsIgnoreCase("")){
				if(mCintura == false){
//					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					medicao.setDataMedicao(data);
					medicao.setCodigoPerfil(perfil.getCodigo());
					medicao.setValor(Double.parseDouble(editCintura.getText().toString()));
//					medida.setCodigo(controleMed.buscarMedida("Cintura", "a"));
//					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoMedida(3);

					listaMedicao.add(medicao);
				}
			}

			//Quadril
			if(!editQuadril.getText().toString().equalsIgnoreCase("")){
				if(mQuadril == false){
//					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					medicao.setDataMedicao(data);
					medicao.setCodigoPerfil(perfil.getCodigo());
					medicao.setValor(Double.parseDouble(editQuadril.getText().toString()));
//					medida.setCodigo(controleMed.buscarMedida("Quadril", "a"));
//					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoMedida(4);

					listaMedicao.add(medicao);
				}
			}
			
			//Peito
			if(!editPeito.getText().toString().equalsIgnoreCase("")){
				if(mPeito == false){
//					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					medicao.setDataMedicao(data);
					medicao.setCodigoPerfil(perfil.getCodigo());
					medicao.setValor(Double.parseDouble(editPeito.getText().toString()));
//					medida.setCodigo(controleMed.buscarMedida("Peito", "a"));
//					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoMedida(5);
					
					listaMedicao.add(medicao);
				}
			}


			//Braço Direito
			if(!editBracoDir.getText().toString().equalsIgnoreCase("")){
				if(mBracoD == false){
//					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					medicao.setDataMedicao(data);
					medicao.setCodigoPerfil(perfil.getCodigo());
					medicao.setValor(Double.parseDouble(editBracoDir.getText().toString()));
//					medida.setCodigo(controleMed.buscarMedida("Braco", "d"));
//					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoMedida(6);
					
					listaMedicao.add(medicao);
				}
			}

			//Braço Esquerdo
			if(!editBracoEsq.getText().toString().equalsIgnoreCase("")){
				if(mBracoE == false){
//					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					medicao.setDataMedicao(data);
					medicao.setCodigoPerfil(perfil.getCodigo());
					medicao.setValor(Double.parseDouble(editBracoEsq.getText().toString()));
//					medida.setCodigo(controleMed.buscarMedida("Braco", "e"));
//					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoMedida(7);

					listaMedicao.add(medicao);
				}
			}


			//Coxa Direito
			if(!editCoxaDir.getText().toString().equalsIgnoreCase("")){
				if(mCoxaD == false){
//					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					medicao.setDataMedicao(data);
					medicao.setCodigoPerfil(perfil.getCodigo());
					medicao.setValor(Double.parseDouble(editCoxaDir.getText().toString()));
//					medida.setCodigo(controleMed.buscarMedida("Coxa", "d"));
//					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoMedida(8);

					listaMedicao.add(medicao);
				}
			}
			//Coxa Esquerda

			if(!editCoxaEsq.getText().toString().equalsIgnoreCase("")){
				if(mCoxaE == false){
//					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					medicao.setDataMedicao(data);
					medicao.setCodigoPerfil(perfil.getCodigo());
					medicao.setValor(Double.parseDouble(editCoxaEsq.getText().toString()));
//					medida.setCodigo(controleMed.buscarMedida("Coxa", "e"));
//					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoMedida(9);

					listaMedicao.add(medicao);
				}
			}
			//Panturrilha Direita

			if(!editPantuDir.getText().toString().equalsIgnoreCase("")){
				if(mPantuD == false){
//					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					medicao.setDataMedicao(data);
					medicao.setCodigoPerfil(perfil.getCodigo());
					medicao.setValor(Double.parseDouble(editPantuDir.getText().toString()));
//					medida.setCodigo(controleMed.buscarMedida("Panturrilha", "d"));
//					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoMedida(10);

					listaMedicao.add(medicao);
				}
			}

			//Panturrilha Esuerda

			if(!editPantuEsq.getText().toString().equalsIgnoreCase("")){
				if(mPantuE == false){
//					Medida medida = new Medida();
					Medicao medicao = new Medicao();
					medicao.setDataMedicao(data);
					medicao.setCodigoPerfil(perfil.getCodigo());
					medicao.setValor(Double.parseDouble(editPantuEsq.getText().toString()));
//					medida.setCodigo(controleMed.buscarMedida("Panturrilha", "e"));
//					medicao.setCodigoMedida(medida.getCodigo());
					medicao.setCodigoMedida(11);


					listaMedicao.add(medicao);
				}
			}

			if(listaMedicao.size() != 0){
				Toast.makeText(this,controleMed.adicionarMedicao(listaMedicao),
						Toast.LENGTH_SHORT).show();
			}
		
			if(listaAtualiza.size() != 0){
				Toast.makeText(this,controleMed.alterarUltimasMedicoes(listaAtualiza),
						Toast.LENGTH_SHORT).show();
			}

		}else{


			Toast.makeText(this,"Primeiro crie seu Perfil !",
					Toast.LENGTH_LONG).show();
		}

	}



	public void carregarCampos(List<Medicao> medicoes){
//		ControleMedida controle = new ControleMedida();

		for(Medicao m : medicoes){

			//Altura
//			if(m.getCodigoMedida() == controle.buscarMedida("Altura", "a") ){
			if(m.getCodigoMedida() == 1 ){
				editAltura.setText(Double.toString(m.getValor()));
			}
			//Peso
//			if(m.getCodigoMedida() == controle.buscarMedida("Peso", "a")){
			if(m.getCodigoMedida() == 2 ){
				editPeso.setText(Double.toString(m.getValor()));
			}
			//Cintura
//			if(m.getCodigoMedida() == controle.buscarMedida("Cintura", "a")){
			if(m.getCodigoMedida() == 3 ){

				editCintura.setText(Double.toString(m.getValor()));
			}
			//Quadril
//			if(m.getCodigoMedida() == controle.buscarMedida("Quadril", "a")){
			if(m.getCodigoMedida() == 4 ){

				editQuadril.setText(Double.toString(m.getValor()));
			}
			
			//Peito
//			if(m.getCodigoMedida() == controle.buscarMedida("Peito", "a")){
			
			if(m.getCodigoMedida() == 5){
				editPeito.setText(Double.toString(m.getValor()));
			}
			
			//Braço Direito
//			if(m.getCodigoMedida() == controle.buscarMedida("Braco", "d")){
			if(m.getCodigoMedida() == 6){

				editBracoDir.setText(Double.toString(m.getValor()));
			}
			//Braço Esquerdo
//			if(m.getCodigoMedida() == controle.buscarMedida("Braco", "e")){
			if(m.getCodigoMedida() == 7){
				editBracoEsq.setText(Double.toString(m.getValor()));
			}
			
			//Coxa Direito
//			if(m.getCodigoMedida() == controle.buscarMedida("Coxa", "d")){
			if(m.getCodigoMedida() == 8){
				editCoxaDir.setText(Double.toString(m.getValor()));
			}
			//Coxa Esquerda
//			if(m.getCodigoMedida() == controle.buscarMedida("Coxa", "e")){
			if(m.getCodigoMedida() == 9){
				editCoxaEsq.setText(Double.toString(m.getValor()));
			}
			//Panturrilha Direita
//			if(m.getCodigoMedida() == controle.buscarMedida("Panturrilha", "d")){
			if(m.getCodigoMedida() == 10){
				editPantuDir.setText(Double.toString(m.getValor()));
			}
//			//Panturrilha Esuerda
//			if(m.getCodigoMedida() == controle.buscarMedida("Panturrilha", "e")){
			if(m.getCodigoMedida() == 11){
				editPantuEsq.setText(Double.toString(m.getValor()));
			}


		}
		//bloquear campos nulos


		if(editAltura.getText().toString().equalsIgnoreCase("")){
			editAltura.setEnabled(false);

		}
		if(editPeso.getText().toString().equalsIgnoreCase("")){
			editPeso.setEnabled(false);

		}
		if(editCintura.getText().toString().equalsIgnoreCase("")){
			editCintura.setEnabled(false);

		}
		if(editQuadril.getText().toString().equalsIgnoreCase("")){
			editQuadril.setEnabled(false);

		}
		if(editBracoDir.getText().toString().equalsIgnoreCase("")){
			editBracoDir.setEnabled(false);

		}
		if(editBracoEsq.getText().toString().equalsIgnoreCase("")){
			editBracoEsq.setEnabled(false);

		}
		if(editPeito.getText().toString().equalsIgnoreCase("")){
			editPeito.setEnabled(false);

		}
		if(editCoxaDir.getText().toString().equalsIgnoreCase("")){
			editCoxaDir.setEnabled(false);

		}
		if(editCoxaEsq.getText().toString().equalsIgnoreCase("")){
			editCoxaEsq.setEnabled(false);

		}
		if(editPantuDir.getText().toString().equalsIgnoreCase("")){
			editPantuDir.setEnabled(false);

		}
		if(editPantuEsq.getText().toString().equalsIgnoreCase("")){
			editPantuEsq.setEnabled(false);

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
				mAltura.setDataMedicao(m.getDataMedicao());
				mAltura.setCodigoMedida(m.getCodigoMedida());

			}
			//Peso
			if(m.getCodigoMedida() == controle.buscarMedida("Peso", "a")){
				mPeso.setValor(Double.parseDouble(editPeso.getText().toString()));
				mPeso.setCodigio(m.getCodigo());
				mPeso.setDataMedicao(m.getDataMedicao());
				mPeso.setCodigoMedida(m.getCodigoMedida());
			}
			//Cintura
			if(m.getCodigoMedida() == controle.buscarMedida("Cintura", "a")){
				mCintura.setValor(Double.parseDouble(editCintura.getText().toString()));
				mCintura.setCodigio(m.getCodigo());
				mCintura.setDataMedicao(m.getDataMedicao());
				mCintura.setCodigoMedida(m.getCodigoMedida());

			}
			//Quadril
			if(m.getCodigoMedida() == controle.buscarMedida("Quadril", "a")){
				mQuadril.setValor(Double.parseDouble(editQuadril.getText().toString()));
				mQuadril.setCodigio(m.getCodigo());
				mQuadril.setDataMedicao(m.getDataMedicao());
				mQuadril.setCodigoMedida(m.getCodigoMedida());
			}
			//Braço Direito
			if(m.getCodigoMedida() == controle.buscarMedida("Braco", "d")){
				mBracoD.setValor(Double.parseDouble(editBracoDir.getText().toString()));
				mBracoD.setCodigio(m.getCodigo());
				mBracoD.setDataMedicao(m.getDataMedicao());
				mBracoD.setCodigoMedida(m.getCodigoMedida());
			}
			//Braço Esquerdo
			if(m.getCodigoMedida() == controle.buscarMedida("Braco", "e")){
				mBracoE.setValor(Double.parseDouble(editBracoEsq.getText().toString()));
				mBracoE.setCodigio(m.getCodigo());
				mBracoE.setDataMedicao(m.getDataMedicao());
				mBracoE.setCodigoMedida(m.getCodigoMedida());
			}
			//Peito
			if(m.getCodigoMedida() == controle.buscarMedida("Peito", "a")){
				mPeito.setValor(Double.parseDouble(editPeito.getText().toString()));
				mPeito.setCodigio(m.getCodigo());
				mPeito.setDataMedicao(m.getDataMedicao());
				mPeito.setCodigoMedida(m.getCodigoMedida());
			}
			//Coxa Direito
			if(m.getCodigoMedida() == controle.buscarMedida("Coxa", "d")){
				mCoxaD.setValor(Double.parseDouble(editCoxaDir.getText().toString()));
				mCoxaD.setCodigio(m.getCodigo());
				mCoxaD.setDataMedicao(m.getDataMedicao());
				mCoxaD.setCodigoMedida(m.getCodigoMedida());
			}
			//Coxa Esquerda
			if(m.getCodigoMedida() == controle.buscarMedida("Coxa", "e")){
				mCoxaE.setValor(Double.parseDouble(editCoxaEsq.getText().toString()));
				mCoxaE.setCodigio(m.getCodigo());
				mCoxaE.setDataMedicao(m.getDataMedicao());
				mCoxaE.setCodigoMedida(m.getCodigoMedida());
			}
			//Panturrilha Direita
			if(m.getCodigoMedida() == controle.buscarMedida("Panturrilha", "d")){
				mPantuD.setValor(Double.parseDouble(editPantuDir.getText().toString()));
				mPantuD.setCodigio(m.getCodigo());
				mPantuD.setDataMedicao(m.getDataMedicao());
				mPantuD.setCodigoMedida(m.getCodigoMedida());
			}
			//Panturrilha Esuerda
			if(m.getCodigoMedida() == controle.buscarMedida("Panturrilha", "e")){
				mPantuE.setValor(Double.parseDouble(editPantuEsq.getText().toString()));
				mPantuE.setCodigio(m.getCodigo());
				mPantuE.setDataMedicao(m.getDataMedicao());
				mPantuE.setCodigoMedida(m.getCodigoMedida());
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

	public void bloquearCampoNull(){

	}

	public void desbloquearCampos(){

		editAltura.setEnabled(true);
		editPeso.setEnabled(true);
		editCintura.setEnabled(true);
		editQuadril.setEnabled(true);
		editBracoDir.setEnabled(true);
		editBracoEsq.setEnabled(true);
		editPeito.setEnabled(true);
		editCoxaDir.setEnabled(true);
		editCoxaEsq.setEnabled(true);
		editPantuDir.setEnabled(true);
		editPantuEsq.setEnabled(true);

	}
}