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

		IUsuarioDao daoUsuarioDao = new UsuarioDao();
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
		IUsuarioDao daoUsuarioDao = new UsuarioDao();
		if (confSenha.equalsIgnoreCase(u.getSenha()) && daoUsuarioDao.buscarUsuario(u) != null){
				daoUsuarioDao.cadastrarUsuario(u);
				return true;
		}else{
				return false;
		}
		
	}

}
