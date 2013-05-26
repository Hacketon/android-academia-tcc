package workoutsystem.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleFicha;
import workoutsystem.control.ControleTreino;
import workoutsystem.model.Ficha;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Objetivo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.DropListener;
import com.mobeta.android.dslv.DragSortListView.RemoveListener;

public class GUIFichaManipular extends ListActivity 
implements 
RemoveListener,
DropListener,
View.OnClickListener,
ListView.OnItemClickListener,
DialogInterface.OnClickListener,
ListView.OnItemLongClickListener{

	private TabHost hostfichatreino;
	private TabSpec spectreino;
	private Dialog dialog;
	private TabSpec specficha;
	private EditText editNomeFicha;
	private EditText editNomeTreino;
	private EditText editDuracaoFicha;
	private EditText editObjetivoFicha;
	private Spinner cbxObjetivo;
	private List<String> listaObjetivo; 
	private ArrayAdapter<String> adapterTreino;
	private DragSortListView listTreinos;
	private String item;
	private int qual;
	private Ficha ficha;
	private Button btnCancelar;
	private Button btnSalvar;
	private TextView txtCodigoTreino;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fichamanipular);
		criarTab();
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.nome_treino);
		editNomeTreino = (EditText) dialog.findViewById(R.id.txt_NomeTreino);
		txtCodigoTreino = (TextView) dialog.findViewById(R.id.txt_codigoTreino);
		btnCancelar = (Button) dialog.findViewById(R.id.btn_cancelarNome);
		btnSalvar = (Button) dialog.findViewById(R.id.btn_confirmarNome);
		editNomeFicha = (EditText) findViewById(R.id.edt_nomeFicha);
		editDuracaoFicha = (EditText) findViewById(R.id.edt_duracaodias);
		cbxObjetivo = (Spinner) findViewById(R.id.cbx_fichaObjetivo);
		criarCombo();
		long i  = (Long) getIntent().getExtras().getSerializable("ficha");
		ControleFicha controle = new ControleFicha();
		try {
			ficha = controle.buscarFichaCodigo(i);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		btnCancelar.setOnClickListener(this);
		btnSalvar.setOnClickListener(this);
		listTreinos = getListView(); 
		
		preencherFicha(ficha);
		
		listTreinos.setOnItemClickListener(this);
		listTreinos.setOnItemLongClickListener(this);

	}


	@Override
	public DragSortListView getListView() {
		return (DragSortListView) super.getListView(); 

	} 



	private void criarCombo(){
		listaObjetivo = new ArrayList<String>();

		for (Objetivo s : Objetivo.values()){
			listaObjetivo.add(s.getObjetivo());
		}

		ArrayAdapter<String> adapter =
			new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,listaObjetivo);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
		cbxObjetivo.setAdapter(adapter);
	} 


	public void criarTab(){
		hostfichatreino = (TabHost) findViewById(R.id.hostfichatreino);
		hostfichatreino.setup();

		specficha = hostfichatreino.newTabSpec("tabfichas");
		specficha.setContent(R.id.tabfichas);
		specficha.setIndicator("Fichas");
		hostfichatreino.addTab(specficha);

		spectreino = hostfichatreino.newTabSpec("tabfichatreinos");
		spectreino.setContent(R.id.tabfichatreinos);
		spectreino.setIndicator("Treinos");
		hostfichatreino.addTab(spectreino);
	}

	private void preencherFicha(Ficha f ){
		if (f.getCodigoFicha() != 0){
			editNomeFicha.setText(f.getNomeFicha());
			editDuracaoFicha.setText(String.valueOf(f.getDuracaoDias()));
			int pos = 0 ;

			createListView(f.getTreinos());
			for (String s : listaObjetivo){
				if (s.trim().equalsIgnoreCase(f.getObjetivo().trim())){
					cbxObjetivo.setSelection(pos);
					break;
				}
				pos++;
			}

		}else{

			createListView(new ArrayList<Treino>());
		}

	}


	private void createListView(List<Treino> treinos) {
		List<String> nomeTreinos = new ArrayList<String>();

		for (Treino t : treinos){
			nomeTreinos.add(t.getNome());
		}

		adapterTreino = new ArrayAdapter<String>(this,
				R.layout.list_item_checkable,
				R.id.text,
				nomeTreinos);


		listTreinos.setAdapter(adapterTreino);
		listTreinos.setRemoveListener(this);
		listTreinos.setDropListener(this);
		listTreinos.setCacheColorHint(Color.TRANSPARENT);
	}

	private Ficha criarFicha(){
		Ficha ficha = new Ficha();
		ficha.setNomeFicha(editNomeFicha.getText().toString());
		ficha.setDuracaoDias(Integer.parseInt
				((editDuracaoFicha.getText().toString())));
		ficha.setObjetivo(editObjetivoFicha.getText().toString());

		return ficha;
	}

	private Treino criarTreino(){
		Treino treino = new Treino();
		String item = "";
		return treino;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_manipular_ficha, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.novo_treino:
			criarCaixa("", "Novo Treino");
			break;

		case R.id.finalizar_edicao:
			break;

			/*
			 * 		case R.id.remover_treino:
					break;
					case R.id.existente_treino:
					break;
			 * 
			 * 	
			 */

		}
		return true;

	}

	

	@Override
	public void drop(int from, int to) {
		if (from != to) {
			DragSortListView list = getListView();
			String item = adapterTreino.getItem(from);
			adapterTreino.remove(item);
			adapterTreino.insert(item, to);
			list.moveCheckState(from, to);
			reordenarLista();
		}

	}

	private void reordenarLista() {
		ControleTreino controle = new ControleTreino();
		int cont; 
		int ordem = 1 ;
		int posicao = 0;
		String nome = ""  ;
		List<Treino> treinos = new ArrayList<Treino>();

		for (cont = 0 ; cont < adapterTreino.getCount(); cont++){

			nome = adapterTreino.getItem(cont);
			posicao = 0;

			for (Treino treino  : ficha.getTreinos()){

				if (nome.trim().
						equalsIgnoreCase
						(treino.getNome().trim())){
					treino.setOrdem(ordem);
					treinos.add(treino);
					
				}
				posicao = posicao + 1;
			}
			ordem++;
		}


		ficha.setTreinos(treinos);
		try {
			controle.reordenarTreino(ficha.getTreinos());
		} catch (Exception e) {
			Toast.makeText(this,
					e.getMessage(),
					Toast.LENGTH_SHORT).show();

		}


	}


	@Override
	public void remove(int which) {
		item = adapterTreino.getItem(which);
		qual = which;
		String texto = "Você realmente deseja deletar ";
		String negativa = "Não";
		String positiva = "Sim";
		String pontuacao = "?";
		String titulo = "Confirmação";
		criarCaixa(item,titulo,texto,negativa,positiva,pontuacao);
	}


	private void criarCaixa(
			String item,
			String titulo,
			String texto,
			String negativa,
			String positiva,
			String pontuacao) {

		texto = texto + item + pontuacao;

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage(texto);
		alert.setTitle(titulo);
		alert.setNegativeButton(negativa, this);
		alert.setPositiveButton(positiva, this);
		alert.show();
	}


	@Override
	public void onClick(DialogInterface dialog, int which) {
		String mensagem = "";
		switch (which) {

		case DialogInterface.BUTTON_NEGATIVE:
			createListView(ficha.getTreinos());
			break;	
		case DialogInterface.BUTTON_POSITIVE:
			try {
				mensagem = removerTreino();
			} catch (Exception e) {
				mensagem = e.getMessage();

			}
			Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
			break;


		}

	}


	private String removerTreino() throws Exception {
		ControleTreino controle = new ControleTreino();
		String mensagem = "";
		for (Treino t : ficha.getTreinos()){
			if(t.getNome().
					equalsIgnoreCase(item)){
				long codigoTreino = t.getCodigoTreino();
				long codigoFicha = t.getCodigoFicha();
				mensagem = controle.removerTreino(codigoTreino,codigoFicha);
				break;
			}
		}
		return mensagem;
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		iniciarFichaTreino(parent, pos);

	}


	private void iniciarFichaTreino(AdapterView<?> parent, int pos) {
		String item = (String) parent.getItemAtPosition(pos);
		Treino treino = null;
		for (Treino t : ficha.getTreinos()){
			if (item.equalsIgnoreCase(t.getNome())){
				treino = t;
				break;
			}
		}
		Intent i = new Intent(this,GUIFichaTreino.class);
		i.putExtra("treino",treino);
		startActivity(i);
	}


	private void criarCaixa(String nomeTreino,String titulo) {
		dialog.setTitle(titulo);
		editNomeTreino.setText(nomeTreino);
		for(Treino t : ficha.getTreinos()){
			if(t.getNome().equalsIgnoreCase(nomeTreino)){
				txtCodigoTreino.setText(String.valueOf(t.getCodigoTreino()));
				break;
			}
		}
		if(txtCodigoTreino.getText().toString().equalsIgnoreCase("")){
			txtCodigoTreino.setText("0");
		}
		dialog.show();
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int pos,
			long id) {
		String item = (String) parent.getItemAtPosition(pos);
		criarCaixa(item,"Renomear Treino");
		return false;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirmarNome:
			String mensagem = "";
			ControleTreino controleTreino = new ControleTreino();
			ControleFicha controleFicha = new ControleFicha();
			try {
				mensagem = controleTreino.manipularTreino
						 (editNomeTreino.getText().toString(),
							ficha.getCodigoFicha(),
							Long.parseLong(txtCodigoTreino.getText().toString()));
				ficha = controleFicha.buscarFichaCodigo(ficha.getCodigoFicha());
				createListView(ficha.getTreinos());
				dialog.dismiss();
			} catch (Exception e) {
				mensagem = e.getMessage();
				
			}
			dialog.dismiss();
			Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
			break;

		case R.id.btn_cancelarNome:
			dialog.dismiss();
			break;
		}
		
	}




}
