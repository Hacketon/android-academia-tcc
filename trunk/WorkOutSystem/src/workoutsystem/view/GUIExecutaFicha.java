package workoutsystem.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleTreino;
import workoutsystem.model.Serie;
import workoutsystem.model.Treino;
import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GUIExecutaFicha extends Activity implements ListView.OnItemLongClickListener{

	private ListView listaSerie; 
	private ArrayAdapter<String> adapterSerie;
	private Treino treino;
	private TextView treinoDia;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_exercicios);
		treino = (Treino) getIntent().getExtras().getSerializable("treino");
		listaSerie = (ListView) findViewById(R.id.lista_realizarexercicio);
		treinoDia = (TextView) findViewById(R.id.txt_treino);

		init();
	}

	private void init(){
		ControleTreino controleTreino = new ControleTreino();

		try {
			treino.setSerie(controleTreino.listarSerie(treino.getCodigo()));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		createListView(treino.getSerie());
		treinoDia.setText(treino.getNome());
	}


	private void createListView(List<Serie> lista) {
		List<String> series = new ArrayList<String>();

		for(Serie s: lista){

			String item = s.getOrdem() + "-" +
			s.getExercicio().getNome()+"\n" +
			"Quantidade : " + s.getQuantidade() + "\n" +
			"Unidade : " + s.getUnidade() + "\n" +"Carga : " + s.getCarga() ;

			series.add(item);

		}

		adapterSerie =  new ArrayAdapter<String>(this, R.layout.itens_simple_lista, series );

		listaSerie.setAdapter(adapterSerie);
		listaSerie.setOnItemLongClickListener(this);
		listaSerie.setCacheColorHint(Color.TRANSPARENT);


	}

	public void onClick(View evento) {
		switch (evento.getId()) {
		case R.id.btn_fazer:
			startActivity(new Intent("workoutsystem.view.REALIZAREXERCICIO"));
			break;
		}
	} 


	@Override
	public boolean onItemLongClick(AdapterView<?> parent , View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		return false;
	}





}
