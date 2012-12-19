package workoutsystem.view;

import java.util.Date;

import workoutsystem.control.ControleMedida;
import workoutsystem.control.ControlePerfil;
import workoutsystem.control.ControleUsuario;
import workoutsystem.dao.MedidaDao;
import workoutsystem.dao.PerfilDao;
import workoutsystem.dao.UsuarioDao;
import workoutsystem.interfaces.IMedidaDao;
import workoutsystem.interfaces.IPerfilDao;
import workoutsystem.interfaces.IUsuarioDao;
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

		ControleMedida controleMed = new ControleMedida();
		Medicao m = controleMed.buscarMedicao(1);
		
		carregarCampos(m);

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
		ControlePerfil controlePerf = new ControlePerfil();

		Date data = new java.util.Date();
		Perfil perfil = controlePerf.buscarPerfil();
		IPerfilDao daoPerf = new PerfilDao();
		perfil = daoPerf.buscarPerfil();
		
		//Altura
		if(!editAltura.getText().toString().equalsIgnoreCase("")){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			medicao.setValor(Double.parseDouble(editAltura.getText().toString()));
			medicao.setDataMedicao(data);
			medicao.setCodigoUsuario(perfil.getCodigousuario());
			//carregar perfil
			medicao.setCodigoPerfil(perfil.getCodigo());
			//medida
			medida.setCodigo(controleMed.buscarMedida("Altura", "a"));
			medicao.setCodigoMedida(medida.getCodigo());
			//adicionar no banco
			Toast.makeText(this, "Altura" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_SHORT).show();

		}

		//Peso
		if(!editPeso.getText().toString().equalsIgnoreCase("")){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			java.sql.Date data1 = new java.sql.Date( data.getDate());
			medicao.setDataMedicao(data1);
			medicao.setCodigoUsuario(perfil.getCodigousuario());
			//carregar perfil
			medicao.setCodigoPerfil(perfil.getCodigo());
			medicao.setValor(Double.parseDouble(editPeso.getText().toString()));
			//medida
			medida.setCodigo(controleMed.buscarMedida("Peso", "a"));
			medicao.setCodigoMedida(medida.getCodigo());
			//adicionar no banco
			Toast.makeText(this, "Peso" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_SHORT).show();

		}

		//Cintura
		if(!editCintura.getText().toString().equalsIgnoreCase("")){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			java.sql.Date data1 = new java.sql.Date( data.getDate());
			medicao.setDataMedicao(data1);
			medicao.setCodigoUsuario(perfil.getCodigousuario());
			//carregar perfil
			medicao.setCodigoPerfil(perfil.getCodigo());
			medicao.setValor(Double.parseDouble(editCintura.getText().toString()));
			//medida
			medida.setCodigo(controleMed.buscarMedida("Cintura", "a"));
			medicao.setCodigoMedida(medida.getCodigo());
			//adicionar no banco
			Toast.makeText(this, "Medida Cintura" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_SHORT).show();

		}

		//Quadril
		if(!editQuadril.getText().toString().equalsIgnoreCase("")){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			java.sql.Date data1 = new java.sql.Date( data.getDate());
			medicao.setDataMedicao(data1);
			medicao.setCodigoUsuario(perfil.getCodigousuario());
			//carregar perfil
			medicao.setCodigoPerfil(perfil.getCodigo());
			medicao.setValor(Double.parseDouble(editQuadril.getText().toString()));
			//medida
			medida.setCodigo(controleMed.buscarMedida("Quadril", "a"));
			medicao.setCodigoMedida(medida.getCodigo());
			//adicionar no banco

			Toast.makeText(this, "Medida Quadril" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_SHORT).show();

		}

		//Braço Direito
		if(!editBracoDir.getText().toString().equalsIgnoreCase("")){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			java.sql.Date data1 = new java.sql.Date( data.getDate());
			medicao.setDataMedicao(data1);
			medicao.setCodigoUsuario(perfil.getCodigousuario());
			//carregar perfil
			medicao.setCodigoPerfil(perfil.getCodigo());
			medicao.setValor(Double.parseDouble(editBracoDir.getText().toString()));
			//medida
			medida.setCodigo(controleMed.buscarMedida("Braco", "d"));
			medicao.setCodigoMedida(medida.getCodigo());
			//adicionar no banco

			Toast.makeText(this, "Medida Braço Direito" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_SHORT).show();}

		//Braço Esquerdo
		if(!editBracoEsq.getText().toString().equalsIgnoreCase("")){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			java.sql.Date data1 = new java.sql.Date( data.getDate());
			medicao.setDataMedicao(data1);
			medicao.setCodigoUsuario(perfil.getCodigousuario());
			//carregar perfil
			medicao.setCodigoPerfil(perfil.getCodigo());
			medicao.setValor(Double.parseDouble(editBracoEsq.getText().toString()));
			//medida
			medida.setCodigo(controleMed.buscarMedida("Braco", "e"));
			medicao.setCodigoMedida(medida.getCodigo());
			//adicionar no banco

			Toast.makeText(this, "Medida Braço Esquerdo" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_SHORT).show();
		}

		//Peito
		if(!editPeito.getText().toString().equalsIgnoreCase("")){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			java.sql.Date data1 = new java.sql.Date( data.getDate());
			medicao.setDataMedicao(data1);
			medicao.setCodigoUsuario(perfil.getCodigousuario());
			//carregar perfil
			medicao.setCodigoPerfil(perfil.getCodigo());
			medicao.setValor(Double.parseDouble(editPeito.getText().toString()));
			//medida
			medida.setCodigo(controleMed.buscarMedida("Peito", "a"));
			medicao.setCodigoMedida(medida.getCodigo());
			//adicionar no banco

			Toast.makeText(this, "Medida Peito" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_SHORT).show();}

		//Coxa Direito
		if(!editCoxaDir.getText().toString().equalsIgnoreCase("")){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			java.sql.Date data1 = new java.sql.Date( data.getDate());
			medicao.setDataMedicao(data1);
			medicao.setCodigoUsuario(perfil.getCodigousuario());
			//carregar perfil
			medicao.setCodigoPerfil(perfil.getCodigo());
			medicao.setValor(Double.parseDouble(editCoxaDir.getText().toString()));
			//medida
			medida.setCodigo(controleMed.buscarMedida("Coxa", "d"));
			medicao.setCodigoMedida(medida.getCodigo());
			//adicionar no banco
		
			Toast.makeText(this, "Medida Coxa Direita" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_SHORT).show();
		}

		//Coxa Esquerda

		if(!editCoxaEsq.getText().toString().equalsIgnoreCase("")){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			
			java.sql.Date data1 = new java.sql.Date( data.getDate());
			medicao.setDataMedicao(data1);
			medicao.setCodigoUsuario(perfil.getCodigousuario());
			//carregar perfil
			medicao.setCodigoPerfil(perfil.getCodigo());
			medicao.setValor(Double.parseDouble(editCoxaEsq.getText().toString()));
			//medida
			medida.setCodigo(controleMed.buscarMedida("Coxa", "e"));
			medicao.setCodigoMedida(medida.getCodigo());
			//adicionar no banco

			Toast.makeText(this, "Medida Coxa Esquerda" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_SHORT).show();
		}

		//Panturrilha Direita

		if(!editPantuDir.getText().toString().equalsIgnoreCase("")){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			
			java.sql.Date data1 = new java.sql.Date( data.getDate());
			medicao.setDataMedicao(data1);
			medicao.setCodigoUsuario(perfil.getCodigousuario());
			//carregar perfil
			medicao.setCodigoPerfil(perfil.getCodigo());
			medicao.setValor(Double.parseDouble(editPantuDir.getText().toString()));
			//medida
			medida.setCodigo(controleMed.buscarMedida("Panturrilha", "d"));
			medicao.setCodigoMedida(medida.getCodigo());
			//adicionar no banco

			Toast.makeText(this, "Medida Panturrilha Direita" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_SHORT).show();
		}

		//Panturrilha Esuerda

		if(!editPantuEsq.getText().toString().equalsIgnoreCase("")){
			//data, codigoPerfil e CodigoUsuario
			//carregarMedicao();
			
			java.sql.Date data1 = new java.sql.Date( data.getDate());
			medicao.setDataMedicao(data1);
			medicao.setCodigoUsuario(perfil.getCodigousuario());
			//carregar perfil
			medicao.setCodigoPerfil(perfil.getCodigo());
			medicao.setValor(Double.parseDouble(editPantuEsq.getText().toString()));
			//medida
			medida.setCodigo(controleMed.buscarMedida("Panturrilha", "e"));
			medicao.setCodigoMedida(medida.getCodigo());
			//adicionar no banco
		

			Toast.makeText(this, "Medida Panturrilha Esquerda" + controleMed.adicionarMedicao(medicao),
					Toast.LENGTH_SHORT).show();
		
		}

	}

public void carregarCampos(Medicao m){


	
	//Altura
	if(m.getCodigoMedida() == 1){
		editAltura.setText(Double.toString(m.getValor()));
	}

	//Peso
	if(m.getCodigoMedida() == 2){
		editPeso.setText(Double.toString(m.getValor()));
	}
	//Cintura
	if(m.getCodigoMedida() == 3){
		editCintura.setText(Double.toString(m.getValor()));
	}
	//Quadril
	if(m.getCodigoMedida() == 4){
		editQuadril.setText(Double.toString(m.getValor()));
	}
	//Braço Direito
	if(m.getCodigoMedida() == 6){
		editBracoDir.setText(Double.toString(m.getValor()));
	}
	//Braço Esquerdo
	if(m.getCodigoMedida() == 7){
		editBracoEsq.setText(Double.toString(m.getValor()));
	}
	//Peito
	if(m.getCodigoMedida() == 5){
		editPeito.setText(Double.toString(m.getValor()));
	}
	//Coxa Direito
	if(m.getCodigoMedida() == 8){
		editCoxaDir.setText(Double.toString(m.getValor()));
	}
	//Coxa Esquerda
	if(m.getCodigoMedida() == 9){
		editCoxaEsq.setText(Double.toString(m.getValor()));
	}
	//Panturrilha Direita
	if(m.getCodigoMedida() == 10){
		editPantuDir.setText(Double.toString(m.getValor()));
	}
	//Panturrilha Esuerda
	if(m.getCodigoMedida() == 11){
		editPantuEsq.setText(Double.toString(m.getValor()));
	}
	
}
	

	
//	public void carregarMedicao(){
//		Usuario usuario = new Usuario();
//		Date data = new Date();
//		Medicao medicao = new Medicao();
//		Perfil perfil = new Perfil();
//
//		java.sql.Date data1 = new java.sql.Date( data.getDate());
//		medicao.setDataMedicao(data1);
//		usuario.setCodigo(perfil.getCodigousuario());
//		medicao.setCodigoUsuario(perfil.getCodigousuario());
//		//carregar perfil
//		medicao.setCodigoPerfil(perfil.getCodigo());
//
//	}

}
