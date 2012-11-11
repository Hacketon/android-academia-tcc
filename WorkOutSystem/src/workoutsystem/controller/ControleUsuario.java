package workoutsystem.controller;

import android.util.Log;
import workoutsystem.dao.IUsuarioDao;
import workoutsystem.dao.UsuarioDao;
import workoutsystem.model.Usuario;

public class ControleUsuario {
	/**
	 * Metodo referente a realização de login do usuario
	 * este metodo irá pegar um usuario do banco de dados 
	 * e verificar com um usuario proveniente da tela
	 * @param u = Usuario
	 * @return true se o login for possivel caso contrario false
	 */
	public boolean realizarLogin(Usuario u){
		Log.i("Metodo", "Entrou no metodo");
		IUsuarioDao daoUsuario = new UsuarioDao();
		Usuario bancoUsuario = daoUsuario.buscarUsuario(u);
				
		if (bancoUsuario != null){
			if (u.getSenha().trim().equalsIgnoreCase(bancoUsuario.getSenha().trim())
				&& u.getNome().trim().equalsIgnoreCase(bancoUsuario.getNome().trim())){
				Log.i("Entrou no if Verdadeiro","Entrou");
				return true;
			}else{
				return false;
			}	
		} else {
			return false;
		}
	}
		
	public boolean cadastrarUsuario(Usuario u,String confSenha){
		return true;
	}

}
