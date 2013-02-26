package workoutsystem.dao;


import java.sql.Connection;
import java.sql.Driver;

import android.util.Log;

public class Banco{

	public static Connection conexao() {
		Connection con = null;
		try{
	
			String urldriver = "org.sqldroid.SQLDroidDriver";
			String url = "jdbc:sqldroid:/data/data/workoutsystem.dao" +
			"/databases/academiabanco.db";
			Driver driver = (Driver) Class.forName(urldriver).newInstance();
			con = driver.connect(url, null);
		}catch (Exception e){
			Log.e("Erro",e.getMessage());
		}
			return con;
	}
	

}
