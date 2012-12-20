package workoutsystem.view;

import workoutsystem.control.ControlePerfil;
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
		carregarStatus();
		
	}

	public void onClick (View evento){
	
		
	}
	
	
	public void carregarStatus(){
		ControlePerfil controle = new ControlePerfil();
		Perfil perfil = controle.buscarPerfil();
		String sexo="";
		
		txtNome.setText(txtNome.getText() + "   "+ perfil.getNome());
		if(perfil.getSexo()){
			 sexo = "Masculino";
		}else{
			sexo = "Feminino";
		}
		txtSexo.setText(txtSexo.getText() + "   "+ sexo);
		txtFrequencia.setText(txtFrequencia.getText()+ "   " + perfil.getTotalFrequencia());
		
		//Medidas
	}
	
}
