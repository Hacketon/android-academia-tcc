package workoutsystem.dao;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Banco extends Activity {
	
	public static Connection conexao() {
		Connection con = null;
		try{
			String driver = "org.sqldroid.SQLDroidDriver";
			String url = "jdbc:sqldroid:/data/data/workoutsystem.dao/databases/academiabanco.db";
			Class.forName(driver).newInstance();
			
			con = DriverManager.getConnection(url);
		}catch (SQLException e){
		}catch (ClassNotFoundException e) {
		}catch (InstantiationException e) {
		}catch (IllegalAccessException e) {
		}
		return con;
	}
		
}
