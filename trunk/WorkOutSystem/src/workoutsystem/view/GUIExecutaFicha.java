package workoutsystem.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleRotina;
import workoutsystem.control.ControleSerie;
import workoutsystem.model.Serie;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Unidade;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GUIExecutaFicha extends Activity implements 
ListView.OnItemLongClickListener,
ListView.OnItemClickListener ,
View.OnClickListener,DialogInterface.OnClickListener{

	private ListView listaSerie; 
	private ArrayAdapter<String> adapterSerie;
	private Treino treino;
	private TextView treinoDia;
	private Dialog dialogEspecificacao;
	private Spinner cbxUnidade;
	private Button btnConfirmar;
	private Button btnCancelar;
	private EditText edtSeries;
	private EditText edtRepeticao;
	private EditText edtCarga;
	private TextView txtCodigoExercicio;
	private TextView txtOrdem;
	private Serie especificacao;
	private List<String> series ;
	private List<Serie> seriesRealizadas;
	private List<Serie> seriesTreino;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_exercicios);

		// Refatorando para controle de rotina , tudo que envolvido a realização!
		//refatorar para controle serie
		treino = (Treino) getIntent().getExtras().getSerializable("treino");
		ControleRotina controleRotina = new ControleRotina();
		//refatorar para controleSerie
		// verificar se treino iniciado é o mesmo que foi selecionado atualmente
		int treinoIniciado = 0;
		try {
			treinoIniciado = controleRotina.buscarTreinoIniciado();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if(treino.getCodigo() != treinoIniciado){
			try {
				controleRotina.removerTudoRealizacaoSerie();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		//listando realizacao serie

		try {
			seriesTreino = controleRotina.listarRealizacaoSerie();
		} catch (SQLException e) {
			e.printStackTrace();
		}




		if(seriesTreino.size() == 0){

			for(Serie s: treino.getSerie()){
				try {
					controleRotina.inserirRealizacaoSerie(s);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				seriesTreino = controleRotina.listarRealizacaoSerie();
			} catch (SQLException e) {
				e.printStackTrace();
			}


			Toast.makeText(this,"Iniciando: " + treino.getNome() ,
					Toast.LENGTH_LONG).show();
		}



		init();
	}

	private void init(){



		listaSerie = (ListView) findViewById(R.id.lista_realizarexercicio);

		treinoDia = (TextView) findViewById(R.id.txt_treino);

		listaSerie.setOnItemLongClickListener(this);
		listaSerie.setOnItemClickListener(this);


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
		edtCarga = (EditText) dialogEspecificacao.
		findViewById(R.id.edt_carga);
		txtCodigoExercicio = (TextView) dialogEspecificacao.
		findViewById(R.id.txt_codigoExercicioEspecificacao);
		txtOrdem = (TextView) dialogEspecificacao.
		findViewById(R.id.txt_ordem);

		btnCancelar.setOnClickListener(this);
		btnConfirmar.setOnClickListener(this);
		series = new ArrayList<String>();
		seriesRealizadas = new ArrayList<Serie>();


		createListView(seriesTreino);
		treinoDia.setText(treino.getNome());




	}

	private void createListView(List<Serie> lista) {

		for(Serie s: lista){

			String item = s.getOrdem() + "-" +
			s.getExercicio().getNome()+"\n" +
			"Quantidade : " + s.getQuantidade() +"\n" +
			"Unidade : " + s.getUnidade() + "\n" + 
			"Carga : " + s.getCarga() ;

			series.add(item);

		}


		adapterSerie =  new ArrayAdapter<String>(this, R.layout.multiple_choice_serie, series );


		listaSerie.setAdapter(adapterSerie);
		listaSerie.setOnItemLongClickListener(this);
		listaSerie.setCacheColorHint(Color.TRANSPARENT);


	}


	public void onClick(View evento) {
		switch (evento.getId()) {

		case R.id.btn_Confirmar_Especficacao:
			String mensagem = "";
			ControleSerie controle = new ControleSerie();
			Double carga = Double.parseDouble(edtCarga.getText().toString());

			mensagem = controle.alterarCarga(carga, especificacao.getCodigo());


			Toast.makeText(this,mensagem , Toast.LENGTH_LONG).show();
			dialogEspecificacao.dismiss();
			init();		

			break;
		case R.id.btn_cancelar_especficacao:

			dialogEspecificacao.dismiss();

			break;
		}
	} 


	public void criarCaixaAlteracaoSerie(Serie esp){
		int aux = 0;
		int posicao = 0;
		dialogEspecificacao.setTitle("Alterar Carga");
		edtCarga.setText(String.valueOf(esp.getCarga()));
		edtRepeticao.setText(String.valueOf(esp.getQuantidade()));
		edtSeries.setText ("1");
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

		edtRepeticao.setEnabled(false);
		edtSeries.setEnabled(false);
		cbxUnidade.setEnabled(false);

		dialogEspecificacao.show();


	}



	@Override
	public boolean onItemLongClick(AdapterView<?> parent , View view, int pos,
			long id) {
		String item = parent.getItemAtPosition(pos).toString();
		especificacao = getSerie(item);

		criarCaixaAlteracaoSerie(especificacao);

		return false;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

		CheckedTextView c = (CheckedTextView) view;
		boolean selecionado = c.isChecked();
		String nomeSerie = parent.getItemAtPosition(pos).toString();
		Serie serie = new Serie();

		for(Serie s : seriesTreino){

			String item = s.getOrdem() + "-" +
			s.getExercicio().getNome()+"\n" +
			"Quantidade : " + s.getQuantidade() +"\n" +
			"Unidade : " + s.getUnidade() + "\n" + 
			"Carga : " + s.getCarga() ;

			if(item.equalsIgnoreCase(nomeSerie)){
				serie = s; 
				break;
			}

		}
		seriesRealizadas.add(serie);


	}

	public void finalizarSeries() throws Exception{

		//refatorar treino serie
		ControleRotina controleRotina = new ControleRotina();

		for(Serie s : seriesRealizadas){
			controleRotina.removerRealizacaoSerie(s);
			controleRotina.inserirRealizacao(s, treino.getCodigoFicha());
		}

		seriesTreino = controleRotina.listarRealizacaoSerie();	

		init();

	}


	private Serie getSerie(String item) {
		Serie esp = new Serie();
		String[] sordem = item.split("-");
		long ordem = Long.parseLong(sordem[0]
		                                   .toString());
		for (Serie es : seriesTreino){
			if(ordem == es.getOrdem()){
				esp = es;
				break;
			}
		}
		return esp;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_realizar_exercicio, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {

		case R.id.finalizar_multiplos:
			try {
				finalizarSeries();
			} catch (Exception e) {
				e.printStackTrace();
			}


			break;

		case R.id.finalizar_treino:

			
			
			try {
				finalizarSeries();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if(seriesTreino.size() > 0){
				construirCaixa();
			}
			
			break;
		}
		return false;
	}

	public void finalizarTudo() throws SQLException{
		ControleRotina controleRotina = new ControleRotina();
		controleRotina.removerTudoRealizacaoSerie();
		seriesTreino = controleRotina.listarRealizacaoSerie();
		init();

	}
	
	private void construirCaixa() {
			String quantidade = String.valueOf(seriesTreino.size());
			String texto = quantidade + " serie(s) não relizadas, realmente deseja finalizar treino ?";
			
			criarCaixa(texto);
		}
		
	private void criarCaixa(String texto) {

		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage(texto);
		alert.setTitle("Confirmação");
		alert.setNegativeButton("Não", this);
		alert.setPositiveButton("Sim",this);
		alert.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		switch (which) {

		case DialogInterface.BUTTON_NEGATIVE:
			dialog.dismiss();
			break;	
		case DialogInterface.BUTTON_POSITIVE:
					

			try {
				finalizarTudo();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			break;


		}


		
	}

	

}
