package workoutsystem.view;

import java.util.List;

import workoutsystem.model.Exercicio;
import workoutsystem.model.Passo;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class GUIPasso extends Activity implements View.OnClickListener{

	private EditText editNome;
	private EditText editDescricao;
	private EditText editSequencia;
	private int indice;
	private List<Passo> passo;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.passo);
		
		editNome = (EditText) findViewById(R.id.txt_nomeexercicio);
		editSequencia = (EditText) findViewById(R.id.txt_sequenciapasso);
		editDescricao = (EditText) findViewById(R.id.txt_descricao_passo);
		
		indice = 0 ;
		Exercicio exercicio = (Exercicio) getIntent().getExtras().getSerializable("exercicio");
		passo = exercicio.getListaPassos();
		editNome.setText(exercicio.getNomeExercicio());
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
		editDescricao.setText(o.getExplicacao());
		editSequencia.setText("Passo "+o.getSequencia());
		
	}



}
