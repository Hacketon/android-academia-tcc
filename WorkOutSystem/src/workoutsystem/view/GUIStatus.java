package workoutsystem.view;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleMedida;
import workoutsystem.control.ControlePerfil;
import workoutsystem.model.Medicao;
import workoutsystem.model.Perfil;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class GUIStatus extends Activity implements View.OnClickListener{

	private TextView txtNome;
	private TextView txtSexo;
	private TextView txtFrequencia;
	private TextView txtAltura;
	private TextView txtPeso;
	private TextView txtCintura;  
	private TextView txtPeito; 
	private TextView txtQuadril; 
	private TextView txtBracoDir; 
	private TextView txtBracoEsq; 
	private TextView txtCoxaDir; 
	private TextView txtCoxaEsq; 
	private TextView txtPantuDir; 
	private TextView txtPantuEsq;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);
		txtNome = (TextView) findViewById(R.id.tv_nome);
		txtSexo = (TextView) findViewById(R.id.tv_sexousuario);
		txtFrequencia = (TextView) findViewById(R.id.tv_frequenciausuario);
		txtAltura = (TextView) findViewById(R.id.tv_alturausuario);
		txtPeso = (TextView) findViewById(R.id.tv_pesousuario);
		txtCintura = (TextView) findViewById(R.id.tv_cinturausuario);
		txtQuadril = (TextView) findViewById(R.id.tv_quadrilusaurio);
		txtPeito = (TextView) findViewById(R.id.tv_peitousuario);
		txtBracoDir = (TextView) findViewById(R.id.tv_bracodirusuario);
		txtBracoEsq = (TextView) findViewById(R.id.tv_bracoesqusuario);
		txtCoxaDir = (TextView) findViewById(R.id.tv_coxadirusuario);
		txtCoxaEsq = (TextView) findViewById(R.id.tv_coxaesqusuario);
		txtPantuDir = (TextView) findViewById(R.id.tv_panturillhadirusuario);
		txtPantuEsq = (TextView) findViewById(R.id.tv_panturillhaesqusuario);

		ControlePerfil controlePerf = new ControlePerfil();
		if(controlePerf.buscarPerfil()!=null){
			carregarStatus();
		}

	}

	public void onClick (View evento){


	}


	public void carregarStatus(){
		ControlePerfil controlePerf = new ControlePerfil();
		ControleMedida controleMed = new ControleMedida();
		Perfil perfil = controlePerf.buscarPerfil();
		String sexo="";
		List<Medicao> medicoes = new ArrayList<Medicao>();
		medicoes = controleMed.buscarMedicao(perfil.getCodigo());


		txtNome.setText(txtNome.getText() + "   "+ perfil.getNome());
		if(perfil.getSexo()){
			sexo = "Masculino";
		}else{
			sexo = "Feminino";
		}
		txtSexo.setText(txtSexo.getText() + "   "+ sexo);
		txtFrequencia.setText(txtFrequencia.getText()+ "   " + controlePerf.quantidadeDias(perfil) );

		//Medidas
		for(Medicao m : medicoes){
			
			if(m.getCodigoMedida()== controleMed.buscarMedida("Altura", "a")){
				txtAltura.setText(txtAltura.getText()+"   " + m.getValor());
			}
			if(m.getCodigoMedida()== controleMed.buscarMedida("Peso", "a")){
				txtPeso.setText(txtPeso.getText()+"   " + m.getValor());	
			}
			if(m.getCodigoMedida()== controleMed.buscarMedida("Cintura", "a")){
				txtCintura.setText(txtCintura.getText()+"   "+ m.getValor() );	
			}
			if(m.getCodigoMedida()== controleMed.buscarMedida("Quadril", "a")){
				txtQuadril.setText(txtQuadril.getText()+"   "+ m.getValor() );
			}
			if(m.getCodigoMedida()== controleMed.buscarMedida("Peito", "a")){
				txtPeito.setText(txtPeito.getText()+"   "+ m.getValor() );
			}
			if(m.getCodigoMedida()== controleMed.buscarMedida("Braco", "d")){
				txtBracoDir.setText(txtBracoDir.getText()+"   "+ m.getValor() );
			}
			if(m.getCodigoMedida()== controleMed.buscarMedida("Braco", "e")){
				txtBracoEsq.setText(txtBracoEsq.getText()+"   "+ m.getValor() );	
			}
			if(m.getCodigoMedida()== controleMed.buscarMedida("Coxa", "d")){
				txtCoxaDir.setText(txtCoxaDir.getText()+"   "+ m.getValor() );	
			}
			if(m.getCodigoMedida()== controleMed.buscarMedida("Coxa", "e")){
				txtCoxaEsq.setText(txtCoxaEsq.getText()+"   "+ m.getValor() );
			}
			if(m.getCodigoMedida()== controleMed.buscarMedida("Panturrilha", "d")){
				txtPantuDir.setText(txtPantuDir.getText()+"   " + m.getValor());
			}	
			if(m.getCodigoMedida()== controleMed.buscarMedida("Panturrilha", "e")){
				txtPantuEsq.setText(txtPantuEsq.getText()+"   " + m.getValor() );
			}




		}
	}

}
