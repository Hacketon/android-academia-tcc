package workoutsystem.view;



import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import workoutsystem.dao.ResourceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class GUISplash extends Activity implements Runnable {

	private Thread t;
	private static final int SLEEP_TIME = 4;
	private ProgressBar barraSplash;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
									, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		barraSplash =(ProgressBar) findViewById(R.id.progressSplash);
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		
		int time = 0;
		ResourceManager res = new ResourceManager();
		String nomeBanco = "academiabanco.db";
		boolean resultado = verificarArquivo(nomeBanco);
		try {
			while(time <100){
				t.sleep(SLEEP_TIME*33);
				barraSplash.setProgress(time++);
			}
						
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		Intent i = new Intent(GUISplash.this,GUIPrincipal.class);
		startActivity(i);
		finish();
		
		
	}
	
	
	/**
	 * Metodo desenvolvido para verificar se o banco existe caso contrario cria o banco
	 * @param nomeBanco
	 * @param context 
	 * @return true = sucesso , false = fracasso
	 */
	public boolean verificarArquivo(String nomeBanco){
		boolean verificar = false;
		try {
			String arquivoLocal = getFilesDir().getAbsolutePath()+"/"+nomeBanco;
			File arquivoBanco = new File(arquivoLocal);
			verificar = arquivoBanco.exists();
			if (!verificar){
				String local = "res/raw/academiabanco.db";
				InputStream arquivoEntrada = getClass().getClassLoader().getResourceAsStream(local);
				
				FileOutputStream arquivoDispositivo = openFileOutput(nomeBanco, Context.MODE_APPEND);
				
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
