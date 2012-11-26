package workoutsystem.dao;


import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Banco {


	public static Connection conexao(){
		try{

			String driver = "org.sqlite.JDBC";
			//Mesmo adicionando o JDBC driver da 
			//Class not found exception !
			String url = "jdbc:sqlite:WorkOutSystem.bd";
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url);

			return con;

		}catch (SQLException e){
			Log.e("SQL","Erro na criação " +
			"do drive (SQLException)");
			e.printStackTrace();
			return null;
		}catch (ClassNotFoundException e){
			Log.e("SQL","Erro na criação " +
			"do drive (ClassNotFoundException)");
			e.printStackTrace();
			return null;

		}catch (Exception e){
			System.out.print("Erro na criação " +
			"do drive (Exception)");
			e.printStackTrace();
			return null;
		}
	}

	//	private BancoGerenciador bancoGerenciador;
	//	private SQLiteDatabase sqld;
	//
	//	public Banco(BancoGerenciador bancoManager) {
	//		bancoGerenciador = bancoManager;
	//	}
	//
	//	public void open() {
	//		sqld = bancoGerenciador.getWritableDatabase();
	//	}
	//
	//	public SQLiteDatabase get() {
	//		if (sqld != null && sqld.isOpen()) {
	//			return sqld;
	//		}
	//		return null;
	//		
	//	}
	//
	//	public void close() {
	//		bancoGerenciador.close();
	//	}
}
