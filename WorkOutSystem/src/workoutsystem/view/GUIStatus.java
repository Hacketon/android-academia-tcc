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
		
		ControlePerfil controlePerf = new ControlePerfil();
		ControleMedida controleMed = new ControleMedida();
		Perfil perfil = controlePerf.buscarPerfil();
		String sexo="";
		List<Medicao> medicoes = new ArrayList<Medicao>();
		medicoes = controleMed.verificarMedicao(perfil.getCodigo());

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
			
			if(m.getCodigoMedida()== 1){
				mAltura.setValor(m.getValor());
			}
			if(m.getCodigoMedida()== 2){
				mPeso.setValor(m.getValor());	
			}
			if(m.getCodigoMedida()== 3){
				mCintura.setValor(m.getValor());
			}
			if(m.getCodigoMedida()== 4){
				mQuadril.setValor(m.getValor());
			}
			if(m.getCodigoMedida()== 5){
				mPeito.setValor(m.getValor());
			}
			if(m.getCodigoMedida()== 6){
				mBracoD.setValor(m.getValor());
			}
			if(m.getCodigoMedida()== 7){
				mBracoE.setValor(m.getValor());	
			}
			if(m.getCodigoMedida()== 8){
				mCoxaD.setValor(m.getValor());
			}
			if(m.getCodigoMedida()== 9){
				mCoxaE.setValor(m.getValor());
			}
			if(m.getCodigoMedida()== 10){
				mPantuD.setValor(m.getValor());
			}	
			if(m.getCodigoMedida()== 11){
				mPantuE.setValor(m.getValor());
			}
		}
		
		txtAltura.setText(txtAltura.getText()+"   " + mAltura.getValor());
		txtPeso.setText(txtPeso.getText()+"   " + mPeso.getValor());
		txtCintura.setText(txtCintura.getText()+"   "+ mCintura.getValor());	
		txtQuadril.setText(txtQuadril.getText()+"   "+ mQuadril.getValor());
		txtPeito.setText(txtPeito.getText()+"   "+ mPeito.getValor());
		txtBracoDir.setText(txtBracoDir.getText()+"   "+ mBracoD.getValor());
		txtBracoEsq.setText(txtBracoEsq.getText()+"   "+ mBracoE.getValor());
		txtCoxaDir.setText(txtCoxaDir.getText()+"   "+ mCoxaD.getValor());	
		txtCoxaEsq.setText(txtCoxaEsq.getText()+"   "+ mCoxaE.getValor());
		txtPantuDir.setText(txtPantuDir.getText()+"   " + mPantuD.getValor());
		txtPantuEsq.setText(txtPantuEsq.getText()+"   " + mPantuE.getValor());

	}

}
