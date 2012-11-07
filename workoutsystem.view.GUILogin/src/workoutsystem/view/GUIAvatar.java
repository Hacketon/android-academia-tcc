package workoutsystem.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class GUIAvatar extends Activity  {

	private TabHost hostAvatar;
	private TabSpec specAvatar;
	private TabSpec specDetalhe;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avatar);
		criarTab();
	}
	
	public void criarTab(){
		
		hostAvatar = (TabHost) findViewById(R.id.hostavatar);
		hostAvatar.setup();
		
		specAvatar = hostAvatar.newTabSpec("tabavatar");
		specAvatar.setContent(R.id.tabavatar);
		specAvatar.setIndicator("Avatar");
		hostAvatar.addTab(specAvatar);
		
		
		specDetalhe = hostAvatar.newTabSpec("tabdetalhe");
		specDetalhe.setContent(R.id.tabdetalhe);
		specDetalhe.setIndicator("Detalhes");
		hostAvatar.addTab(specDetalhe);
		
	}



}
