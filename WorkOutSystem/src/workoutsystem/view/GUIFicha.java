package workoutsystem.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleFicha;
import workoutsystem.model.Ficha;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GUIFicha extends Activity implements 
DialogInterface.OnMultiChoiceClickListener,
ListView.OnItemClickListener,

DialogInterface.OnClickListener{

	private ListView listaFicha;
	private ArrayAdapter<String> adapter;
	private String [] fichas;
	private List<Ficha> listFicha;
	private boolean [] selecaoexc;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ficha);
		
		listaFicha = (ListView) findViewById(R.id.listafichas);
		listaFicha.setOnItemClickListener(this);
		try {
			createListView();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void createListView() throws SQLException {
		ControleFicha controle = new ControleFicha();
		listFicha  = controle.buscarFicha();
		ArrayList<String> nomes = new ArrayList<String>();
		for (Ficha f : listFicha){
			nomes.add(f.getNomeFicha());
		}
		adapter = new ArrayAdapter<String>
		(this,R.layout.itens_simple_lista,nomes);
		adapter.notifyDataSetChanged();
		listaFicha.setAdapter(adapter);
		listaFicha.setCacheColorHint(Color.BLUE);
		criarExclusao(listFicha);

	}

	private void criarExclusao(List<Ficha> listFichas) {
		int i = 0;
		fichas = null;
		selecaoexc = null;
		int listaTamanho = listFichas.size();
		
		if (listaTamanho > 0){
			fichas = new String[listaTamanho];
			selecaoexc = new boolean[listaTamanho];
		}
		 
		for (Ficha f : listFichas){
			fichas[i] = f.getNomeFicha();
			i++;
		}
	}

	
	protected Dialog onCreateDialog(int id){

		return new AlertDialog.Builder(this)
		.setTitle("Fichas")
		.setMultiChoiceItems(fichas,selecaoexc,this)
		.setPositiveButton("Deletar",this)
		.setNeutralButton("Cancelar",this)
		.create();

	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_principal_ficha, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.adicionar_ficha:
			Ficha f = new Ficha();
			iniciarFichaManipular(f);
		break;

		case R.id.remover_ficha:
			
		break;
		
		case R.id.mudar_ficha:
			
		break;
		}
		
		return false;
	}

	

	
	
	private void iniciarFichaManipular(Ficha f) {
		Intent i = new Intent(this,GUIFichaManipular.class);
		i.putExtra("ficha", f);
		startActivity(i);
		
	}

	private boolean deletarFichas() throws SQLException {
		ControleFicha controle = new ControleFicha();
		int i = 0 ;
		int contador = 0;
		boolean resultado;
		ArrayList<String> deletados = new ArrayList<String>();
		ArrayList<String> ndeletados = new ArrayList<String>();

		for (boolean b : selecaoexc){
			if (b == true){
				deletados.add(fichas[i]);
				contador++;
			}else{
				ndeletados.add(fichas[i]);
			}
			i++;
		}
		i = 0;
		contador = fichas.length - contador;
		fichas = null;
		selecaoexc = null;
		if (contador != 0){
			fichas = new String[contador];
			selecaoexc = new boolean[contador];
			for (String o : ndeletados){
				fichas[i] = o;
				i++;
			}

		}

		resultado = controle.excluirFicha(deletados);
		return resultado;


	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos,long id) {
		ControleFicha controle = new ControleFicha();
		try {
			Ficha ficha = new Ficha();
			String s = parent.getItemAtPosition(pos).toString();
			for (Ficha f : listFicha){
				if(f.getNomeFicha().equalsIgnoreCase(s.trim())){
					 ficha = f;
					break;
				}
				
			}
			
			iniciarFichaManipular(ficha);
			 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		selecaoexc[which]= isChecked;
	}

	public void onClick(DialogInterface dialog, int clicked) {
		String mensagem;
		switch (clicked) {
		case DialogInterface.BUTTON_POSITIVE:
			try {
				if (deletarFichas()){
					mensagem = "Operação realizada com sucesso";
					}else{
					mensagem = "Operação contendo erros";
				}
			} catch (SQLException e) {
				mensagem = "Erro no banco de dados";
			}
			try {
				createListView();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		}

	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		ArrayAdapter<String> list = new ArrayAdapter<String>
		(this, android.R.layout.select_dialog_multichoice,fichas);
		AlertDialog alerta = (AlertDialog) dialog;
		alerta.getListView().setAdapter(list);
		list.notifyDataSetChanged();


	}
}
