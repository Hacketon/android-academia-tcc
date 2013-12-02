package workoutsystem.view;

import java.text.SimpleDateFormat;
import java.util.List;

import workoutsystem.control.ControleMedida;
import workoutsystem.control.ControlePerfil;
import workoutsystem.model.Medicao;
import workoutsystem.model.Perfil;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class GUIStatus extends Activity{

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
	private TextView txtMedida;
	private TextView txtDataMedida;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status);
		txtMedida = (TextView) findViewById(R.id.texto_medida);
		txtDataMedida = (TextView) findViewById(R.id.texto_data_medida);
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

	public void carregarStatus(){	

		ControlePerfil controlePerf = new ControlePerfil();
		ControleMedida controleMed = new ControleMedida();

		Perfil perfil = controlePerf.buscarPerfil();
		if (perfil == null){
			String erro = "Cria o seu perfil primeiro";
			Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
			finish();

		}else{
			String sexo="";

			txtNome.setText(txtNome.getText() + "   "+ perfil.getNome());

			if(perfil.getSexo()){
				sexo = "Masculino";
			}else{
				sexo = "Feminino";
			}
			txtSexo.setText(txtSexo.getText() + "   "+ sexo);
			txtFrequencia.setText(txtFrequencia.getText()+ "   " + controlePerf.quantidadeDias(perfil) );

			if (controleMed.verificarMedicao(perfil.getCodigo())){
				carregarMedicoes(perfil.getCodigo());
			}else{
				String erro = "Você não possui medidas cadastradas";
				Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
				finish();
			}

		}

	}

	private void carregarMedicoes(int codigo) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		ControleMedida controleMed = new ControleMedida();
		ControlePerfil controlePerfil = new ControlePerfil();
		String data = "";
		Perfil perfil = controlePerfil.buscarPerfil();
		try {
			data = controleMed.buscarDataUltimaMedicao();
			txtDataMedida.setText(data);
		for (int medida = 1; medida <12 ; medida++){
			List<Medicao> ultimasMedicoes = controleMed.ultimasMedicoes(perfil.getCodigo(), medida);
			Medicao medicao = ultimasMedicoes.get(0);
			
			switch (medicao.getCodigo()) {
			case 1:
				txtAltura.setText(txtAltura.getText()+"   " + medicao.getValor());
				break;
			case 2:
				txtPeso.setText(txtPeso.getText()+"   " + medicao.getValor());
				break;
			case 3:
				txtCintura.setText(txtCintura.getText()+"   "+ medicao.getValor());
				break;
			case 4:
				txtQuadril.setText(txtQuadril.getText()+"   "+ medicao.getValor());
				break;
			case 5:
				txtPeito.setText(txtPeito.getText()+"   "+ medicao.getValor());
				break;
			case 6:
				txtBracoDir.setText(txtBracoDir.getText()+"   "+ medicao.getValor());
				break;
			case 7:
				txtBracoEsq.setText(txtBracoEsq.getText()+"   "+ medicao.getValor());
				break;
			case 8:
				txtCoxaDir.setText(txtCoxaDir.getText()+"   "+ medicao.getValor());	
				break;
			case 9:
				txtCoxaEsq.setText(txtCoxaEsq.getText()+"   "+ medicao.getValor());
				break;
			case 10:
				txtPantuDir.setText(txtPantuDir.getText()+"   " + medicao.getValor());
				break;
			case 11:
				txtPantuEsq.setText(txtPantuEsq.getText()+"   " + medicao.getValor());
				break;
			}

		}
		
		} catch (Exception e) {
		
			e.printStackTrace();
		}
}




}





