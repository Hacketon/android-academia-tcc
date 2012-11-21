package workoutsystem.dao;

import android.database.sqlite.SQLiteDatabase;

public class Banco {

	private BancoGerenciador bancoGerenciador;
	private SQLiteDatabase sqld;

	public Banco(BancoGerenciador bancoManager) {
		bancoGerenciador = bancoManager;
	}

	public void open() {
		sqld = bancoGerenciador.getWritableDatabase();
	}

	public SQLiteDatabase get() {
		if (sqld != null && sqld.isOpen()) {
			return sqld;
		}
		return null;
		
	}

	public void close() {
		bancoGerenciador.close();
	}
}
