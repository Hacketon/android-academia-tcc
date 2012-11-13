package workoutsystem.view;

import java.util.ArrayList;

import workoutsystem.model.Exercicio;
import workoutsystem.utilitaria.TipoExercicio;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class GUICriarExercicio extends Activity{

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
		ArrayList<String> tipoExercicio = new ArrayList<String>();

		for (TipoExercicio t : TipoExercicio.values())
			tipoExercicio.add(t.getTipoExercicio());


		ArrayAdapter<String> adapter =
			new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tipoExercicio);

		adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
		cbxGrupo.setAdapter(adapter);

	}

	public void criarExercicio(){
		Exercicio exercicio = new Exercicio();

		exercicio.setNomeExercicio(editNomeExercicio.getText().toString());

		if(cbxGrupo.getSelectedItem().equals(TipoExercicio.Peito)){
			exercicio.setGrupoMuscular(TipoExercicio.Peito.toString());
		}else
			if(cbxGrupo.getSelectedItem().equals(TipoExercicio.Costa)){
				exercicio.setGrupoMuscular(TipoExercicio.Costa.toString());
			}
			else
				if(cbxGrupo.getSelectedItem().equals(TipoExercicio.Biceps)){
					exercicio.setGrupoMuscular(TipoExercicio.Biceps.toString());
				}else
					if(cbxGrupo.getSelectedItem().equals(TipoExercicio.Triceps)){
						exercicio.setGrupoMuscular(TipoExercicio.Triceps.toString());
					}
					else
						if(cbxGrupo.getSelectedItem().equals(TipoExercicio.Aerobico)){
							exercicio.setGrupoMuscular(TipoExercicio.Aerobico.toString());
						}
						else
							if(cbxGrupo.getSelectedItem().equals(TipoExercicio.Ombro)){
								exercicio.setGrupoMuscular(TipoExercicio.Ombro.toString());
							}
							else
								if(cbxGrupo.getSelectedItem().equals(TipoExercicio.MembrosInferiores)){
									exercicio.setGrupoMuscular(TipoExercicio.MembrosInferiores.toString());
								}else
									if(cbxGrupo.getSelectedItem().equals(TipoExercicio.Abdomen)){
										exercicio.setGrupoMuscular(TipoExercicio.Abdomen.toString());
									}

		exercicio.setDescricao(editDescricaoExercicio.getText().toString());
		exercicio.setPersonalizado(true);

	}
}