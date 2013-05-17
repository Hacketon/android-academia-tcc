package workoutsystem.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleFicha;
import workoutsystem.model.Ficha;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Objetivo;
import android.app.AlertDialog;
import android.app.ListActivity;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.DropListener;
import com.mobeta.android.dslv.DragSortListView.RemoveListener;

public class GUIFichaManipular extends ListActivity 
implements 
RemoveListener,
DropListener,
ListView.OnItemClickListener,
DialogInterface.OnClickListener,
ListView.OnItemLongClickListener{

	private TabHost hostfichatreino;
	private TabSpec spectreino;
	private TabSpec specficha;
	private EditText editNomeFicha;
	private EditText editDuracaoFicha;
	private EditText editObjetivoFicha;
	private Spinner cbxObjetivo;
	private List<String> listaObjetivo; 
	private ArrayAdapter<String> adapterTreino;
	private DragSortListView listTreinos;
	private List<Treino> listaTreinos;
	private String item;
	private int qual;
	private Ficha ficha;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fichamanipular);
		criarTab();
		editNomeFicha = (EditText) findViewById(R.id.edt_nomeFicha);
		editDuracaoFicha = (EditText) findViewById(R.id.edt_duracaodias);
		cbxObjetivo = (Spinner) findViewById(R.id.cbx_fichaObjetivo);
		criarCombo();
		long i  = (Long) getIntent().getExtras().getSerializable("ficha");
		ControleFicha controle = new ControleFicha();
		try {
			ficha = controle.buscarFichaCodigo(i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listTreinos = getListView(); 
		preencherFicha(ficha);
		listTreinos.setOnItemClickListener(this);

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
			listaTreinos = f.getTreinos();
			createListView(listaTreinos);
			for (String s : listaObjetivo){
				if (s.trim().equalsIgnoreCase(f.getObjetivo().trim())){
					cbxObjetivo.setSelection(pos);
					break;
				}
				pos++;
			}

		}else{
			listaTreinos = new ArrayList<Treino>();	
			createListView(listaTreinos);
		}

	}


	private void createListView(List<Treino> treinos) {
		List<String> nomeTreinos = new ArrayList<String>();

		for (Treino t : treinos){
			nomeTreinos.add(t.getNomeTreino());
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
			Treino t = new Treino();
			iniciarTreino(t);
			break;
		case R.id.remover_treino:

			break;
		case R.id.existente_treino:
			break;
		case R.id.finalizar_edicao:
			break;
		}
		return true;

	}

	private void iniciarTreino(Treino t){
		Intent i = new Intent(this,GUIFichaTreino.class);
		i.putExtra("treino", t);
		startActivity(i);
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
		ControleFicha controle = new ControleFicha();
		int cont; 
		int ordem = 1 ;
		int posicao = 0;
		String nome = ""  ;
		List<Treino> treinos = new ArrayList<Treino>();

		for (cont = 0 ; cont < adapterTreino.getCount(); cont++){

			nome = adapterTreino.getItem(cont);
			posicao = 0;
			
			for (Treino treino  : listaTreinos){
				
				if (nome.trim().
						equalsIgnoreCase
						(treino.getNomeTreino().trim())){
					treino.setOrdem(ordem);
					treinos.add(treino);
					listaTreinos.remove(posicao);
				}
				posicao = posicao + 1;
			}
			ordem++;
		}

		listaTreinos = treinos;
		ficha.setTreinos(listaTreinos);
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
		caixaDialogo(item,titulo,texto,negativa,positiva,pontuacao);
	}


	private void caixaDialogo(
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
		ControleFicha controle = new ControleFicha();
		String mensagem = "";
		for (Treino t : ficha.getTreinos()){
			if(t.getNomeTreino().
					equalsIgnoreCase(item)){
				long codigoTreino = t.getCodigoTreino();
				int codigoFicha = t.getCodigoFicha();
				mensagem = controle.removerTreino(codigoTreino,codigoFicha);
				break;
			}
		}
		return mensagem;
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		String item = (String) parent.getItemAtPosition(pos);
		Treino treino = null;
		for (Treino t : ficha.getTreinos()){
			if (item.equalsIgnoreCase(t.getNomeTreino())){
				treino = t;
				break;
			}
		}
		Intent i = new Intent(this,GUIFichaTreino.class);
		i.putExtra("treino",treino);
		startActivity(i);
			
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int pos,
			long id) {
		
		return false;
	}


	

}
