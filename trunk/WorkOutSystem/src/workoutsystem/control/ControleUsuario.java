package workoutsystem.control;

import java.util.List;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;


import workoutsystem.dao.UsuarioDao;
import workoutsystem.interfaces.IUsuarioDao;
import workoutsystem.model.Usuario;

public class ControleUsuario {

	public ControleUsuario(){
		
	}
	/**
	 * Metodo referente a realização de login do usuario
	 * este metodo irá pegar um usuario do banco de dados 
	 * e verificar com um usuario proveniente da tela
	 * @param u = Usuario
	 * @return true se o login for possivel caso contrario false
	 */

	public boolean realizarLogin(Usuario usuario){
		// validação
		Validator validator = new Validator();
		List<ConstraintViolation> violations = validator.validate(usuario);
		if (violations.size()<=0){
			IUsuarioDao daoUsuario = new UsuarioDao();
			if (usuario != null){
				return daoUsuario.realizarLogin(usuario);
			}else{
				return false;
			}
		}else{
			return false;
		}
		
		
			
	
		
	}

	public boolean cadastrarUsuario(Usuario usuario,String confSenha){
		Validator validator = new Validator();
		List<ConstraintViolation> violations = validator.validate(usuario);
		IUsuarioDao daoUsuario = new UsuarioDao();
		boolean retorno = false;
		if (violations.size()<=0){
			if (usuario.getSenha().equalsIgnoreCase(confSenha)){
				if (daoUsuario.buscarUsuario(usuario) == null){
					retorno =  daoUsuario.cadastrarUsuario(usuario);
				}
			}
		}
		return retorno;
		
	}
	
	public boolean alterarSenha(Usuario u,String senhaNova,String confSenha,String senhaAtual){
		boolean verificador = false;
		IUsuarioDao daoUsuario = new UsuarioDao();
		if (u.getSenha().equalsIgnoreCase(senhaAtual)){
			if (senhaNova.equalsIgnoreCase(confSenha)){
				verificador = daoUsuario.alterarSenha(u,senhaNova);
			}
		}
		return verificador;
		
	}
	
	public Usuario buscarUsuario(){
		IUsuarioDao daoUsuario = new UsuarioDao();
		return daoUsuario.buscarUsuario();
	}
	public void desconectarUsuario() {
		IUsuarioDao daoUsuario = new UsuarioDao();
		daoUsuario.desconectarUsuario();
	}

}
