package workoutsystem.interfaces;

import workoutsystem.model.Usuario;

public interface IUsuarioDao {
	/**
	 * Metodo responvavel pela busca do usuario no banco de dados
	 * 
	 * @param u
	 * @return usuario preenchido se encontrado ou sen�o 
	 * null para indicar que n�o foi encontrado
	 */
	public abstract Usuario buscarUsuario(Usuario u );
	/**
	 * Metodo responvavel pelo cadastro de um novo usuario
	 * @param usuario a ser cadastrado
	 * @return 
	 */
	public abstract boolean cadastrarUsuario (Usuario u);
	/**
	 * Metodo responsavel pelo login do usuario
	 * @param u
	 * @return true = usuario pode logar , false = usuario n�o pode logar
	 */
	public abstract boolean realizarLogin(Usuario u);
	/**
	 * Busca o usuario que esta logado no sistema
	 * @return usuario logado caso contrario null
	 */
	public abstract Usuario buscarUsuario();
	
	public abstract void desconectarUsuario();
	
	public abstract boolean alterarSenha(Usuario u,String senha);
}
	
