package workoutsystem.dao;

import android.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class BancoWorkOutSystem  extends BancoGerenciador{
	
	
	

	private static final  String NOME = "WorkOutSystem";
	private static final int VERSAO = 1;
	
	public BancoWorkOutSystem(Context context){
		super(context, NOME, VERSAO);
	// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase bd) {
		criarTabelas(bd);
		
	}
	
	
	/**
	 * Metodo é chamado automaticamente pela linguagem
	 */
	@Override
	public void onUpgrade(SQLiteDatabase bd, int versaoAtual, int versaoNova) {
		Log.i("Banco","Versão Atual : " + versaoAtual);
		Log.i("Banco","Versão Nova : " + versaoAtual);
	
		try{
			execucaoScript(workoutsystem.view.R.raw.exclusao, bd);
		}catch (Exception e) {
			
			
		}
		criarTabelas(bd);
		
	}
	
	private void criarTabelas(SQLiteDatabase bd) {
		try{
			execucaoScript(workoutsystem.view.R.raw.workoutsystem,bd);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	

}
