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
	 * Metodo � chamado automaticamente pela linguagem
	 */
	@Override
	public void onUpgrade(SQLiteDatabase bd, int versaoAtual, int versaoNova) {
		Log.i("Banco","Vers�o Atual : " + versaoAtual);
		Log.i("Banco","Vers�o Nova : " + versaoAtual);
	
		
	}
	
	private void criarTabelas(SQLiteDatabase bd) {
		try{
			leituraScript(R.raw.workoutsystem,bd);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	

}
