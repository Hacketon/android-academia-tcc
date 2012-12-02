package workoutsystem.dao;


import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Banco extends Activity {

	public static Connection conexao(){




		try{
			String driver = "org.sqldroid.SQLDroidDriver";
			String url = "jdbc:sqldroid:/workoutsystem/dao/academiabanco.bd";
			Driver d = (Driver) Class.forName(driver).newInstance();
			DriverManager.registerDriver(d);
			Connection con = DriverManager.getConnection(url);
			return con;
		}catch (SQLException e){
			Log.e("SQL","(SQLException)" + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}




}
