package workoutsystem.dao;


import java.io.File;
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
			Driver d = (Driver) Class.forName(driver).newInstance();
			con = d.connect(url, null);
					
		}catch (SQLException e){
			Log.e("SQL","(SQLException)");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		
		return con;
		
		
	}
	



}
