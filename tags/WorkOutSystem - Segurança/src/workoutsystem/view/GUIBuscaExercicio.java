package workoutsystem.view;

import java.util.ArrayList;

import workoutsystem.control.ControleExercicio;
import workoutsystem.model.GrupoMuscular;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class GUIBuscaExercicio extends Activity {

	private Spinner cbxExercicio;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buscarexercicio);
		cbxExercicio = (Spinner) findViewById(R.id.cbx_buscaexercicio);
		criarCombo();
	}
	
	private void criarCombo(){
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
		cbxExercicio.setAdapter(adapter);
		
	}


}
