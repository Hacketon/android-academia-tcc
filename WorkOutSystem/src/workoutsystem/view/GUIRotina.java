package workoutsystem.view;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import workoutsystem.control.ControleFicha;
import workoutsystem.control.ControleRotina;
import workoutsystem.control.ControleSerie;
import workoutsystem.control.ControleTreino;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Ficha;
import workoutsystem.model.Realizacao;
import workoutsystem.model.Serie;
import workoutsystem.model.Treino;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class GUIRotina extends Activity 
implements View.OnClickListener,AdapterView.OnItemClickListener,
DialogInterface.OnClickListener{

	private TabHost hostrotina;
	private TabSpec spechistorico;
	private TabSpec spectreino;
	private Calendar data;
	private TextView textomes;
	private TextView ultimaData;
	private TextView ultimoTreino;
	private Spinner comboTreinos;  
	private TextView txtNomeFicha;
	private List<Treino> listaTreinos;
	private Ficha ficha;
	private ProgressBar conclusao;
	private TextView ultimaFicha;
	private Dialog dialogPreview;
	private ListView listaExercicios;
	private ArrayAdapter<String> adapter;
	private ListView listaRealizacao;
	private int mesAtual;
	private int anoAtual;
	private TextView conclusaoTexto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotina);
		conclusao = (ProgressBar) findViewById(R.id.conclusao_rotina);
		conclusaoTexto = (TextView) findViewById(R.id.conclusao_texto);
		ultimaData = (TextView) findViewById(R.id.ultimo_data);
		textomes = (TextView) findViewById(R.id.txt_mes);
		ultimoTreino = (TextView) findViewById(R.id.ultimo_treino);
		ultimaFicha = (TextView) findViewById(R.id.ultimo_ficha);
		comboTreinos = (Spinner) findViewById(R.id.combo_treinos);
		listaRealizacao = (ListView) findViewById(R.id.lista_historicoRealizacao);
		txtNomeFicha = (TextView) findViewById(R.id.nome_ficha_atual);
		dialogPreview = new Dialog(this);
		dialogPreview.setContentView(R.layout.gerar_preview);
		listaExercicios = (ListView) dialogPreview.findViewById(R.id.lista_preview);
		data = Calendar.getInstance();
		mesAtual = data.get(Calendar.MONTH);
		anoAtual = data.get(Calendar.YEAR);
		init();
		criarTab();

	}

	private void init() {
		ControleRotina controleRotina = new ControleRotina();
		ControleFicha controleFicha = new ControleFicha();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String texto = "";
		try {
			textomes.setText(android.text.format.DateFormat.format("MMMM - yyyy", data));
			selecionarFichaAtual();
			atualizarHistorico();
			long progresso = controleRotina.calcularConclusao();
			conclusao.setProgress((int)progresso);
			conclusaoTexto.setText(texto);
			texto = "Conclusão " + " ( "+ progresso + "% ) ";
			conclusaoTexto.setText(texto);
			String mensagem =controleFicha.calcularRestante();
			Realizacao realizacao = 
					controleRotina.buscarUltimoTreinoRealizado();
			ultimoTreino.setText(realizacao.getTreino().getNome());
			ultimaData.setText(sdf.format(realizacao.getData()));
			ultimaFicha.setText(realizacao.getFicha().getNome());
			if(!mensagem.equalsIgnoreCase("")){
				Toast.makeText(this,mensagem, Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			//Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

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

		try {
			criarCombo(ficha);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		init();
	}

	private void createListView(List<Realizacao> lista) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<String> listaRealizacaoString = new ArrayList<String>();
		for(Realizacao r: lista){
			String s = sdf.format(r.getData()); 
			String item = "Treino: " + r.getTreino().getNome() + "\n"
			+ "Ficha:  "+ r.getFicha().getNome() + "\n"
			+ "Data: " + s  ;
			listaRealizacaoString.add(item);
		}
		adapter =  new ArrayAdapter<String>
			(this, R.layout.itens_simples_2 , listaRealizacaoString );
		listaRealizacao.setAdapter(adapter);
		listaRealizacao.setCacheColorHint(Color.TRANSPARENT);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflate = getMenuInflater();
		inflate.inflate(R.menu.menu_rotina, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		String mensagem = "";
		try {
			switch(item.getItemId()) {
			case R.id.realizar_rotina:
				validarRealizacao();
				break;
			case R.id.preview_rotina:
				criarListPreview();
				break;
			}
		} catch (Exception e) {
			mensagem = e.getMessage();
		}
		if(!mensagem.equalsIgnoreCase("")){

			Toast.makeText(this,mensagem ,
					Toast.LENGTH_LONG).show();
		}
		return true;
	}


	@Override
	public void onClick(View evento) {
		switch (evento.getId()) {
		case (R.id.btn_proximomes):
			atualizarProximo();
		break;
		case (R.id.btn_anteriormes):
			atualizarAnterior();
		break;

		}

	}

	private void inicializarTelaExecucao(Treino treino){
		Intent i = new Intent(this, GUIRotinaExecucao.class);
		i.putExtra("treino", treino);
		startActivity(i);
	}
	private void validarRealizacao() throws Exception {
		String nome = "";
		ControleRotina controle = new ControleRotina();
		String mensagem = "Cadastre treinos validos na ficha !";
		if(comboTreinos.getCount()>0){
			Treino treino = null;
			Realizacao resultado = controle.buscarTreinoIniciado();
			nome = comboTreinos.getSelectedItem().toString();
			for(Treino t : listaTreinos){
				if( t.getNome().equalsIgnoreCase(nome)){
					treino = t;
					break;
				}
			}
			//treino.setSerie(controle.listarRealizacaoSerie(treino.getCodigo()));
			if(treino != null){
				if(resultado.getCodigo() != 0){
					if(treino.getCodigo() != resultado.getCodigo() 
							&& !treino.getSerie().isEmpty() ){
						criarCaixa(resultado.getTreino().getNome(),"Confirmação","O treino ",
								"Não","Sim", " ja esta iniciado . Começar um novo?");
					}else{
						inicializarTelaExecucao(treino);
					}
				}else{
					inicializarTelaExecucao(treino);
				}
				
			}else{
				throw new Exception(mensagem);
			}
		}else{
			throw new Exception(mensagem);
		}
	}
	
	@Override
	public void onClick(DialogInterface dialog, int clicked) {
		try{
			ControleRotina controle = new ControleRotina();
			Realizacao resultado = controle.buscarTreinoIniciado();
			Treino treino = null;
			switch (clicked) {
			case DialogInterface.BUTTON_NEGATIVE:
				treino = resultado.getTreino();
				controle.removerTudoRealizacaoSerie();
				break;	
			case DialogInterface.BUTTON_POSITIVE:
				String nome = comboTreinos.getSelectedItem().toString();
				controle.atualizarRealizacao(1,0);
				for(Treino t : listaTreinos){
					if( t.getNome().equalsIgnoreCase(nome)){
						treino = t;
						break;
					}
				}
				break;
			
			}
			
			inicializarTelaExecucao(treino);
	
		}catch (Exception e) {
			// TODO: handle exception
		}
		
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


	//combo de treinos da ficha

	public void criarCombo(Ficha ficha) throws SQLException{
		if(ficha!= null){
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

	}



	public void criarListPreview() throws Exception{
		String mensagem ="";
		String nome = "";
		Treino treino = new Treino();
		List<String> lista = new ArrayList<String>();

		if(ficha != null){
			if(ficha.getTreinos().size() > 0 && 
					comboTreinos.getCount()>0){
				nome = comboTreinos.getSelectedItem().toString();
				for(Treino t : listaTreinos){
					if( t.getNome().equalsIgnoreCase(nome)){
						treino = t;
						break;
					}
				}
				for(Serie s : treino.getSerie()){
					String exercicio = s.getExercicio().getNome();
					if(!lista.contains(exercicio)){
						lista.add(exercicio);
					}
				}

				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.itens_simple_lista, lista);
				listaExercicios.setAdapter(adapter);
				listaExercicios.setCacheColorHint(Color.TRANSPARENT);
				dialogPreview.setTitle(treino.getNome());
				dialogPreview.show();


			}else{
				mensagem = "Cadastre treinos validos na ficha !";
				throw new Exception(mensagem);
			}

		}else{
			mensagem = "Primeiro selecione uma ficha !";
			throw new Exception(mensagem);
		}

	}


	// ficha atual	
	private Ficha selecionarFichaAtual() {
		ControleFicha controle = new ControleFicha();
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


	/**
	 * Metodo responsavel por pegar o proximo data.
	 * 
	 */
	public void atualizarProximo(){

		int mes =data.get(Calendar.MONTH);
		int ano = data.get(Calendar.YEAR);
		if(mes == mesAtual && ano == anoAtual){
			textomes.setText
			(android.text.format.DateFormat.format("MMMM - yyyy", data));
		}else{
			if (data.get(Calendar.MONTH) == 
				data.getActualMaximum(Calendar.MONTH)){
				data.set((data.get(Calendar.YEAR)+1),
						data.getActualMinimum(Calendar.MONTH),1);
			}else{
				data.set(Calendar.MONTH,data.get(Calendar.MONTH)+1);
			}
			textomes.setText(
					android.text.format.DateFormat.format
					("MMMM - yyyy", data));	
		}

		atualizarHistorico();
	}

	public void atualizarHistorico(){
		String mensagem = "";
		ControleRotina controle = new ControleRotina();
		try {
			List<Realizacao> historicoMensal = controle.buscarHistoricoMensal(data);
			createListView(historicoMensal);
		} catch (Exception e) {
			mensagem = e.getMessage();
			createListView(new ArrayList<Realizacao>());
			//Toast.makeText(this, mensagem,Toast.LENGTH_SHORT).show();

		}
	}


	/**
	 * Metodo responsavel pela atualização do  data anterior
	 */
	public void atualizarAnterior(){
		//pega o data e compara com o data minimo (janeiro) , se data atual for janeiro vai 
		// subtrair um no ano
		if (data.get(Calendar.MONTH) == data.getActualMinimum(Calendar.MONTH)){
			// vai subtrair um ano , pegar o data maximo (Dezembro) e iniciar dia 1
			data.set((data.get(Calendar.YEAR)-1),data.getActualMaximum(Calendar.MONTH) , 1);
		}else{
			data.set(Calendar.MONTH,data.get(Calendar.MONTH)-1);
		}
		textomes.setText(android.text.format.DateFormat.format("MMMM - yyyy", data));
		atualizarHistorico();
	}
	/*
		@Override
		protected void onRestart() {
			init();
			super.onRestart();
		}
	 */
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
	//		
	//
	//	}


}
