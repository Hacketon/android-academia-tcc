package workoutsystem.view;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.utilitaria.AdaptadorIcones;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.AdapterView;
import android.widget.TextView;

public class GUIAjuda extends Activity implements AdapterView.OnItemClickListener, DialogInterface.OnClickListener{

	private GridView gridAjuda;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajuda);

		List<Integer> imagem = listarImagem();
		List<Integer> texto = listarTexto(); 
		AdaptadorIcones ad = new AdaptadorIcones(this, imagem, texto);
		gridAjuda = (GridView) findViewById(R.id.grid_ajuda);

		gridAjuda.setAdapter(ad);
		gridAjuda.setOnItemClickListener(this);

	}


	private List<Integer> listarTexto() {
		List<Integer> lista = new ArrayList<Integer>();

		lista.add(R.string.botao_perfil);
		lista.add(R.string.botao_exercicio);
		lista.add(R.string.botao_rotina);
		lista.add(R.string.botao_ficha);
		lista.add(R.string.botao_evolucao);
		lista.add(R.string.botao_medidas);
		lista.add(R.string.botao_status);


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
		lista.add(R.drawable.ic_status);


		return lista;
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {
		TextView textView = (TextView) view.findViewById(R.id.texto_icone);
		String comando = (String) textView.getText();
		//String tela = "";

		if(comando.equalsIgnoreCase("exercicio")){
			alert("Exercicio", "Teste ");
		}else if(comando.equalsIgnoreCase("rotina")){
			alert("Rotina", "Teste ");
		}else if(comando.equalsIgnoreCase("ficha")){
			alert("Ficha", "Teste ");
		}else if(comando.equalsIgnoreCase("evolução")){
			alert("Evolução", "Teste ");
		}else if(comando.equalsIgnoreCase("perfil")){
			alert("Perfil", "Teste ");
		}else if(comando.equalsIgnoreCase("medida")){
			alert("Medida", "Teste ");
		}
		//Intent i = new Intent(tela);
		//startActivity(i);


	}


	public void alert(String titulo,String mensagem){
		AlertDialog.Builder a = new AlertDialog.Builder(this);
		a.setTitle(titulo);
		a.setMessage(mensagem);
		a.setPositiveButton("OK", this);
		a.show();
	}



	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {

		case DialogInterface.BUTTON_POSITIVE:
			dialog.dismiss();
			break;

		default:
			break;
		}


	}
}