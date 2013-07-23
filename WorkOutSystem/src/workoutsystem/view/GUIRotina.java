package workoutsystem.view;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import workoutsystem.control.ControleExercicio;
import workoutsystem.control.ControleFicha;
import workoutsystem.control.ControleSerie;
import workoutsystem.control.ControleTreino;
import workoutsystem.dao.ITreinoDao;
import workoutsystem.dao.SerieDao;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Ficha;
import workoutsystem.model.Frequencia;
import workoutsystem.model.Grupo;
import workoutsystem.model.Realizacao;
import workoutsystem.model.Serie;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.AdaptadorCalendario;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.TabHost.TabSpec;

public class GUIRotina extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

	private TabHost hostrotina;
	private TabSpec spechistorico;
	private TabSpec spectreino;
	private Spinner cbxDiaSemana;
	private Calendar mes;
	private Calendar dia;
	private TextView textomes;
	private TextView ultimaData;
	private TextView grupoMuscular;
	private TextView ultimoTreino;
	private Spinner comboTreinos;  
	private TextView txtNomeFicha;
	private List<Treino> listaTreinos;
	private Ficha ficha;
	private TextView ultimaFicha;
	private Dialog dialogPreview;
	private ListView listaExercicios;
	private ArrayAdapter<String> adapter;
	private TextView treinoPreview;
	private ListView listaRealizacao;
	private List<String> listaRealizacaoString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotina);

		cbxDiaSemana = (Spinner)findViewById(R.id.combo_treinos);
		ultimaData = (TextView) findViewById(R.id.ultimo_data);
		ultimoTreino = (TextView) findViewById(R.id.ultimo_treino);
		ultimaFicha = (TextView) findViewById(R.id.ultimo_ficha);
		comboTreinos = (Spinner) findViewById(R.id.combo_treinos);

		listaRealizacaoString = new ArrayList<String>();
		listaRealizacao = (ListView) findViewById(R.id.lista_historicoRealizacao);


		//		mes = Calendar.getInstance();
		dia = Calendar.getInstance();

		dia.get(Calendar.DAY_OF_WEEK);

		//diaSemana.setText(android.text.format.DateFormat.format("EEEEE", dia));

		//		adapter = new AdaptadorCalendario(mes,this);

		//		textomes.setText(android.text.format.DateFormat.format("MMMM yyyy", mes));

		//		gradedias = (GridView) findViewById(R.id.calendariogrid);
		//		gradedias.setAdapter(adapter);
		//		gradedias.setOnItemClickListener(this);

		txtNomeFicha = (TextView) findViewById(R.id.nome_ficha_atual);


		dialogPreview = new Dialog(this);
		dialogPreview.setContentView(R.layout.gerar_preview);
		listaExercicios = (ListView) dialogPreview.findViewById(R.id.lista_preview);
		//treinoPreview = (TextView) dialogPreview.findViewById(R.id.txt_preview);

		init();
		criarTab();


	}
	private void init() {
		ITreinoDao dao = new TreinoDao();
		SerieDao daoSerie = new SerieDao();
		List<Realizacao> lista = new ArrayList<Realizacao>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		try {
			lista = daoSerie.listarHistoricoRealizacaoSerie();
			createListView(lista);
			Realizacao realizacao = dao.buscarUltimoTreinoRealizado();
			//if(realizacao.getCodigo()!=0){
				ultimoTreino.setText(realizacao.getTreino().getNome());
				ultimaData.setText(sdf.format(realizacao.getData()));
				ultimaFicha.setText(realizacao.getFicha().getNome());	
			//}
			selecionarFichaAtual();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	protected void onResume() {
//		super.onResume();
//		init();	
//	}
	/**
	 * Metodo de criação das tab spec e tab host
	 */
	public void criarTab(){

		hostrotina = (TabHost) findViewById(R.id.hostrotina);
		hostrotina.setup();

		spectreino = hostrotina.newTabSpec("tabrotina");
		spectreino.setContent(R.id.tabtreino);
		spectreino.setIndicator("Treinos");
		hostrotina.addTab(spectreino);

		spechistorico = hostrotina.newTabSpec("tabchistorico");
		spechistorico.setContent(R.id.tabhistoricoRotina);
		spechistorico.setIndicator("Histórico");
		hostrotina.addTab(spechistorico);

		ficha = new Ficha();
		ficha = selecionarFichaAtual();
		try {
			criarCombo(ficha);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private void createListView(List<Realizacao> lista) {

		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		for(Realizacao r: lista){

			String s = sdf.format(r.getData()); 
			
			String item = "Treino: " + r.getTreino().getNome() + "\n"
			+ "Ficha:  "+ r.getFicha().getNome() + "\n"
			+ "Data: " + s  ;



			listaRealizacaoString.add(item);

		}


		adapter =  new ArrayAdapter<String>(this, R.layout.itens_simple_lista , listaRealizacaoString );


		listaRealizacao.setAdapter(adapter);
		listaRealizacao.setCacheColorHint(Color.TRANSPARENT);


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflate = getMenuInflater();
		inflate.inflate(R.menu.menu_rotina, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch(item.getItemId()) {

		case R.id.realizar_rotina:

			inicializarTelaRealizacao();

			break;
		case R.id.preview_rotina:
			criarListPreview();

			break;
		}
		return true;
	}


	@Override

	public void onClick(View evento) {
		//		switch (evento.getId()) {
		//
		//		case (R.id.btn_proximomes):
		//			atualizarProximo();
		//
		//		break;
		//
		//		case (R.id.btn_anteriormes):
		//
		//			atualizarAnterior();
		//
		//		break;
		//
		//		}

	}


	private void inicializarTelaRealizacao() {
		String nome = comboTreinos.getSelectedItem().toString();
		Treino treino = new Treino();

		for(Treino t : listaTreinos){
			if( t.getNome().equalsIgnoreCase(nome)){
				treino = t;
				break;
			}
		}

		Intent i = new Intent(this, GUIExecutaFicha.class);
		i.putExtra("treino", treino);
		startActivity(i);

	}

	//combo de treinos da ficha

	public void criarCombo(Ficha ficha) throws SQLException{
		listaTreinos = new ArrayList<Treino>();
		ControleTreino controleTreino = new ControleTreino();
		ControleSerie controleSerie = new ControleSerie();
		TreinoDao dao = new TreinoDao();
		List<String> lista = new ArrayList<String>();
		listaTreinos = controleTreino.listarTreinos(ficha.getCodigo());

		for(Treino te : listaTreinos){
			te.setSerie(controleSerie.listarSerie(te.getCodigo()));
			if(te.getSerie().size() != 0) {
				lista.add(te.getNome());
			}
		}

		ArrayAdapter<String> adapter =	new ArrayAdapter<String>
		(this,android.R.layout.simple_spinner_item,lista);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
		comboTreinos.setAdapter(adapter);

	}


	public void criarListPreview(){
		//pegando treino selecionado
		String nome = comboTreinos.getSelectedItem().toString();
		Treino treino = new Treino();

		for(Treino t : listaTreinos){
			if( t.getNome().equalsIgnoreCase(nome)){
				treino = t;
				break;
			}
		}

		//criar lista de exercicios do treino selecionado

		List<String> lista = new ArrayList<String>();

		String aux ="";

		for(Serie s : treino.getSerie()){
			String exercicio = s.getExercicio().getNome();

			if(aux.equalsIgnoreCase("")){
				lista.add(exercicio);
				aux = exercicio;	
			}
			if(!exercicio.equalsIgnoreCase(aux)){
				lista.add(exercicio);
				aux = exercicio;
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.itens_simple_lista, lista);

		listaExercicios.setAdapter(adapter);
		listaExercicios.setCacheColorHint(Color.TRANSPARENT);
		//treinoPreview.setText("Exercicios -" + treino.getNome().toString());		

		dialogPreview.setTitle(treino.getNome());
		dialogPreview.show();

	}


	// ficha atual	
	private Ficha selecionarFichaAtual() {
		ControleFicha controle = new ControleFicha();
		Ficha ficha = null;
		String nome = "Nenhuma";
		try {
			ficha= controle.buscarFichaAtual();
			txtNomeFicha.setText(ficha.getNome());
		} catch (Exception e) {
			String message = e.getMessage();
			txtNomeFicha.setText(nome);
			Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		}

		return ficha;

	}


	public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {
		TextView textofrequencia = (TextView) view.findViewById(R.id.text_frequencia);
		String dia = (String) textofrequencia.getText();
		if (!dia.equalsIgnoreCase("")){
			startActivity(new Intent("workoutsystem.view.EXEMPLO"));
		}

	}

	//	/**
	//	 * Metodo responsavel por pegar o proximo mes.
	//	 * 
	//	 */
	//	public void atualizarProximo(){
	//		if (mes.get(Calendar.MONTH) == mes.getActualMaximum(Calendar.MONTH)){
	//			mes.set((mes.get(Calendar.YEAR)+1), mes.getActualMinimum(Calendar.MONTH),1);
	//		}else{
	//			mes.set(Calendar.MONTH,mes.get(Calendar.MONTH)+1);
	//		}
	//		atualizarCalendario();
	//	}
	//
	//
	//	/**
	//	 * Atualização dos dados do Calendario na tela , chamando notifyDataSetChanged para renderizar os dados
	//	 */
	//	public void atualizarCalendario() {
	//
	//		TextView mesView = (TextView) findViewById(R.id.txt_mes);
	//		adapter.atualizarDias();
	//		adapter.notifyDataSetChanged();
	//		mesView.setText(android.text.format.DateFormat.format("MMMM yyyy", mes));
	//
	//	}
	//	/**
	//	 * Metodo responsavel pela atualização do  mes anterior
	//	 */
	//	public void atualizarAnterior(){
	//		//pega o mes e compara com o mes minimo (janeiro) , se mes atual for janeiro vai 
	//		// subtrair um no ano
	//		if (mes.get(Calendar.MONTH) == mes.getActualMinimum(Calendar.MONTH)){
	//			// vai subtrair um ano , pegar o mes maximo (Dezembro) e iniciar dia 1
	//			mes.set((mes.get(Calendar.YEAR)-1),mes.getActualMaximum(Calendar.MONTH) , 1);
	//		}else{
	//			mes.set(Calendar.MONTH,mes.get(Calendar.MONTH)-1);
	//		}
	//		atualizarCalendario();
	//	}



}
