package workoutsystem.view;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.utilitaria.AdaptadorIcones;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;


public class GUIPrincipal extends Activity implements AdapterView.OnItemClickListener{
	
	private GridView grid;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);
		grid = (GridView) findViewById(R.id.icon_grid);
		
		List<Integer> imagem = listarImagem();
		List<Integer> texto = listarTexto(); 
		
		AdaptadorIcones ad = new AdaptadorIcones(this,imagem,texto);	
		grid.setAdapter(ad);
		grid.setOnItemClickListener(this);
	}

	private List<Integer> listarTexto() {
		List<Integer> lista = new ArrayList<Integer>();
		
		lista.add(R.string.botao_perfil);
		lista.add(R.string.botao_exercicio);
		lista.add(R.string.botao_rotina);
		lista.add(R.string.botao_ficha);
		lista.add(R.string.botao_evolucao);
		lista.add(R.string.botao_medidas);
		lista.add(R.string.botao_ajuda);
		lista.add(R.string.botao_configuracao);
		lista.add(R.string.botao_sobre);
		
		return lista;
	}

	private List<Integer> listarImagem() {
		
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(R.drawable.ic_perfil);
		lista.add(R.drawable.ic_exercicio);
		lista.add(R.drawable.ic_rotina);
		lista.add(R.drawable.ic_ficha);
		lista.add(R.drawable.ic_evolucao);
		lista.add(R.drawable.ic_medida);
		lista.add(R.drawable.ic_ajuda);
		lista.add(R.drawable.ic_configuracao);
		lista.add(R.drawable.ic_sobre);
		
		return lista;
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {
		TextView textView = (TextView) view.findViewById(R.id.texto_icone);
		String comando = (String) textView.getText();
		String tela = "";
		
		if (comando.equalsIgnoreCase("conf.")){
			tela = "workoutsystem.view.STATUS";
		}else if (comando.equalsIgnoreCase("ajuda")) {
			tela = "workoutsystem.view.AJUDA";
		}else if(comando.equalsIgnoreCase("exercicio")){
			tela = "workoutsystem.view.EXERCICIO" ;
		}else if(comando.equalsIgnoreCase("rotina")){
			tela = "workoutsystem.view.ROTINA" ;
		}else if(comando.equalsIgnoreCase("ficha")){
			tela = "workoutsystem.view.FICHA";
		}else if(comando.equalsIgnoreCase("sobre")){
			tela = "workoutsystem.view.SOBRE" ;
		}else if(comando.equalsIgnoreCase("evolução")){
			tela = "workoutsystem.view.EVOLUCAO"; 
		}else if(comando.equalsIgnoreCase("perfil")){
			tela = "workoutsystem.view.PERFIL";
		}else if(comando.equalsIgnoreCase("medida")){
			tela = "workoutsystem.view.MEDIDAS";
		}
		Intent i = new Intent(tela);
		startActivity(i);
	}







}
