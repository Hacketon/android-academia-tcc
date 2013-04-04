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

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class ResourceManager {


	public static  Connection conexao() {
		Connection con = null;
		try{
			ResourceManager resourceManager = new ResourceManager();
			String fileName = "/data/data/workoutsystem.view/databases/academiabanco.bd";
			resourceManager.verificarArquivo(fileName);
			String urldriver = "org.sqldroid.SQLDroidDriver";
			String url = "jdbc:sqldroid:"+ fileName;
			Driver driver = (Driver) Class.forName(urldriver).newInstance();
			con = driver.connect(url, null);
		}catch (Exception e){
			Log.e("Erro",e.getMessage());
		}
		return con;
	}

	
	/**
	 * Metodo desenvolvido para verificar se o banco existe caso contrario cria o banco
	 * @param nomeBanco
	 * @return true = sucesso , false = fracasso
	 */
	public boolean verificarArquivo(String nomeBanco){
		boolean verificar = false;
		try {
			File arquivoBanco = new File(nomeBanco);
			verificar= arquivoBanco.exists();
			if (!verificar){
				String local = "res/raw/academiabanco.db"; 
				InputStream arquivoEntrada = this.getClass().getClassLoader().getResourceAsStream(local);
				arquivoBanco.createNewFile();
				FileOutputStream arquivoDispositivo = new FileOutputStream(arquivoBanco);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = arquivoEntrada.read(buffer))>0){
					arquivoDispositivo.write(buffer, 0, length);
				}
				arquivoDispositivo.flush();
				arquivoDispositivo.close();
				arquivoEntrada.close();
				verificar = arquivoBanco.exists() ? true : false;

			}

		}catch (Exception e) {
			verificar = false;
			e.printStackTrace();
		}
		return verificar;
		
	}
	
	
}











