package workoutsystem.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import workoutsystem.control.ControleExercicio;
import workoutsystem.control.ControleFicha;
import workoutsystem.control.ControleTreino;
import workoutsystem.model.Serie;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.ItemListaHistorico;
import workoutsystem.utilitaria.Unidade;
import android.app.Dialog;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.DropListener;
import com.mobeta.android.dslv.DragSortListView.RemoveListener;

public class GUIFichaTreino 
extends ListActivity
implements
AdapterView.OnItemSelectedListener,
ListView.OnItemClickListener,
ListView.OnItemLongClickListener,
View.OnClickListener,
RemoveListener,
DropListener {

	private TabHost host;
	private TabSpec tabEspecificacao;
	private TabSpec  tabExercicio;
	private TabSpec tabTreino;
	private Treino treino ; 
	private ListView listaBusca;
	private ListView listaExercicio;
	private Spinner cbxGrupoMuscular;
	private Spinner cbxGrupo;
	private Dialog dialogEspecificacao;
	private Spinner cbxUnidade;
	private EditText edtSeries;
	private EditText edtRepeticao;
	private List<Exercicio> listaExercicioBusca;
	private Dialog dialogNovoExercicio;
	private TextView txtCodExercicio;
	private TextView txtCodigoExercicio;
	private EditText editDescricaoExercicio;
	private TextView txtOrdem;
	private EditText editNomeExercicio;
	private Button btnCancelarExercicio;
	private Button btnSalvarExercicio;
	private Button btnConfirmar;
	private Button btnCancelar;
	private DragSortListView listaEspecificacao; 
	private List<Exercicio> listaExercicioTreino;
	private ArrayAdapter<String> adapterEspecificacao;
	private List<Exercicio> listaRemocaoExercicio;
	private ArrayList<GrupoMuscular> grupos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fichatreino);

		init();
		createTabs();
		criarComboGrupo();
		criarComboUnidade();
	}

	private void init() {
		ControleTreino controleTreino 
		= new ControleTreino();
		ControleExercicio controleExercicio 
		= new ControleExercicio();
		dialogEspecificacao = new Dialog(this);
		dialogEspecificacao.setContentView(R.layout.gerar_especificacao);


		cbxUnidade = (Spinner) dialogEspecificacao.
		findViewById(R.id.cbx_Unidade);
		btnConfirmar = (Button) dialogEspecificacao.
		findViewById(R.id.btn_Confirmar_Especficacao);
		btnCancelar = (Button) dialogEspecificacao.
		findViewById(R.id.btn_cancelar_especficacao);

		edtSeries = (EditText) dialogEspecificacao.
		findViewById(R.id.edt_series);
		edtRepeticao = (EditText) dialogEspecificacao.
		findViewById(R.id.edt_repeticao);
		txtCodigoExercicio = (TextView) dialogEspecificacao.
		findViewById(R.id.txt_codigoExercicioEspecificacao);
		txtOrdem = (TextView) dialogEspecificacao.
		findViewById(R.id.txt_ordem);

		dialogNovoExercicio = new Dialog(this);
		dialogNovoExercicio.setContentView(R.layout.criarexercicio);

		txtCodExercicio = (TextView)
		dialogNovoExercicio.findViewById(R.id.codigo_exercicio);
		cbxGrupo= (Spinner) 
		dialogNovoExercicio.findViewById(R.id.cbx_grupo);
		editDescricaoExercicio = (EditText) 
		dialogNovoExercicio.findViewById(R.id.edt_descricaoExercicio);
		editNomeExercicio = (EditText)
		dialogNovoExercicio.findViewById(R.id.edt_nomeExercicio);

		btnCancelarExercicio = (Button) 
		dialogNovoExercicio.findViewById(R.id.btn_voltar);

		btnSalvarExercicio = (Button)
		dialogNovoExercicio.findViewById(R.id.btn_criar);

		cbxGrupoMuscular = (Spinner) findViewById(R.id.cbx_buscaexercicio);
		treino = (Treino) getIntent().getExtras().getSerializable("treino");
		listaBusca = (ListView) findViewById(R.id.list_busca);
		listaExercicio = (ListView) findViewById(R.id.list_exercicio);
		listaEspecificacao = getListView();


		listaExercicio.setOnItemLongClickListener(this);
		listaBusca.setOnItemLongClickListener(this);

		listaExercicio.setOnItemClickListener(this);

		cbxGrupoMuscular.setOnItemSelectedListener(this);		
		cbxUnidade.setOnItemSelectedListener(this);

		btnCancelar.setOnClickListener(this);
		btnConfirmar.setOnClickListener(this);
		btnCancelarExercicio.setOnClickListener(this);
		btnSalvarExercicio.setOnClickListener(this);
		try {
			listaExercicioTreino =
				controleExercicio.
				listarExercicioTreino(treino.getCodigo());
			treino.setSerie
			(controleTreino.listarSerie(treino.getCodigo()));
			listaRemocaoExercicio = new ArrayList<Exercicio>();
			createListView(treino.getSerie());
			createListView(listaExercicioTreino, 
					listaExercicio,
					R.layout.multiple_choice);
			listaExercicio.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public DragSortListView getListView() {
		return (DragSortListView) super.getListView(); 

	}

	private void criarComboGrupo() {

		ArrayList<String> listaGrupos = new ArrayList<String>();
		ControleExercicio controle = new ControleExercicio();
		grupos = 
			(ArrayList<GrupoMuscular>) controle.listarGrupos(); 

		for (GrupoMuscular grupo : grupos){
			listaGrupos.add(grupo.getNome());
		}

		ArrayAdapter<String> adapter =
			new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,listaGrupos);

		adapter.
		setDropDownViewResource
		(android.R.layout.simple_list_item_multiple_choice);

		cbxGrupoMuscular.setAdapter(adapter);
		cbxGrupo.setAdapter(adapter);

	}

	private void criarComboUnidade() {
		List<String> listaUnidade = new ArrayList<String>();

		ControleExercicio controle = new ControleExercicio();

		for (Unidade unidade : Unidade.values()){
			listaUnidade.add(unidade.getUnidade());
		}

		ArrayAdapter<String> adapter =
			new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,listaUnidade);

		adapter.
		setDropDownViewResource
		(android.R.layout.simple_list_item_multiple_choice);

		cbxUnidade.setAdapter(adapter);

	}

	private void createListView(List<Serie> lista) {
		List<String> nomeTreinos = new ArrayList<String>();
		for (Serie t : lista){
			String item =  t.getOrdem() + "-" +
			t.getExercicio().getNome()+"\n" +
			"Quantidade : " + t.getQuantidade() + "\n" +
			"Unidade :" + t.getUnidade() ;

			nomeTreinos.add(item);
		}

		adapterEspecificacao = new ArrayAdapter<String>(this,
				R.layout.list_item_checkable,
				R.id.text,
				nomeTreinos);

		listaEspecificacao.setAdapter(adapterEspecificacao);
		listaEspecificacao.setRemoveListener(this);
		listaEspecificacao.setOnItemLongClickListener(this);
		listaEspecificacao.setDropListener(this);
		listaEspecificacao.setCacheColorHint(Color.TRANSPARENT);
	}


	private void createListView(
			List<Exercicio> listaExercicio,
			ListView lista,
			int layout){
		List<String> nomes = new ArrayList<String>();
		for(Exercicio e : listaExercicio){
			nomes.add(e.getNome());
		}
		ListAdapter adapter = 
			new ArrayAdapter<String>(this,
					layout,nomes);

		lista.setCacheColorHint(Color.TRANSPARENT);
		lista.setAdapter(adapter);
		lista.setOnItemLongClickListener(this);
	}



	private void createTabs() {
		host = (TabHost) findViewById(R.id.host_treino);
		host.setup();

		tabTreino = host.newTabSpec("tab_treino");
		tabTreino.setContent(R.id.tab_treino);
		tabTreino.setIndicator("Busca");
		host.addTab(tabTreino);

		tabExercicio = host.newTabSpec("tab_exercicio_treino");
		tabExercicio.setContent(R.id.tab_exercicio_treino);
		tabExercicio.setIndicator("Exercicios");
		host.addTab(tabExercicio);

		tabEspecificacao = host.newTabSpec("tab_especificacao");
		tabEspecificacao.setContent(R.id.tab_especificacao);
		tabEspecificacao.setIndicator("Series");
		host.addTab(tabEspecificacao);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_treino_ficha, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.criar_Exercicio:
			criarCaixaDialogoExercicio("Novo Exercicio");
			break;
		case R.id.remover_exercicio_ficha:
			removerExercicios();
			break;
		}
		return true;
	}

	private void criarCaixaDialogoExercicio(String titulo) {
		dialogNovoExercicio.setTitle(titulo);
		txtCodExercicio.setText("");
		editDescricaoExercicio.setText("");
		editNomeExercicio.setText("");
		txtCodExercicio.setText("");
		editNomeExercicio.requestFocus();
		dialogNovoExercicio.show();
	}

	private void criarCaixaDialogoEspecificacao(String titulo,
			long codigo) {
		dialogEspecificacao.setTitle(titulo);
		edtRepeticao.setText("");
		edtSeries.setText("");
		edtSeries.setEnabled(true);
		txtCodigoExercicio.setText(String.valueOf(codigo));
		ArrayList<String> list = new ArrayList<String>();
		for (Unidade u : Unidade.values()){
			list.add(u.getUnidade());
		}
		ArrayAdapter<String> adapter =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
		cbxUnidade.setAdapter(adapter);
		txtOrdem.setText("");
		dialogEspecificacao.show();
	}


	private void criarCaixaDialogoEspecificacao(Serie esp) {
		int aux = 0;
		int posicao = 0;
		dialogEspecificacao.setTitle(esp.getExercicio().getNome());
		edtRepeticao.setText(String.valueOf(esp.getQuantidade()));
		edtSeries.setText ("1");
		edtSeries.setEnabled(false);
		txtCodigoExercicio.setText(String.valueOf(esp.getExercicio().getCodigo()));

		ArrayList<String> list = new ArrayList<String>();
		for (Unidade u : Unidade.values()){
			list.add(u.getUnidade());
			if (u.getUnidade().equalsIgnoreCase(esp.getUnidade())){
				posicao = aux;
			}
			aux++;
		}

		ArrayAdapter<String> adapter =
			new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
		cbxUnidade.setAdapter(adapter);
		cbxUnidade.setSelection(posicao);
		txtOrdem.setText(String.valueOf(esp.getOrdem()));
		dialogEspecificacao.show();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {

		if(parent.getId() == R.id.cbx_buscaexercicio){
			String grupo = parent.getItemAtPosition(pos).toString();
			listarBusca(grupo);
		}

	}

	private void listarBusca(String grupo) {
		ControleExercicio controle = new ControleExercicio();
		GrupoMuscular grupoMuscular = new GrupoMuscular();
		try {
			for (GrupoMuscular g : grupos){
				if(g.getNome().equalsIgnoreCase(grupo)){
					grupoMuscular = g;
					break;
				}
			}

			listaExercicioBusca = 
				controle.listarExercicioDisponiveis
				(treino.getCodigo(), 
						grupoMuscular.getCodigo());

			for(Exercicio e1 : listaExercicioTreino){
				for(Exercicio e: listaExercicioBusca){
					if(e1.getCodigo()==(e.getCodigo())){
						listaExercicioBusca.remove(e);
						break;
					}

				}

			}

			createListView(listaExercicioBusca,
					listaBusca,
					R.layout.itens_simple_lista);
		}
		catch (Exception e) {
			String mensagem = e.getMessage();
			Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();

		}
	}

	private Exercicio criarExercicio(){
		Exercicio exercicio = new Exercicio();
		GrupoMuscular grupo = new GrupoMuscular();

		if (!txtCodExercicio.getText().toString().equalsIgnoreCase("")){
			exercicio.setCodigo(Long.parseLong(txtCodExercicio.getText().toString()));
		}

		exercicio.setNome(editNomeExercicio.getText().toString());
		grupo.setNome(cbxGrupo.getSelectedItem().toString());
		exercicio.setDescricao(editDescricaoExercicio.getText().toString());
		exercicio.setGrupoMuscular(grupo);

		return exercicio;
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

		/*
		 * este metodo o obtem o estado anterior da sele��o , ou seja , 
		 *  se estiver a caixa marcada ent�o o estado anterior � false  
		 */
		CheckedTextView c = (CheckedTextView) view;
		boolean selecionado = c.isChecked();
		String nome = parent.getItemAtPosition(pos).toString();
		Exercicio exercicio = new Exercicio();

		for(Exercicio e : listaExercicioTreino){
			if(e.getNome().equalsIgnoreCase(nome)){
				exercicio = e; 
				break;
			}
		}

		if (!listaRemocaoExercicio.contains(exercicio)
				&& !selecionado){
			listaRemocaoExercicio.add(exercicio);
		}else{
			listaRemocaoExercicio.remove(exercicio);
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int pos,
			long id) {
		String item = parent.getItemAtPosition(pos).toString();
		Exercicio exercicio = new Exercicio();
		if (parent.getId() == listaEspecificacao.getId()){
			Serie especificacao = getEspecificacao(item);
			criarCaixaDialogoEspecificacao (especificacao);
		}else if (parent.getId()== R.id.list_exercicio){
			for(Exercicio e : listaExercicioTreino){
				if (e.getNome().equalsIgnoreCase(item)){
					exercicio = e;
					break;
				}
			}
			criarCaixaDialogoEspecificacao(item, exercicio.getCodigo());
		}else if (parent.getId() == R.id.list_busca){
			for(Exercicio e : listaExercicioBusca){
				if (e.getNome().equalsIgnoreCase(item)){
					exercicio = e; 
					break;
				}
			}

			String mensagem = item+
			" adicionado aos exercicios";
			listaExercicioTreino.add(exercicio);
			listarBusca(exercicio.getGrupoMuscular().getNome());
			createListView(listaExercicioTreino, listaExercicio, R.layout.multiple_choice);
			Toast.makeText(this,mensagem, Toast.LENGTH_SHORT).show();
		}
		return false;
	}





	@Override
	public void onClick(View v) {
		ControleTreino controle = new ControleTreino();
		String mensagem = "";

		switch (v.getId()) {

		case R.id.btn_Confirmar_Especficacao:
			try{			
				List<Serie> esp = criarEspecificacao();
				mensagem = controle.manipularSerie(esp);
				treino.setSerie
				(controle.listarSerie(treino.getCodigo()));
				createListView(treino.getSerie());
				dialogEspecificacao.dismiss();
			} catch (Exception e) {
				mensagem = e.getMessage();

			}

			Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
			dialogEspecificacao.dismiss();
			break;
		case (R.id.btn_criar):
			salvarExercicio();
		break;
		case (R.id.btn_voltar):
			dialogNovoExercicio.dismiss();
		break;
		case (R.id.btn_cancelar_especficacao):
			dialogEspecificacao.dismiss();
		break;
		}

		}


	

	private void removerExercicios() {
		ControleTreino controleTreino = new ControleTreino();
		ControleExercicio controleExercicio	= new ControleExercicio();
		int posicao = 0;
		try {
			controleTreino.removerSerie(treino.getCodigo(),listaRemocaoExercicio);
			listaExercicioTreino.removeAll(listaRemocaoExercicio);
			listaRemocaoExercicio.clear();	
			treino.setSerie
			(controleTreino.listarSerie
					(treino.getCodigo()));
			cbxGrupoMuscular.getSelectedItem().toString();
			createListView(treino.getSerie());
			createListView(listaExercicioTreino, 
					listaExercicio, R.layout.multiple_choice);
			String grupo = cbxGrupoMuscular.getSelectedItem().toString();
			listarBusca(grupo);

		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private void salvarExercicio(){
		Exercicio e = criarExercicio();
		ControleExercicio controle = new ControleExercicio();
		String mensagem;
		try {
			mensagem = controle.manipularExercicio(e);
			GrupoMuscular grupoMuscular = new GrupoMuscular();
			String grupo =cbxGrupo.getSelectedItem().toString();
			for (GrupoMuscular g : grupos){
				if (g.getNome().equalsIgnoreCase(grupo)){
					grupoMuscular = g;
					break;
				}
			}

			listaExercicioBusca = 
				controle.listarExercicioDisponiveis
				(treino.getCodigo(), 
						grupoMuscular .getCodigo());
			atualizarCombo(e, cbxGrupoMuscular);
			createListView(listaExercicioBusca, listaBusca,
					R.layout.itens_simple_lista);
			dialogNovoExercicio.dismiss();
		} catch (Exception e1) {
			mensagem = e1.getMessage();
		}


		Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();

	}


	private void atualizarCombo(Exercicio e,Spinner combo) {
		int i = 0;
		for (GrupoMuscular l : grupos){
			if (l.getNome()
					.equalsIgnoreCase(e.getGrupoMuscular().getNome())){
				combo.setSelection(i);
				break;
			}
			i++;
		}

	}


	private List<Serie> criarEspecificacao() throws Exception{
		String serieString = edtSeries.getText().toString().trim();
		String repeticao = edtRepeticao.getText().toString().trim();
		String mensagem = "Digite os campos obrigatorios";
		List<Serie> lista = new ArrayList<Serie>();
		ControleTreino controle = new ControleTreino();

		if(serieString.equalsIgnoreCase("") || repeticao.equalsIgnoreCase("")){
			throw new Exception(mensagem);
		}else{
			int serie = Integer.parseInt(serieString);
			Exercicio e = new Exercicio();
			e.setCodigo(Long.parseLong
					(txtCodigoExercicio.getText().toString()));
			while(serie > 0){
				Serie esp = new Serie();
				esp.setCodigoTreino(treino.getCodigo());
				esp.setExercicio(e);
				esp.setQuantidade(Integer.parseInt
						(edtRepeticao.getText().toString().trim()));
				esp.setCodigoTreino(treino.getCodigo());
				esp.setUnidade(cbxUnidade.getSelectedItem().toString());
				if(!txtOrdem.getText().toString().equalsIgnoreCase("")){
					esp.setOrdem(Integer.parseInt
							(txtOrdem.getText().toString().trim()));
				}
				lista.add(esp);
				serie--;
			}


		}


		return lista;


	}

	@Override
	public void drop(int from, int to) {
		if (from != to) {
			ControleTreino controle = new ControleTreino();
			DragSortListView list = getListView();
			String item = adapterEspecificacao.getItem(from);
			adapterEspecificacao.remove(item);
			adapterEspecificacao.insert(item, to);
			list.moveCheckState(from, to);
			try {
				controle.reordenarLista(from,to,treino.getCodigo());
			} catch (Exception e) {
				String mensagem = e.getMessage();
				Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
			}
		}
	}

	


	@Override
	public void remove(int which) {
		String item = adapterEspecificacao.getItem(which);
		String mensagem = "";
		ControleTreino controle = new ControleTreino();
		Serie esp = getEspecificacao(item);
		try {
			mensagem = controle.removerSerie(treino.getCodigo(), esp.getOrdem());
		} catch (Exception e) {
			mensagem = e.getMessage();
		}

		Toast.makeText(this,mensagem, Toast.LENGTH_SHORT).show();

	}

	private Serie getEspecificacao(String item) {
		Serie esp = new Serie();
		String[] sordem = item.split("-");
		long ordem = Long.parseLong(sordem[0]
		                                   .toString());
		for (Serie es : treino.getSerie()){
			if(ordem == es.getOrdem()){
				esp = es;
				break;
			}
		}
		return esp;
	}


}
