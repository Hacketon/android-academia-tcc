package workoutsystem.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoTeste extends SQLiteOpenHelper {


	public BancoTeste(Context contexto){
		super(contexto, "academia.db",null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	public SQLiteDatabase getConnection(){
		return this.getWritableDatabase();
	}

}
