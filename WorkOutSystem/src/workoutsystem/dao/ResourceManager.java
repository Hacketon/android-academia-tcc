package workoutsystem.dao;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Driver;

import workoutsystem.view.R;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class ResourceManager extends Application{
	
	private static Context c;
	
	public void onCreate(){
		super.onCreate();
		ResourceManager.c = getApplicationContext();
	}

	public static  Connection conexao() {
		Connection con = null;
		try{
			ResourceManager resourceManager = new ResourceManager();
			String fileName = "/data/data/workoutsystem.view/files/academiabanco.db";
			String urldriver = "org.sqldroid.SQLDroidDriver";
			String url = "jdbc:sqldroid:"+ fileName;
			Driver driver = (Driver) Class.forName(urldriver).newInstance();
			con = driver.connect(url, null);
		}catch (Exception e){
			Log.e("Erro",e.getMessage());
		}
		return con;
	}

	

	
	
}











