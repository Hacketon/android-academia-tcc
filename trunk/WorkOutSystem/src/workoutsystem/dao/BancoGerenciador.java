package workoutsystem.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BancoGerenciador extends SQLiteOpenHelper{

	protected Context contexto;

	public BancoGerenciador(Context context, String name,int version) {
		super(context,name,null,version);
		contexto = context;
	}

	public abstract void onCreate(SQLiteDatabase bd);
	public abstract void onUpgrade(SQLiteDatabase bd, int versaoAtual, int versaoNova);
	/**
	 * Metodo responsavel pela leitura do Script das tabelas do banco
	 * quando ele acha o ponto e virgula responsavel pelo fim da tabela , faz a execução.
	 * @param idArquivo = id do arquivo do script
	 * @param bd = SQLiteDatabase , manipulação do banco
	 * @throws IOException = lida com leitura de arquivo
	 */
	protected void execucaoScript(int idArquivo,SQLiteDatabase bd) throws IOException{
		StringBuilder sql = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(contexto.getResources().openRawResource(idArquivo)));
		String linha;
		while (reader.ready()){
			linha = reader.readLine();
			linha = linha.trim();
			if (linha.length() > 0){
				sql.append(linha);
				if (linha.endsWith(";")){
					bd.execSQL(sql.toString());
					sql.delete(0, sql.length());
				}
			}

		}
	}


}
