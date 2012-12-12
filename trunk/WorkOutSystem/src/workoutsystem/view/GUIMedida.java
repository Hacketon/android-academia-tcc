package workoutsystem.view;

import java.util.Date;

import workoutsystem.control.ControleMedida;
import workoutsystem.control.ControleUsuario;
import workoutsystem.dao.IMedidaDao;
import workoutsystem.dao.IPerfilDao;
import workoutsystem.dao.IUsuarioDao;
import workoutsystem.dao.MedidaDao;
import workoutsystem.dao.PerfilDao;
import workoutsystem.dao.UsuarioDao;
import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;
import workoutsystem.model.Perfil;
import workoutsystem.model.Usuario;
import android.app.Activity;
import android.os.Bundle;
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


	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.medidas);
		criarTab();

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

		textAltura = (TextView) findViewById(R.id.tv_altura);
		textPeso = (TextView) findViewById(R.id.tv_peso);
		textCintura = (TextView) findViewById(R.id.tv_cintura);
		textQuadril = (TextView) findViewById(R.id.tv_quadril);
		textBracoDir = (TextView) findViewById(R.id.tv_braco);
		textBracoEsq = (TextView) findViewById(R.id.tv_braco);
		textPeito = (TextView) findViewById(R.id.tv_peito);
		textCoxaDir = (TextView) findViewById(R.id.tv_coxa);
		textCoxaEsq = (TextView) findViewById(R.id.tv_coxa);
		textPantuDir = (TextView) findViewById(R.id.tv_panturilha);
		textPantuEsq = (TextView) findViewById(R.id.tv_panturilha);


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

		switch (v.getId()){
		case R.id.btn_salvar:
			criaMedida();
			break;

		case R.id.btn_cancelar:

			break;
		}
	}

	public void criaMedida(){
		Medida medida = new Medida();
		Medicao medicao = new Medicao();
		ControleMedida controleMed = new ControleMedida();


		//Altura
		if(editAltura.getText() != null){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			Usuario usuario = new Usuario();
			Date data = new Date();
			Perfil perfil = new Perfil();
			IPerfilDao daoPerf = new PerfilDao();
			
			perfil = daoPerf.buscarPerfil();
			
			
			medicao.setValor(Double.parseDouble(editAltura.getText().toString()));
			
			java.sql.Date data1 = new java.sql.Date( data.getDate());
			medicao.setDataMedicao(data1);
			
			medicao.setCodigoUsuario(1);
			//carregar perfil
			medicao.setCodigoPerfil(1);
			//medida
			medida.setCodigo(controleMed.buscarMedida("Altura", "a"));
			medicao.setCodigoMedida(1);
			
			//adicionar no banco
			Toast.makeText(this, "Altura" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_LONG).show();

		}

//		//Peso
//		if(editPeso.getText()!= null){
//			//data, codigoPerfil e CodigoUsuario
//			carregarMedicao();
//			medicao.setValor(Double.parseDouble(editPeso.getText().toString()));
//			//medida
//			medida.setCodigo(controleMed.buscarMedida("Peso", "a"));
//			medicao.setCodigoMedida(medida);
//			//adicionar no banco
//			controleMed.adicionarMedicao(medicao);
//
//		}
//
//		//Cintura
//		if(editCintura.getText() != null){
//			//data, codigoPerfil e CodigoUsuario
//			carregarMedicao();
//			medicao.setValor(Double.parseDouble(editCintura.getText().toString()));
//			//medida
//			medida.setCodigo(controleMed.buscarMedida("Cintura", "a"));
//			medicao.setCodigoMedida(medida);
//			//adicionar no banco
//			controleMed.adicionarMedicao(medicao);
//
//		}
//
//		//Quadril
//		if(editQuadril.getText() != null){
//			//data, codigoPerfil e CodigoUsuario
//			carregarMedicao();
//			medicao.setValor(Double.parseDouble(editQuadril.getText().toString()));
//			//medida
//			medida.setCodigo(controleMed.buscarMedida("Quadril", "a"));
//			medicao.setCodigoMedida(medida);
//			//adicionar no banco
//			controleMed.adicionarMedicao(medicao);
//
//		}
//
//		//Braço Direito
//		if(editBracoDir.getText()!=null){
//			//data, codigoPerfil e CodigoUsuario
//			carregarMedicao();
//			medicao.setValor(Double.parseDouble(editBracoDir.getText().toString()));
//			//medida
//			medida.setCodigo(controleMed.buscarMedida("Braco", "d"));
//			medicao.setCodigoMedida(medida);
//			//adicionar no banco
//			controleMed.adicionarMedicao(medicao);
//		}
//
//		//Braço Esquerdo
//		if(editBracoEsq.getText()!=null){
//			//data, codigoPerfil e CodigoUsuario
//			carregarMedicao();
//			medicao.setValor(Double.parseDouble(editBracoEsq.getText().toString()));
//			//medida
//			medida.setCodigo(controleMed.buscarMedida("Braco", "e"));
//			medicao.setCodigoMedida(medida);
//			//adicionar no banco
//			controleMed.adicionarMedicao(medicao);
//		}
//
//		//Peito
//		if(editPeito.getText()!=null){
//			//data, codigoPerfil e CodigoUsuario
//			carregarMedicao();
//			medicao.setValor(Double.parseDouble(editPeito.getText().toString()));
//			//medida
//			medida.setCodigo(controleMed.buscarMedida("Peito", "a"));
//			medicao.setCodigoMedida(medida);
//			//adicionar no banco
//			controleMed.adicionarMedicao(medicao);
//		}
//
//		//Coxa Direito
//		if(editCoxaDir.getText()!=null){
//			//data, codigoPerfil e CodigoUsuario
//			carregarMedicao();
//			medicao.setValor(Double.parseDouble(editCoxaDir.getText().toString()));
//			//medida
//			medida.setCodigo(controleMed.buscarMedida("Coxa", "d"));
//			medicao.setCodigoMedida(medida);
//			//adicionar no banco
//			controleMed.adicionarMedicao(medicao);
//		}
//
//		//Coxa Esquerda
//
//		if(editCoxaEsq.getText()!=null){
//			//data, codigoPerfil e CodigoUsuario
//			carregarMedicao();
//			medicao.setValor(Double.parseDouble(editCoxaEsq.getText().toString()));
//			//medida
//			medida.setCodigo(controleMed.buscarMedida("Coxa", "e"));
//			medicao.setCodigoMedida(medida);
//			//adicionar no banco
//			controleMed.adicionarMedicao(medicao);
//		}
//
//		//Panturrilha Direita
//
//		if(editPantuDir.getText()!=null){
//			//data, codigoPerfil e CodigoUsuario
//			carregarMedicao();
//			medicao.setValor(Double.parseDouble(editPantuDir.getText().toString()));
//			//medida
//			medida.setCodigo(controleMed.buscarMedida("Panturrilha", "d"));
//			medicao.setCodigoMedida(medida);
//			//adicionar no banco
//			controleMed.adicionarMedicao(medicao);
//		}
//
//		//Panturrilha Esuerda
//
//		if(editPantuEsq.getText()!=null){
//			//data, codigoPerfil e CodigoUsuario
//			carregarMedicao();
//			medicao.setValor(Double.parseDouble(editPantuEsq.getText().toString()));
//			//medida
//			medida.setCodigo(controleMed.buscarMedida("Panturrilha", "e"));
//			medicao.setCodigoMedida(medida);
//			//adicionar no banco
//			controleMed.adicionarMedicao(medicao);
//		}

	}

	public void carregarMedicao(){
		Usuario usuario = new Usuario();
		Date data = new Date();
		Medicao medicao = new Medicao();
		Perfil perfil = new Perfil();

		java.sql.Date data1 = new java.sql.Date( data.getDate());
		medicao.setDataMedicao(data1);
		usuario.setCodigo(perfil.getCodigousuario());
		medicao.setCodigoUsuario(perfil.getCodigousuario());
		//carregar perfil
		medicao.setCodigoPerfil(perfil.getCodigo());

	}

}
