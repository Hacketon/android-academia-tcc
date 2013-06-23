package workoutsystem.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.control.ControleFicha;
import workoutsystem.control.ControlePerfil;
import workoutsystem.control.ControleTreino;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Ficha;
import workoutsystem.model.Perfil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GUIFicha extends Activity implements 
ListView.OnItemClickListener,
ListView.OnItemLongClickListener,
DialogInterface.OnClickListener

{

	private ListView listaFicha;
	private ArrayAdapter<String> adapter;
	private String [] fichas;
	private List<Ficha> listFicha;
	private boolean [] selecaoexc;
	private List<Ficha> listaRemocao;
	private TextView txtNomeFicha;
	private Dialog dialogFicha;
	private TextView txtNome;
	private TextView txtObjetivo;
	private TextView txtQuantidade;
	private TextView txtRealizacao;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ficha);
		dialogFicha = new Dialog(this);
		dialogFicha.setContentView(R.layout.dados_ficha);

		txtNome = (TextView) dialogFicha.findViewById(R.id.ficha_atual_nome);
		txtObjetivo = (TextView) dialogFicha.findViewById(R.id.ficha_atual_objetivo);
		txtQuantidade = (TextView) dialogFicha.findViewById(R.id.ficha_atual_quantidade);
		txtRealizacao = (TextView) dialogFicha.findViewById(R.id.ficha_atual_realizacao);


		listaFicha = (ListView) findViewById(R.id.listafichas);
		listaFicha.setOnItemClickListener(this);
		listaFicha.setOnItemLongClickListener(this);
		listaRemocao = new ArrayList<Ficha>();
		txtNomeFicha = (TextView) findViewById(R.id.nome_ficha_atual);


		try {
			createListView();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		selecionarFichaAtual();

	}

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

	private void createListView() throws SQLException {
		ControleFicha controle = new ControleFicha();
		listFicha  = controle.buscarFicha();
		ArrayList<String> nomes = new ArrayList<String>();
		for (Ficha f : listFicha){
			nomes.add(f.getNome());
		}
		adapter = new ArrayAdapter<String>
		(this,R.layout.multiple_choice,nomes);
		adapter.notifyDataSetChanged();
		listaFicha.setAdapter(adapter);
		listaFicha.setCacheColorHint(Color.TRANSPARENT);
		listaFicha.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		criarExclusao(listFicha);

	}

	private void criarExclusao(List<Ficha> listFichas) {
		int i = 0;
		fichas = null;
		selecaoexc = null;
		int listaTamanho = listFichas.size();

		if (listaTamanho > 0){
			fichas = new String[listaTamanho];
			selecaoexc = new boolean[listaTamanho];
		}

		for (Ficha f : listFichas){
			fichas[i] = f.getNome();
			i++;
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_principal_ficha, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String mensagem = "";
		ControlePerfil controle = new ControlePerfil();
		ControleFicha controleFicha = new ControleFicha();
		Ficha f = new Ficha();
		Perfil perfil = null;

		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.mostrar_ficha:
			f = selecionarFichaAtual();
			if(f != null){
				criarDialogo("Ficha Atual", f);
			}

			break;
		case R.id.adicionar_ficha:
			f = new Ficha();
			iniciarFichaManipular(f);
			break;

		case R.id.remover_ficha:
			String titulo = "Confirmação";
			String texto = "Deseja realmente remover as ficha(s) selecionada(s)";
			String negativa = "Cancelar";
			String positiva = "Remover";
			String pontuacao = "?";
			if (listaRemocao.size()>0){
				criarCaixa(titulo,texto,negativa,positiva,pontuacao);	
			}else{
				texto = "Selecione as fichas antes da remoção!";
				Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.mudar_ficha:
			perfil = controle.buscarPerfil();
			if(perfil == null){
				mensagem = "Primeiro crie o seu perfil";
			}else{
				if (listFicha.size()<=0){
					mensagem = "Você não possui ficha(s)" +
					" cadastrada(s) !";
				}else{
					criarDialogoFichaAtual();
					mensagem = "";
				}
			}
			if(!mensagem.equalsIgnoreCase("")){
				Toast.makeText
				(this, mensagem, Toast.LENGTH_SHORT).show();
			}

			break;
		}

		return false;
	}



	private void criarDialogoFichaAtual() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Ficha(s)");
		int selected = -1;
		int cont = 0;
		for(Ficha f : listFicha ){
			fichas[cont] = f.getNome();
			cont++;
		}
		builder.setSingleChoiceItems(fichas, selected, this);
		builder.show();

	}







	private void iniciarFichaManipular(Ficha f) {
		Intent i = new Intent(this,GUIFichaManipular.class);
		i.putExtra("ficha", f.getCodigo());
		startActivity(i);

	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos,long id) {
		CheckedTextView c = (CheckedTextView) view;
		boolean selecionado = c.isChecked();
		String nome = parent.getItemAtPosition(pos).toString();
		Ficha ficha = new Ficha();

		for(Ficha f : listFicha){
			if(f.getNome().equalsIgnoreCase(nome)){
				ficha = f; 
				break;
			}
		}
		if (!listaRemocao.contains(ficha)
				&& !selecionado){
			listaRemocao.add(ficha);
		}else{
			listaRemocao.remove(ficha);
		}


	}

	public void onClick(DialogInterface dialog, int clicked) {
		String mensagem = "";
		ControleFicha controle = new ControleFicha();
		switch (clicked) {
		case DialogInterface.BUTTON_POSITIVE:
			try {
				mensagem = controle.excluirFicha(listaRemocao);
				createListView();
				selecionarFichaAtual();
			} catch (Exception e) {
				mensagem = e.getMessage();
			}
			dialog.dismiss();


			break;
		case DialogInterface.BUTTON_NEGATIVE:
			dialog.dismiss();
			break;
		default:
			try {
				String nome = fichas[clicked];
				Ficha ficha = null;
				for (Ficha f : listFicha){
					if(f.getNome().equalsIgnoreCase(nome)){
						ficha = f;
						break;
					}
				}
				mensagem = controle.mudarFichaAtual(ficha);
				selecionarFichaAtual();
				dialog.dismiss();
			} catch (SQLException e) {
				mensagem = e.getMessage();

			}


			break;
		}
		if(!mensagem.equalsIgnoreCase("")){
			Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
		}

	}




	private void criarCaixa(
			String titulo,
			String texto,
			String negativa,
			String positiva,
			String pontuacao) {

		texto = texto + pontuacao;

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage(texto);
		alert.setTitle(titulo);
		alert.setNegativeButton(negativa, this);
		alert.setPositiveButton(positiva, this);
		alert.show();
	}

	private void criarDialogo(String titulo,Ficha f){
		int quantidade = f.getTreinos().size() + 1;
		dialogFicha.setTitle(titulo);
		txtNome.setText(f.getNome());
		txtObjetivo.setText(f.getObjetivo());
		txtQuantidade.setText(String.valueOf(quantidade));
		String realizacao = f.getRealizacoes()+ "/" +f.getDuracao();;
		txtRealizacao.setText(realizacao);

		dialogFicha.show();

	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		super.onPrepareDialog(id, dialog);
		ArrayAdapter<String> list = new ArrayAdapter<String>
		(this, android.R.layout.select_dialog_multichoice,fichas);
		AlertDialog alerta = (AlertDialog) dialog;
		alerta.getListView().setAdapter(list);
		list.notifyDataSetChanged();


	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int pos,
			long id) {
		ControleFicha controle = new ControleFicha();

		Ficha ficha = new Ficha();
		String s = parent.getItemAtPosition(pos).toString();
		for (Ficha f : listFicha)
			if(f.getNome().equalsIgnoreCase(s.trim())){
				ficha = f;
				break;
			}
		iniciarFichaManipular(ficha);
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			createListView();
			selecionarFichaAtual();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




}
