package workoutsystem.view;

import java.util.ArrayList;

import workoutsystem.control.ControleExercicio;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class GUICriarExercicio extends Activity implements View.OnClickListener{

	private Spinner cbxGrupo;
	private EditText editNomeExercicio;
	private EditText editDescricaoExercicio;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.criarexercicio);
		cbxGrupo = (Spinner) findViewById(R.id.cbx_grupo);
		criarCombo();
		editNomeExercicio = (EditText) findViewById(R.id.edt_nomeExercicio);
		editDescricaoExercicio = (EditText) findViewById(R.id.edt_descricaoExercicio);
	}

	public void criarCombo(){
		ArrayList<String> listaGrupos = new ArrayList<String>();
		ControleExercicio controle = new ControleExercicio();
		ArrayList<GrupoMuscular> grupos = 
			(ArrayList<GrupoMuscular>) controle.listarGrupos(); 

		for (GrupoMuscular grupo : grupos){
			listaGrupos.add(grupo.getNome());
		}

		ArrayAdapter<String> adapter =
			new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listaGrupos);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
		cbxGrupo.setAdapter(adapter);

	}

	public Exercicio criarExercicio(){
		Exercicio exercicio = new Exercicio();
		GrupoMuscular grupo = new GrupoMuscular();
		exercicio.setNomeExercicio(editNomeExercicio.getText().toString());
		grupo.setNome(cbxGrupo.getSelectedItem().toString());
		exercicio.setDescricao(editDescricaoExercicio.getText().toString());
		exercicio.setGrupoMuscular(grupo);
		Log.i("Exercicios",exercicio.toString());
		return exercicio;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_criar:
			Exercicio e = criarExercicio();
			ControleExercicio ex = new ControleExercicio();
			Toast.makeText(this, 
					ex.adicionarExercicio(e),
					Toast.LENGTH_LONG).show();
			break;

		default:
			break;
		}

	}
}