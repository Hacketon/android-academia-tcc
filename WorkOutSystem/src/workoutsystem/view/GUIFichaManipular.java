package workoutsystem.view;

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
	private String[] fichas;
	private List<Ficha> listaFichaExistente;

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
			if (ficha.getAtual() == 1){
				editDuracaoFicha.setEnabled(false);
				Toast.makeText
					(this,"Ficha Atual : dura��o n�o pode ser alterada!",
						Toast.LENGTH_SHORT).show();
			}
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
		specficha.setIndicator("Ficha");
		hostfichatreino.addTab(specficha);

		spectreino = hostfichatreino.newTabSpec("tabfichatreinos");
		spectreino.setContent(R.id.tabfichatreinos);
		spectreino.setIndicator("Treinos");
		hostfichatreino.addTab(spectreino);
	}

	private void preencherFicha(Ficha f ){
		if (f.getCodigo() != 0){
			editNomeFicha.setText(f.getNome());
			editDuracaoFicha.setText(String.valueOf(f.getDuracao()));
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

	private Ficha criarFicha() throws Exception{
		
			ficha.setNome(editNomeFicha.getText().toString());
			if(editDuracaoFicha.getText().toString().
					equalsIgnoreCase("")){
				String mensagem = "Dura��o � obrigatoria!";
				throw new Exception(mensagem);
			}
			ficha.setDuracao(Integer.parseInt
					((editDuracaoFicha.getText().toString())));
			ficha.setObjetivo(cbxObjetivo.getSelectedItem().toString());

		
		return ficha;
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
		String mensagem = "";
		switch (item.getItemId()) {
		case R.id.novo_treino:
			if(ficha.getCodigo() == 0 ){
				mensagem = "Primeiro salve as informa��es da sua ficha";
			}else{
				criarCaixa("", "Novo Treino");
			}
			break;

		case R.id.salvar_ficha:
			try {
				ControleFicha controle = new ControleFicha();
				ficha = criarFicha();
				mensagem = controle.manipularFicha(ficha);
				if(ficha.getCodigo() == 0 ){
					ficha = controle.buscarUltimaFicha();
				}
			} catch (Exception e) {
				mensagem = e.getMessage();
			}
			break;
		case R.id.treino_existente:
			criarDialogFicha();
			break;
		}
		if(!mensagem.equalsIgnoreCase("")){
			Toast.makeText(this,mensagem, Toast.LENGTH_LONG).show();
		}
		return true;

	}



	private void criarDialogFicha() {
		String mensagem = "";
		if(ficha.getCodigo() != 0){
			ControleFicha controle = new ControleFicha();
			try {
				listaFichaExistente =
						controle.buscarFichaDiferente
									(ficha.getCodigo());
				AlertDialog.Builder alerta = 
							new AlertDialog.Builder(this);
				alerta.setTitle("Selecione uma ficha");
				int selected = -1;
				int cont = 0;
				fichas = new String[listaFichaExistente.size()];
				for(Ficha f : listaFichaExistente){
					fichas[cont] = f.getNome();
					cont ++;
				}
				alerta.setSingleChoiceItems(fichas,selected, this);
				alerta.show();
			} catch (Exception e) {
				mensagem = e.getMessage();
			}
		}else{
			mensagem = "Salve sua ficha primeiro!";
		}
		if(!mensagem.equalsIgnoreCase("")){
			Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
		}
		
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
			createListView(ficha.getTreinos());
			
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
		String texto = "Voc� realmente deseja deletar ";
		String negativa = "N�o";
		String positiva = "Sim";
		String pontuacao = "?";
		String titulo = "Confirma��o";
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
	public void onClick(DialogInterface dialog, int clicked) {
		String mensagem = "";
		ControleTreino controle = new ControleTreino();
		switch (clicked) {

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
			default:
				listarTreinoExistente(clicked);
			break;


		}

	}


	private void listarTreinoExistente(int clicked) {
		try {
			ControleTreino controle = new ControleTreino();
			if(fichas != null){
				String nome = fichas[clicked];
				Ficha ficha = null;
				for(Ficha f : listaFichaExistente){
					if(f.getNome().equalsIgnoreCase(nome.trim())){
						ficha = f;
						break;
					}
				}
			}
		
			controle.buscarTreinoValido(ficha.getCodigo());
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
	}


	private String removerTreino() throws Exception {
		ControleTreino controle = new ControleTreino();
		String mensagem = "";
		long codigoFicha = 0;
		long codigoTreino = 0;
		ControleFicha controleFicha = new ControleFicha();
		for (Treino t : ficha.getTreinos()){
			if(t.getNome().
					equalsIgnoreCase(item)){
				 codigoTreino = t.getCodigo();
				 codigoFicha= t.getCodigoFicha();
				mensagem = controle.removerTreino(codigoTreino,codigoFicha);
				break;
			}
		}
		
		ficha = controleFicha.buscarFichaCodigo(ficha.getCodigo());
		createListView(ficha.getTreinos());
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
		if(nomeTreino.equals("")){
			txtCodigoTreino.setText("0");
		}else{
			for(Treino t : ficha.getTreinos()){
				if(t.getNome().equalsIgnoreCase(nomeTreino)){
					txtCodigoTreino.setText(String.valueOf(t.getCodigo()));
					break;
				}
			}
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
						ficha.getCodigo(),
						(int) Long.parseLong(txtCodigoTreino.getText().toString()));
				ficha = controleFicha.buscarFichaCodigo(ficha.getCodigo());
				createListView(ficha.getTreinos());
				dialog.dismiss();
			} catch (Exception e) {
				mensagem = e.getMessage();

			}
			Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
			break;

		case R.id.btn_cancelarNome:
			dialog.dismiss();
			break;
		}

	}


}
