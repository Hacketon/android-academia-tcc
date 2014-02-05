package workoutsystem.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import workoutsystem.control.ControleFicha;
import workoutsystem.control.ControleTreino;
import workoutsystem.model.Ficha;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Objetivo;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
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

public class GUIFichaTreino extends ListActivity 
implements 
RemoveListener,
DropListener,
View.OnClickListener,
ListView.OnItemClickListener,
DialogInterface.OnClickListener,
ListView.OnItemLongClickListener{

	private TabHost hospedeiro;
	private TabSpec spectreino;
	private Dialog dialog;
	private TabSpec specficha;
	private EditText editNomeFicha;
	private EditText editNomeTreino;
	private EditText editAntecedencia;
	private EditText editDuracaoFicha;
	private Spinner cbxObjetivo;
	private List<String> listaObjetivo; 
	private ArrayAdapter<String> adapterTreino;
	private DragSortListView listTreinos;
	private String item;
	private int qual;
	private Ficha ficha;
	private Button btnCancelar;
	private Button btnSalvar;
	private Button btnAdicionar;
	private TextView txtCodigoTreino;
	private String[] fichas;
	private List<Ficha> listaFichaExistente;
	private Dialog dialogTreinos;
	private ListView listaExistente;
	private ArrayAdapter<String> adapterExistente;
	private List<Treino> listaAdicao;
	private List<Treino> treinos;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ficha_manipular);
		criarTab();
		dialogTreinos = new Dialog(this);
		dialogTreinos.setContentView(R.layout.lista_treinos);
		listaExistente = (ListView) dialogTreinos.findViewById(R.id.lista_treino_existente);
		btnAdicionar = (Button) dialogTreinos.findViewById(R.id.btn_adicionar_treinos);
		
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.nome_treino);
		editNomeTreino = (EditText) dialog.findViewById(R.id.txt_NomeTreino);
		txtCodigoTreino = (TextView) dialog.findViewById(R.id.txt_codigoTreino);
		btnCancelar = (Button) dialog.findViewById(R.id.btn_cancelarNome);
		btnSalvar = (Button) dialog.findViewById(R.id.btn_confirmarNome);
		editNomeFicha = (EditText) findViewById(R.id.edt_nomeFicha);
		editAntecedencia = (EditText) findViewById(R.id.edt_antecedencia);
		editDuracaoFicha = (EditText) findViewById(R.id.edt_duracaodias);
		cbxObjetivo = (Spinner) findViewById(R.id.cbx_fichaObjetivo);
		criarCombo();
		long i  = (Long) getIntent().getExtras().getSerializable("ficha");
		ControleFicha controle = new ControleFicha();
		try {
			ficha = controle.buscarFichaCodigo(i);
			if (ficha.getAtual() == 1){
				editDuracaoFicha.setEnabled(false);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		btnCancelar.setOnClickListener(this);
		btnSalvar.setOnClickListener(this);
		btnAdicionar.setOnClickListener(this);
		listaAdicao = new ArrayList<Treino>();
		
		listTreinos = getListView(); 

		preencherFicha(ficha);

		listTreinos.setOnItemClickListener(this);
		listTreinos.setOnItemLongClickListener(this);
		listaExistente.setOnItemClickListener(this);
	}


	@Override
	public DragSortListView getListView() {
		return (DragSortListView) super.getListView(); 

	} 



	private void criarCombo(){
		listaObjetivo = new ArrayList<String>();

		String o = Objetivo.CONDICIONAMENTO.getObjetivo();
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
		hospedeiro = (TabHost) findViewById(R.id.hostfichatreino);
		hospedeiro.setup();

		specficha = hospedeiro.newTabSpec("tabfichas");
		specficha.setContent(R.id.tabfichas);
		specficha.setIndicator("Ficha");
		hospedeiro.addTab(specficha);

		spectreino = hospedeiro.newTabSpec("tabfichatreinos");
		spectreino.setContent(R.id.tabfichatreinos);
		spectreino.setIndicator("Treinos");
		hospedeiro.addTab(spectreino);
	}

	private void preencherFicha(Ficha f ){
		if (f.getCodigo() != 0){
			editNomeFicha.setText(f.getNome());
			editDuracaoFicha.setText(String.valueOf(f.getDuracao()));
			editAntecedencia.setText(String.valueOf(f.getAntecedencia()));
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
			int valor = 0;
			ficha.setNome(editNomeFicha.getText().toString());
			if(editDuracaoFicha.getText().toString().
					equalsIgnoreCase("")){
				String mensagem = "Duração é obrigatoria!";
				throw new Exception(mensagem);
			}
			if(!editAntecedencia.getText().toString().equalsIgnoreCase("")){
				ficha.setAntecedencia(Integer.parseInt(editAntecedencia.getText().toString()));
			}
			ficha.setDuracao(Integer.parseInt
					((editDuracaoFicha.getText().toString())));
			ficha.setObjetivo(cbxObjetivo.getSelectedItem().toString());
	
		return ficha;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuInflater inflate = getMenuInflater();
		int tab = hospedeiro.getCurrentTab();
		if (tab == 0){
			menu.clear();
			inflate.inflate(R.menu.menu_manipular_ficha, menu);

		}else{
			menu.clear();
			inflate.inflate(R.menu.menu_ficha_treino, menu);
		}    
		return super.onPrepareOptionsMenu(menu);
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		String mensagem = "";
		switch (item.getItemId()) {
		case R.id.novo_treino:
			if(ficha.getCodigo() == 0 ){
				mensagem = "Primeiro salve as informações da sua ficha";
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
	/**
	 * criação da lista de fichas que possui treino 
	 */
	private void criarDialogFicha() {
		String mensagem = "";
		if(ficha.getCodigo() != 0){
			ControleFicha controle = new ControleFicha();
			try {
				listaFichaExistente =
						controle.buscarFichaDiferente
									(ficha.getCodigo());
				Builder alertaFicha = new AlertDialog.Builder(this);
				alertaFicha.setTitle("Selecione uma ficha");
				int selected = -1;
				int cont = 0;
				fichas = new String[listaFichaExistente.size()];
				for(Ficha f : listaFichaExistente){
					fichas[cont] = f.getNome();
					cont ++;
				}
				alertaFicha.setSingleChoiceItems(fichas,selected, this);
				alertaFicha.show();
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

	private void listarTreinoExistente(Ficha f) {
		try {
			String ntreinos = "";
			String titulo = "";
			ControleTreino controle = new ControleTreino();
			listaAdicao.clear();
			treinos = controle.buscarTreinoValido(f.getCodigo());
			List<String> nomeTreinos = new ArrayList<String>();
			titulo = "Treinos validos";
			dialogTreinos.setTitle(titulo);
			for(Treino t : treinos){
				ntreinos= t.getNome();
				nomeTreinos.add(ntreinos);
			}
			adapterExistente = new ArrayAdapter<String>(this,		
					R.layout.multiple_choice,
					nomeTreinos);
			listaExistente.setAdapter(adapterExistente);
			listaExistente.setCacheColorHint(Color.TRANSPARENT);
			dialogTreinos.show();
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
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
	public void onClick(DialogInterface dialog, int clicked) {
		String mensagem = "";
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
				Ficha ficha = null;
				if(fichas != null){
					String nome = fichas[clicked];
					for(Ficha f : listaFichaExistente){
						if(f.getNome().equalsIgnoreCase(nome.trim())){
							ficha = f;
							break;
						}
					}
				}
				listarTreinoExistente(ficha);
			break;


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



	

	private void iniciarFichaSerie(AdapterView<?> parent, int pos) {
		String item = (String) parent.getItemAtPosition(pos);
		Treino treino = null;
		
		for (Treino t : ficha.getTreinos()){
			if (item.equalsIgnoreCase(t.getNome())){
				treino = t;
				break;
			}
		}
		Intent i = new Intent(this,GUIFichaSerie.class);
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
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
			if(parent.getId() == listTreinos.getId()){
				iniciarFichaSerie(parent, pos);
			}else if (parent.getId() == listaExistente.getId()){
				CheckedTextView c = (CheckedTextView) view;
				boolean selecionado = c.isChecked();
				String nome = parent.getItemAtPosition(pos).toString();
				Treino treino = new Treino();
				for(Treino f : treinos){
					if(f.getNome().equalsIgnoreCase(nome)){
						treino = f; 
						break;
					}
					
				}
				if(android.os.Build.VERSION.SDK_INT <= 11){
					if (!listaAdicao.contains(treino)
							&& !selecionado){
						listaAdicao.add(treino);
					}else{
						listaAdicao.remove(treino);
					}	
				}else{
				if (!listaAdicao.contains(treino)
						&& selecionado){
					listaAdicao.add(treino);
				}else{
					listaAdicao.remove(treino);
				}
				}
			}
			
	}

	@Override
	public void onClick(View v) {
		String mensagem = "";
		switch (v.getId()) {
		case R.id.btn_confirmarNome:
			
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
			
			break;

		case R.id.btn_cancelarNome:
			dialog.dismiss();
			break;
		
		case R.id.btn_adicionar_treinos:
				mensagem = adicionarTreinoExistente();
			break;
		}
		
		if(!mensagem.equalsIgnoreCase("")){
			Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
		}
		
	}


	private String adicionarTreinoExistente() {
		ControleTreino controle = new ControleTreino();
		String mensagem = "Selecione algum treino !";
		if(listaAdicao.size()>0){
			try {
				mensagem = controle.adicionarTreinoExistentes
				(ficha.getTreinos(),listaAdicao,ficha.getCodigo());
			    ficha.setTreinos(controle.listarTreinos(ficha.getCodigo()));
			    dialogTreinos.dismiss();
			} catch (Exception e) {
				mensagem = e.getMessage();
			}
		}
		
		createListView(ficha.getTreinos());
		return mensagem;
		
	}


}
