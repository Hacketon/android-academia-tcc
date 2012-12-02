package workoutsystem.control;

import android.content.Context;
import android.util.Log;
import workoutsystem.dao.IUsuarioDao;
import workoutsystem.dao.UsuarioDao;
import workoutsystem.model.Usuario;

public class ControleUsuario {
	
	private Context contexto;
	
	
	public ControleUsuario(Context context){
		contexto = context;
	}
	/**
	 * Metodo referente a realiza��o de login do usuario
	 * este metodo ir� pegar um usuario do banco de dados 
	 * e verificar com um usuario proveniente da tela
	 * @param u = Usuario
	 * @return true se o login for possivel caso contrario false
	 */
	
	public boolean realizarLogin(Usuario u){

		IUsuarioDao daoUsuarioDao = new UsuarioDao(contexto);
		Usuario bancoUsuario = daoUsuarioDao.buscarUsuario(u);
				
		if (bancoUsuario != null){
			if (u.getSenha().trim().equalsIgnoreCase(bancoUsuario.getSenha().trim())
				&& u.getNome().trim().equalsIgnoreCase(bancoUsuario.getNome().trim())){
				return true;
			}else{
				return false;
			}	
		} else {
			return false;
		}
	}
		
	public boolean cadastrarUsuario(Usuario u,String confSenha){
		IUsuarioDao daoUsuarioDao = new UsuarioDao(contexto);
		if (u.getSenha().equalsIgnoreCase(confSenha)){
			if (daoUsuarioDao.buscarUsuario(u) == null){
				daoUsuarioDao.cadastrarUsuario(u);
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}		
	}

}
