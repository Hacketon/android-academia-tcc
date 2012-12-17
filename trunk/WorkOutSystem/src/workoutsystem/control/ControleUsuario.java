package workoutsystem.control;

import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationProviderResolver;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.spi.ValidationProvider;


import workoutsystem.dao.Banco;
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
		
		IUsuarioDao daoUsuario = new UsuarioDao();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
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
