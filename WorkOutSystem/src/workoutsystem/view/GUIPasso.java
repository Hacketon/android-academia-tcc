package workoutsystem.view;


import java.util.List;

import workoutsystem.control.ControleExercicio;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Passo;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GUIPasso extends Activity implements View.OnClickListener{

	private TextView textNome;
	private TextView textDescricao;
	private TextView textSequencia;
	private ImageView imagem;
	private int indice;
	private List<Passo> passo;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.passo);

		textNome = (TextView) findViewById(R.id.txt_nome_exercicio);
		textSequencia = (TextView) findViewById(R.id.txt_sequenciapasso);
		textDescricao = (TextView) findViewById(R.id.txt_descricao_passo);
		imagem = (ImageView) findViewById(R.id.imagemExercicio);
		indice = 0 ;
		Exercicio exercicio = (Exercicio) getIntent().getExtras()
		.getSerializable("exercicio");
		
		passo = exercicio.getListaPassos();
		textNome.setText(exercicio.getNome());
		anteriorPasso();
	}



	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.btn_anteriorpasso:
			anteriorPasso();
			break;

		case R.id.btn_proximopasso:
			proximoPasso();
			break;
		}


	}

	private void proximoPasso() {
		Passo o = null;
		if (!passo.isEmpty()){
			if (indice <passo.size()-1){
				o = passo.get(indice +1);
			}if (indice == passo.size() - 1){
				o = passo.get(passo.size()-1);
			}
		}

		if (o != null){
			carregarPasso(o);
		}

	}

	private void anteriorPasso() {
		Passo o = null;
		if (!passo.isEmpty()){
			if (indice > 0){
				o = passo.get(indice -1);
				indice--;
			}else if (indice == 0 ){
				o = passo.get(0);
			}
		}
		if (o != null){
			carregarPasso(o);
		}

	}



	private void carregarPasso(Passo o) {
		ControleExercicio controle = new ControleExercicio();
		textDescricao.setText(o.getExplicacao());
		textSequencia.setText("Passo "+o.getSequencia());
		try {
			imagem.setImageResource(controle.carregarImagem(o));
		} catch (Exception e) {
			String mensagem = "Erro ao carregar imagem!";
			Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
			
		}
	}



}
