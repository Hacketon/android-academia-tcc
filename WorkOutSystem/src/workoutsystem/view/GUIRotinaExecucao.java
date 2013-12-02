package workoutsystem.view;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleExercicio;
import workoutsystem.control.ControleRotina;
import workoutsystem.control.ControleSerie;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Rotina;
import workoutsystem.model.Serie;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Unidade;
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
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GUIRotinaExecucao extends Activity implements 
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
	private static final int LAYOUT_SIMPLES = R.layout.itens_simple_lista;
	private EditText edtCarga;
	private TextView txtCodigoExercicio;
	private Serie especificacao;
	private List<Serie> seriesRealizadas;
	private ListView lista;
	private Dialog dialogPasso;
	private List<Exercicio> exercicios;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotina_execucao);
		treino = (Treino) getIntent().getExtras().getSerializable("treino");

		listaSerie = (ListView) findViewById(R.id.lista_realizarexercicio);
		treinoDia = (TextView) findViewById(R.id.txt_treino);
		listaSerie.setOnItemLongClickListener(this);
		listaSerie.setOnItemClickListener(this);
		dialogEspecificacao = new Dialog(this);
		dialogEspecificacao.setContentView(R.layout.gerar_especificacao);
		dialogPasso = new Dialog(this);
		dialogPasso.setContentView(R.layout.exercicios_passos);
		lista = (ListView) dialogPasso.findViewById(R.id.lista_exercicio);
		lista.setOnItemClickListener(this);
		cbxUnidade = (Spinner) dialogEspecificacao.findViewById(R.id.cbx_Unidade);
		btnConfirmar = (Button) dialogEspecificacao.findViewById(R.id.btn_Confirmar_Especficacao);
		btnCancelar = (Button) dialogEspecificacao.findViewById(R.id.btn_cancelar_especficacao);
		edtSeries = (EditText) dialogEspecificacao.findViewById(R.id.edt_series);
		edtRepeticao = (EditText) dialogEspecificacao.findViewById(R.id.edt_repeticao);
		edtCarga = (EditText) dialogEspecificacao.findViewById(R.id.edt_carga);
		txtCodigoExercicio = (TextView) dialogEspecificacao.findViewById(R.id.txt_codigoExercicioEspecificacao);
		btnCancelar.setOnClickListener(this);
		btnConfirmar.setOnClickListener(this);
		seriesRealizadas = new ArrayList<Serie>();
		init();
	}

	private void init(){
		try{
			treinoDia.setText(treino.getNome());
			createListView();
		}catch (Exception e) {

		}


	}

	private void createListView() {
		ControleRotina controleRotina = new ControleRotina();
		try {
			int completa = 1;
			int chave = 0;
			treino.setSerie
			(controleRotina.listarRealizacaoSerie(treino.getCodigo()));
			List<String> series = new ArrayList<String>();

			if(treino.getSerie().isEmpty()){
				controleRotina.atualizarRealizacao(completa,chave);
				controleRotina.removerRealizacaoSerie();
				String mensagem = "Treino finalizado com sucesso";
				Toast.makeText(this,mensagem,Toast.LENGTH_SHORT).show();
				finish();
			}
			for(Serie s: treino.getSerie()){
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			createListView();		
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
		String item = parent.getItemAtPosition(pos).toString();
		if (parent.getId() == listaSerie.getId()) {
			CheckedTextView c = (CheckedTextView) view;
			boolean selecionado = c.isChecked();
			
			Serie serie = getSerie(item);
			if (!seriesRealizadas.contains(serie) && !selecionado) {
				seriesRealizadas.add(serie);
			} else {
				seriesRealizadas.remove(serie);
			}
		}else if(parent.getId() == lista.getId()){
			Exercicio exercicio = getExercicio(item);
			Intent i = new Intent(this,GUIPasso.class);
			i.putExtra("exercicio", exercicio);
			startActivity(i);
			
			
		}
		
	}

	public void finalizarSeries() throws Exception{
		ControleRotina controleRotina = new ControleRotina();
		Rotina realizacao = new Rotina();
		realizacao.setTreino(treino);
		controleRotina.inserirRealizacao(realizacao);
		for(Serie s : seriesRealizadas){
			controleRotina.inserirRealizacaoSerie(s);
		}
		createListView();

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
		try {
			switch (item.getItemId()) {
			case R.id.finalizar_multiplos:
				finalizarSeries();
				break;
			case R.id.finalizar_treino:
				finalizarSeries();
				if(treino.getSerie().size() > 0){
					construirCaixa();
				}
				break;
			case R.id.visualizar_passo:
				carregarExercicio();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private void carregarExercicio() {
		try {
			List<String> nomes = new ArrayList<String>();
			dialogPasso.setTitle("Passo a Passo");
			ControleExercicio controle = new ControleExercicio();
			exercicios = controle.buscarExercicioPasso(treino.getCodigo());

			for(Exercicio exercicio : exercicios){
				String item =exercicio.getCodigo() +" - " +exercicio.getNome(); 
				nomes.add(item);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,LAYOUT_SIMPLES,nomes);
			adapter.notifyDataSetChanged();
			lista.setAdapter(adapter);
			lista.setCacheColorHint(Color.TRANSPARENT);
			dialogPasso.show();

		} catch (Exception e) {
			String erro = e.getMessage();
			Toast.makeText(this, erro, Toast.LENGTH_LONG).show();;

		}

	}




	public void finalizarTudo() throws Exception{
		ControleRotina controleRotina = new ControleRotina();
		int completa = 1;
		int chave = 0;
		String mensagem = "Treino finalizado com sucesso";
		controleRotina.removerRealizacaoSerie();
		controleRotina.atualizarRealizacao(completa,chave);
		Toast.makeText(this,mensagem, Toast.LENGTH_SHORT).show();
		finish();

	}

	private void construirCaixa() {
		String quantidade = String.valueOf(treino.getSerie().size());
		String texto = quantidade + 
				" serie(s) não relizadas, realmente deseja finalizar treino ?";
		criarCaixa(texto);
	}

	private void criarCaixa(String texto) {
		AlertDialog.Builder alert = 
				new AlertDialog.Builder(this);
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
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	private Serie getSerie(String item) {
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
	
	
	private Exercicio getExercicio(String item){
		Exercicio exercicio = new Exercicio();
		String[] sordem = item.split(" - ");
		long codigo= Long.parseLong(sordem[0]
				.toString());
		for (Exercicio es : exercicios){
			if(codigo == es.getCodigo()){
				exercicio = es;
				break;
			}
		}
		return exercicio;
		
		
	}


}
