package workoutsystem.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleExercicio;
import workoutsystem.control.ControleSerie;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import workoutsystem.view.GUIPasso;
import workoutsystem.view.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

public class GUIExercicio
extends Activity 
implements
View.OnClickListener,
AdapterView.OnItemSelectedListener, 
ListView.OnItemClickListener,
DialogInterface.OnClickListener,

ListView.OnItemLongClickListener{

	private TabHost hospedeiro;
	private TabSpec tabpadrao;
	private TabSpec tabcriado;
	private ListView listapadrao;
	private ListView listacriado;
	private Spinner cbxExercicioPadrao;
	private Spinner cbxExercicioCriado;
	private Spinner cbxGrupo;
	private EditText editNomeExercicio;
	private EditText editDescricaoExercicio;
	private ArrayAdapter<String> adapter;
	private Dialog dialog;
	private Button btnSalvar;
	private Button btnCancelar;
	private String grupo;
	private ArrayList<String> listaGrupos;
	private TextView txtCodExercicio;
	private List<Exercicio>listaRemocaoExercicio;
	private List<Exercicio> listaExercicio;
	private static final int LAYOUT_SIMPLES = R.layout.itens_simple_lista;
	private static final int LAYOUT_MULTIPLE = R.layout.multiple_choice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercicio);
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.criarexercicio);
		btnSalvar = (Button) dialog.findViewById(R.id.btn_criar);
		btnCancelar = (Button) dialog.findViewById(R.id.btn_voltar);

		txtCodExercicio = (TextView) dialog.findViewById(R.id.codigo_exercicio);
		cbxGrupo= (Spinner) dialog.findViewById(R.id.cbx_grupo);
		editDescricaoExercicio = (EditText) dialog.findViewById(R.id.edt_descricaoExercicio);
		editNomeExercicio = (EditText) dialog.findViewById(R.id.edt_nomeExercicio);


		cbxExercicioCriado = (Spinner) findViewById(R.id.cbx_grupocriado);
		cbxExercicioPadrao = (Spinner) findViewById(R.id.cbx_grupopadrao);

		listapadrao = (ListView) findViewById(R.id.listapadrao);
		listacriado = (ListView) findViewById(R.id.listacriado);
		btnCancelar.setOnClickListener(this);
		btnSalvar.setOnClickListener(this);
		cbxExercicioCriado.setOnItemSelectedListener(this);
		cbxExercicioPadrao.setOnItemSelectedListener(this);
		listacriado.setOnItemClickListener(this);
		listacriado.setOnItemLongClickListener(this);
		listapadrao.setOnItemClickListener(this);
		listaRemocaoExercicio = new ArrayList<Exercicio>();
		criarTabs();
		criarCombo();
	}
	private void criarTabs(){
		hospedeiro = (TabHost) findViewById(R.id.hospedeiro);
		hospedeiro.setup();

		tabpadrao = hospedeiro.newTabSpec("tabpadrao");
		tabpadrao.setContent(R.id.tabpadrao);
		tabpadrao.setIndicator("Padrão");
		hospedeiro.addTab(tabpadrao);

		tabcriado = hospedeiro.newTabSpec("tabcriado");
		tabcriado.setContent(R.id.tabcriado);
		tabcriado.setIndicator("Criado");
		hospedeiro.addTab(tabcriado);

	}
	private void criarCombo(){
		listaGrupos = new ArrayList<String>();
		ControleExercicio controle = new ControleExercicio();
		ArrayList<GrupoMuscular> grupos = 
			(ArrayList<GrupoMuscular>) controle.listarGrupos(); 

		for (GrupoMuscular grupo : grupos){
			listaGrupos.add(grupo.getNome());
		}

		ArrayAdapter<String> adapter =
			new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listaGrupos);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
		cbxExercicioCriado.setAdapter(adapter);
		cbxExercicioPadrao.setAdapter(adapter);
		cbxGrupo.setAdapter(adapter);
	}
	
	@Override
	public void onClick(View v) {
		String mensagem = "Selecione algum exercicio antes !";
		switch (v.getId()) {
		case (R.id.btn_add):
			criarCaixaDialog("Novo Exercicio");
		break;
		case (R.id.btn_criar):

			try {
				salvarExercicio();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case (R.id.btn_voltar):
			dialog.dismiss();
		break;
		case (R.id.btn_exc_exercicio):
			if(listaRemocaoExercicio.size()>0){
				construirCaixa();
			}else{
				Toast.makeText(this,mensagem, Toast.LENGTH_LONG).show();
			}
			
		break;
		}

	}
	
	private void construirCaixa() {
		if(listaRemocaoExercicio.size()>0){
			String quantidade = String.
					valueOf(listaRemocaoExercicio.size())
									+ " exercicio(s)";
			String texto = "Exercicios podem estar vinculados com ficha, realmente deseja deletar  ";
			String negativa = "Não";
			String positiva = "Sim";
			String pontuacao = "?";
			String titulo = "Confirmação";
			criarCaixa(quantidade,titulo,texto,negativa,positiva,pontuacao);
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
	
	
	

	@Override
	public void onClick(DialogInterface dialog, int which) {
		String mensagem = "";
		switch (which) {

		case DialogInterface.BUTTON_NEGATIVE:
			dialog.dismiss();
			break;	
		case DialogInterface.BUTTON_POSITIVE:
			try {
				mensagem = removerExercicios();
				ControleExercicio controle = new ControleExercicio();
				listaExercicio = controle.listarExercicios
				(grupo,1);
				criarListView(listaExercicio, listacriado, LAYOUT_MULTIPLE);
			} catch (Exception e) {
				mensagem = e.getMessage();

			}
			if(!mensagem.equalsIgnoreCase("")){
				Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
			}
			
			break;


		}

	}

	private String removerExercicios() {
		ControleSerie controle = new ControleSerie();
		ControleExercicio controleExercicio = new ControleExercicio();
		String mensagem = "";
		try {
			if(!listaRemocaoExercicio.isEmpty()){
				mensagem = controleExercicio.excluirExercicio(listaRemocaoExercicio);	
			}
			 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return mensagem;
	}


	private void salvarExercicio() throws SQLException {
		Exercicio e = criarExercicio();
		ControleExercicio controle = new ControleExercicio();
		String mensagem;
		try {
			mensagem = controle.manipularExercicio(e);
			listaExercicio = 
			controle.listarExercicios(e.getGrupoMuscular().getNome(), 
					e.getPadrao());
			atualizarCombo(e,cbxExercicioCriado);
			criarListView(listaExercicio, listacriado,LAYOUT_MULTIPLE);
			dialog.dismiss();
		}catch (Exception e1) {
			mensagem = e1.getMessage();
		}
		Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();

	}


	private void atualizarCombo(Exercicio e,Spinner combo) {
		int i = 0;
		for (String l : listaGrupos){
			if (l.equalsIgnoreCase(e.getGrupoMuscular().getNome())){
				combo.setSelection(i);
				break;
			}
			i++;
		}

	}

	private void carregarExercicio(Exercicio exercicio) {
		int i = 0;
		if (exercicio != null){
			txtCodExercicio.setText(String.valueOf(exercicio.getCodigo()));
			editNomeExercicio.setText(exercicio.getNome());
			editDescricaoExercicio.setText(exercicio.getDescricao());
			atualizarCombo(exercicio,cbxGrupo);

		}
	}
	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		ControleExercicio controle = new ControleExercicio();
		List<Exercicio> listaExercicios = null;
		grupo = parent.getItemAtPosition(pos).toString();
		if (parent.getId() == R.id.cbx_grupocriado){
			try {
				listaExercicio = controle.listarExercicios
				(grupo,1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			criarListView(listaExercicio,listacriado,LAYOUT_MULTIPLE);

		}else if (parent.getId()== R.id.cbx_grupopadrao){
			try {
				listaExercicios = controle.listarExercicios
				(grupo,0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			criarListView(listaExercicios,listapadrao,LAYOUT_SIMPLES);

		}
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {


	}

	private void criarListView(List<Exercicio> exercicios,ListView lista,int layout) {
		ArrayList<String> nomes = new ArrayList<String>();
		for (Exercicio e : exercicios){
			nomes.add(e.getNome());
		}
		adapter = new ArrayAdapter<String>
		(this,layout,nomes);
		adapter.notifyDataSetChanged();
		lista.setAdapter(adapter);
		lista.setCacheColorHint(Color.TRANSPARENT);
		

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		ControleExercicio controle = new ControleExercicio();
		Exercicio exercicio = 
			controle.buscarExercicio(parent.getItemAtPosition(pos).toString());
		if (R.id.listacriado == parent.getId()){
			CheckedTextView c = (CheckedTextView) view;
			boolean selecionado = c.isChecked();
			String nome = parent.getItemAtPosition(pos).toString();
			

			for(Exercicio e : listaExercicio){
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
		}else{
			Intent i = new Intent(this,GUIPasso.class);
			i.putExtra("exercicio", exercicio);
			startActivity(i);

		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int pos,
			long id) {
		ControleExercicio controle = new ControleExercicio();
		Exercicio exercicio = 
			controle.buscarExercicio(parent.getItemAtPosition(pos).toString());
		criarCaixaDialog("Alterar Exercicio");
		carregarExercicio(exercicio);
		
		return false;
	}
	
	
	private void criarCaixaDialog(String titulo) {
		dialog.setTitle(titulo);
		txtCodExercicio.setText("");
		editDescricaoExercicio.setText("");
		editNomeExercicio.setText("");
		txtCodExercicio.setText("");
		editNomeExercicio.requestFocus();
		dialog.show();

	}

	public Exercicio criarExercicio(){
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


}





