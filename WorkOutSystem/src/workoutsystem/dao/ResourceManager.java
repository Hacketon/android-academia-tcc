package workoutsystem.dao;

import java.sql.Connection;
import java.sql.Driver;

import android.app.Application;
import android.util.Log;

public class ResourceManager extends Application{
	
	private static String fileName;
	
	public static  Connection getConexao() {
		Connection con = null;
		try{
			String urldriver = "org.sqldroid.SQLDroidDriver";
			String url = "jdbc:sqldroid:"+ fileName;
			Driver driver = (Driver) Class.forName(urldriver).newInstance();
			con = driver.connect(url, null);
		}catch (Exception e){
			Log.e("Erro",e.getMessage());
		}
		return con;
		
	}

	
	public void setFileName(String nomeBanco){
		fileName = nomeBanco;
	}

	
	
}











