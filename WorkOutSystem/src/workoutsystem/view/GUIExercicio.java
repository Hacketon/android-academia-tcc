package workoutsystem.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleExercicio;
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
DialogInterface.OnMultiChoiceClickListener,
DialogInterface.OnClickListener{

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
	private boolean [] selecaoexc;
	private String [] exercicios;
	private ArrayAdapter<String> adapter;
	private Dialog dialog;
	private Button btnSalvar;
	private Button btnCancelar;
	private String grupo;
	private ArrayList<String> listaGrupos;
	private TextView txtCodExercicio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercicio);
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.criarexercicio);
		txtCodExercicio = (TextView) dialog.findViewById(R.id.codigo_exercicio);
		cbxExercicioCriado = (Spinner) findViewById(R.id.cbx_grupocriado);
		cbxExercicioPadrao = (Spinner) findViewById(R.id.cbx_grupopadrao);
		cbxGrupo= (Spinner) dialog.findViewById(R.id.cbx_grupo);
		listapadrao = (ListView) findViewById(R.id.listapadrao);
		listacriado = (ListView) findViewById(R.id.listacriado);
		editDescricaoExercicio = (EditText) dialog.findViewById(R.id.edt_descricaoExercicio);
		editNomeExercicio = (EditText) dialog.findViewById(R.id.edt_nomeExercicio);
		btnSalvar = (Button) dialog.findViewById(R.id.btn_criar);
		btnCancelar = (Button) dialog.findViewById(R.id.btn_voltar);

		btnCancelar.setOnClickListener(this);
		btnSalvar.setOnClickListener(this);
		cbxExercicioCriado.setOnItemSelectedListener(this);
		cbxExercicioPadrao.setOnItemSelectedListener(this);
		listacriado.setOnItemClickListener(this);
		listapadrao.setOnItemClickListener(this);

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
		String mensagem = "Não há exercicios para ser excluido";
		switch (v.getId()) {
		case (R.id.btn_add):
			criarCaixaDialog("Novo Exercicio");
		break;
		case (R.id.btn_criar):

			try {
				alterarExercicio();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		break;
		case (R.id.btn_voltar):
			dialog.dismiss();
		break;
		case (R.id.btn_exc_exercicio):
			if (exercicios != null && selecaoexc != null){
				showDialog(0);
			}else{
				Toast.makeText(this,mensagem, Toast.LENGTH_LONG).show();
			}

		break;
		}

	}

	private void alterarExercicio() throws SQLException {
		Exercicio e = criarExercicio();
		ControleExercicio controle = new ControleExercicio();
		String mensagem;
		try {
			mensagem = controle.manipularExercicio(e);
		} catch (SQLException e1) {
			mensagem = "Operação do banco contém erros";

		}
		Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();

		ArrayList<Exercicio> exercicios  = (ArrayList<Exercicio>)
		controle.listarExercicios(e.getGrupoMuscular().getNome(), 
				e.getPadrao());
		atualizarCombo(e,cbxExercicioCriado);
		createListView(exercicios, listacriado);
		dialog.dismiss();
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
			editNomeExercicio.setText(exercicio.getNomeExercicio());
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
				listaExercicios = controle.listarExercicios
				(grupo,1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			createListView(listaExercicios,listacriado);
			
		}else if (parent.getId()== R.id.cbx_grupopadrao){
			try {
				listaExercicios = controle.listarExercicios
				(grupo,0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			createListView(listaExercicios,listapadrao);

		}
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {


	}
	
	private void createListView(List <Exercicio> exercicios,ListView lista) {
		ArrayList<String> nomes = new ArrayList<String>();
		for (Exercicio e : exercicios){
			nomes.add(e.getNomeExercicio());
		}
		adapter = new ArrayAdapter<String>
		(this,R.layout.itens_simple_lista,nomes);
		adapter.notifyDataSetChanged();
		lista.setAdapter(adapter);
		lista.setCacheColorHint(Color.TRANSPARENT);
		criarExclusao(exercicios);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		ControleExercicio controle = new ControleExercicio();
		Exercicio exercicio = 
			controle.buscarExercicio(parent.getItemAtPosition(pos).toString());
		if (R.id.listacriado == parent.getId()){
			criarCaixaDialog("Alterar Exercicio");
			carregarExercicio(exercicio);
		}else{
			Intent i = new Intent(this,GUIPasso.class);
			i.putExtra("exercicio", exercicio);
			startActivity(i);

		}

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


		exercicio.setNomeExercicio(editNomeExercicio.getText().toString());
		grupo.setNome(cbxGrupo.getSelectedItem().toString());
		exercicio.setDescricao(editDescricaoExercicio.getText().toString());
		exercicio.setGrupoMuscular(grupo);

		return exercicio;
	}

	
	private void criarExclusao(List<Exercicio> listaExercicios) {
		int i = 0 ;
		exercicios = null;
		selecaoexc = null;
		int listaTamanho = listaExercicios.size();
		
		if (listaTamanho > 0){
			exercicios = new String[listaTamanho];
			selecaoexc = new boolean[listaTamanho];
		}
		 
		for (Exercicio e : listaExercicios){
			exercicios[i] = e.getNomeExercicio();
			i++;
		}
		
	}


	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return new AlertDialog.Builder(this)
		.setTitle("Exercicios")
		.setMultiChoiceItems(exercicios,selecaoexc,this)
		.setPositiveButton("Deletar",this)
		.setNeutralButton("Cancelar",this)
		.create();

	}


	public void onClick(DialogInterface dialog, int clicked) {
		String mensagem;
		switch (clicked) {
		case DialogInterface.BUTTON_POSITIVE:
			try {
				if (deletarExercicios()){
					mensagem = "Operação realizada com sucesso";
					}else{
					mensagem = "Operação contendo erros";
				}
			} catch (SQLException e) {
				mensagem = "Erro no banco de dados";
			}
			List<Exercicio> listarExercicios = null;
			try {
				listarExercicios = new 
						ControleExercicio().listarExercicios(grupo, 1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			createListView(listarExercicios, listacriado);

			break;

		}

	}

	private boolean deletarExercicios() throws SQLException {
		ControleExercicio controle = new ControleExercicio();
		int i = 0 ;
		int contador = 0;
		boolean resultado;
		ArrayList<String> deletados = new ArrayList<String>();
		ArrayList<String> ndeletados = new ArrayList<String>();

		for (boolean b : selecaoexc){
			if (b == true){
				deletados.add(exercicios[i]);
				contador++;
			}else{
				ndeletados.add(exercicios[i]);
			}
			i++;
		}
		i = 0;
		contador = exercicios.length - contador;
		exercicios = null;
		selecaoexc = null;
		if (contador != 0){
			exercicios = new String[contador];
			selecaoexc = new boolean[contador];
			for (String o : ndeletados){
				exercicios[i] = o;
				i++;
			}

		}

		resultado = controle.excluirExercicio(deletados);
		return resultado;


	}


	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		selecaoexc[which]= isChecked;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		ArrayAdapter<String> list = new ArrayAdapter<String>
		(this, android.R.layout.select_dialog_multichoice,exercicios);
		AlertDialog alerta = (AlertDialog) dialog;
		alerta.getListView().setAdapter(list);
		list.notifyDataSetChanged();


	}
}





