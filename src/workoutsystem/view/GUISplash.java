package workoutsystem.view;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import workoutsystem.dao.ResourceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GUISplash extends Activity implements Runnable {

	private Thread t;
	private int sleepTime;
	private ProgressBar barraSplash;
	private TextView txtSplash;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
									, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		sleepTime = 2;
		barraSplash =(ProgressBar) findViewById(R.id.progressSplash);
		txtSplash = (TextView ) findViewById(R.id.textSplash);
		t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		int time = 0;
		ResourceManager res = new ResourceManager();
		String nomeBanco = "academiabanco.db";
		String texto = txtSplash.getText().toString() + " : ";
		
		if (verificarBanco(nomeBanco)){
			texto +=  "verificando base de dados ";
		 }else{
			criarBanco(nomeBanco);
			sleepTime = sleepTime * 2;
			texto += "criar base de dados";
		}
		txtSplash.setText(texto);
		res.setFileName(getDatabaseName(nomeBanco));
		try {
			while(time <70){
				t.sleep(sleepTime*33);
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
	
	
	private String getDatabaseName(String nomeBanco) {
		File f = new File(getFilesDir(),nomeBanco);
		return f.getAbsolutePath();
	}

	
	/**
	 * Metodo responsavel pela criação do arquivo de banco de dados
	 * no celular obtendo através de uma pasta de recursos
	 * @param nomeBanco
	 * @param context 
	 * @return true = sucesso , false = fracasso
	 */
	
	public boolean criarBanco(String nomeBanco){
		boolean verificar; 
		try {
				// busca o arquivo na pasta res/raw/academia banco.db e abre ele em input
				InputStream arquivoEntrada = getResources().openRawResource(R.raw.academiabanco);
				// cria o arquivo interno no celular 
				FileOutputStream arquivoDispositivo = openFileOutput(nomeBanco, Context.MODE_APPEND);
				byte[] buffer = new byte[1024];
				int length;
				
				while ((length = arquivoEntrada.read(buffer))>0){
					arquivoDispositivo.write(buffer, 0, length);
				}
				
				arquivoDispositivo.flush();
				arquivoDispositivo.close();
				arquivoEntrada.close();
				verificar = true;
				
		}catch (IOException e) {
			verificar = false;
			e.printStackTrace();
		}
		return verificar;
		
	}

	/**
	 * Verifica se o arquivo do banco ja existe no sistema
	 * @param nomeBanco
	 * @return true = existente , false = inexistente
	 */
	public boolean verificarBanco(String nomeBanco){
		String arquivoLocal = getFilesDir().getAbsolutePath()+"/"+nomeBanco;
		File arquivoBanco = new File(arquivoLocal);
		return arquivoBanco.exists();
		
		
	}

}
