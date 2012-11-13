package workoutsystem.view;

import workoutsystem.model.Medida;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
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
		// TODO Auto-generated method stub

	}

	public void criaMedida(){
		Medida medida = new Medida();
		
		//fazer lógica 
		
	
		//Altura
		medida.setNome(textAltura.getText().toString());
		medida.setValor(Double.parseDouble(editAltura.getText().toString()));
		medida.setLado(0); //definir lado
		medida.setUnidade("Metros");
		//medida.setDataMedicao(dataMedicao);
		
		//Peso
		medida.setNome(textPeso.getText().toString());
		medida.setValor(Double.parseDouble(editPeso.getText().toString()));
		medida.setLado(0); //definir lado
		medida.setUnidade("Kg");
		//medida.setDataMedicao(dataMedicao);
		
		//Cintura
		medida.setNome(textCintura.getText().toString());
		medida.setValor(Double.parseDouble(editCintura.getText().toString()));
		medida.setLado(0);
		medida.setUnidade("cm");
		//medida.setDataMedicao(dataMedicao);
		
		//Quadril
		medida.setNome(textQuadril.getText().toString());
		medida.setValor(Double.parseDouble(editQuadril.getText().toString()));
		medida.setLado(0);
		medida.setUnidade("cm");
		//medida.setDataMedicao(dataMedicao);
		
		//Braço Direito
		medida.setNome(textBracoDir.getText().toString());
		medida.setValor(Double.parseDouble(editBracoDir.getText().toString()));
		medida.setLado(1);
		medida.setUnidade("cm");
		//medida.setDataMedicao(dataMedicao);
		
		//Braço Esquerdo
		medida.setNome(textBracoEsq.getText().toString());
		medida.setValor(Double.parseDouble(editBracoEsq.getText().toString()));
		medida.setLado(2);
		medida.setUnidade("cm");
		//medida.setDataMedicao(dataMedicao);
				
		//Peito
		medida.setNome(textPeito.getText().toString());
		medida.setValor(Double.parseDouble(editPeito.getText().toString()));
		medida.setLado(0);
		medida.setUnidade("cm");
		//medida.setDataMedicao(dataMedicao);
		
		//Coxa Direita
		medida.setNome(textCoxaDir.getText().toString());
		medida.setValor(Double.parseDouble(editCoxaDir.getText().toString()));
		medida.setLado(1);
		medida.setUnidade("cm");
		//medida.setDataMedicao(dataMedicao);
		
		//Coxa Esquerda
		medida.setNome(textCoxaEsq.getText().toString());
		medida.setValor(Double.parseDouble(editCoxaEsq.getText().toString()));
		medida.setLado(2);
		medida.setUnidade("cm");
		//medida.setDataMedicao(dataMedicao);
	
		//Panturrilha Direita
		medida.setNome(textPantuDir.getText().toString());
		medida.setValor(Double.parseDouble(editPantuDir.getText().toString()));
		medida.setLado(1);
		medida.setUnidade("cm");
		//medida.setDataMedicao(dataMedicao);
		
		//Panturrilha Esuerda
		medida.setNome(textPantuEsq.getText().toString());
		medida.setValor(Double.parseDouble(editPantuEsq.getText().toString()));
		medida.setLado(2);
		medida.setUnidade("cm");
		//medida.setDataMedicao(dataMedicao);
		
		
	}
	
}
