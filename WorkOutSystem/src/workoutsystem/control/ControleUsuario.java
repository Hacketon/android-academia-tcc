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
	 * Metodo referente a realização de login do usuario
	 * este metodo irá pegar um usuario do banco de dados 
	 * e verificar com um usuario proveniente da tela
	 * @param u = Usuario
	 * @return true se o login for possivel caso contrario false
	 */

	public boolean realizarLogin(Usuario u){
		IUsuarioDao daoUsuario = new UsuarioDao(contexto);
		if (u != null){
			return daoUsuario.realizarLogin(u);	
		} else {
			return false;
		}
	}

	public boolean cadastrarUsuario(Usuario u,String confSenha){
		IUsuarioDao daoUsuarioDao = new UsuarioDao(contexto);
		if (u.getSenha().equalsIgnoreCase(confSenha)){
			if (daoUsuarioDao.buscarUsuario(u) == null){
				return daoUsuarioDao.cadastrarUsuario(u);
			}else{
				return false;
			}

		}else{
			return false;
		}		
	}

}
