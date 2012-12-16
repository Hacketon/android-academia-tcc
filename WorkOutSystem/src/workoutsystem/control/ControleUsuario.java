package workoutsystem.control;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import android.content.Context;
import android.util.Log;
import workoutsystem.dao.UsuarioDao;
import workoutsystem.interfaces.IUsuarioDao;
import workoutsystem.model.Usuario;

public class ControleUsuario {

	private Context contexto;


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
		IUsuarioDao daoUsuario = new UsuarioDao();
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
		if (violations.isEmpty()){
			return daoUsuario.realizarLogin(usuario);
		}else{
			return false;
		}
		
	
		
	}

	public boolean cadastrarUsuario(Usuario u,String confSenha){
		IUsuarioDao daoUsuarioDao = new UsuarioDao();
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
