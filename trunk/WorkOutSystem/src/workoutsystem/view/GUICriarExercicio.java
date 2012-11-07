package workoutsystem.view;

import java.util.ArrayList;

import workoutsystem.utilitaria.TipoExercicio;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class GUICriarExercicio extends Activity{

	private Spinner cbxGrupo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.criarexercicio);
		cbxGrupo = (Spinner) findViewById(R.id.cbx_grupo);
		criarCombo();
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

}
