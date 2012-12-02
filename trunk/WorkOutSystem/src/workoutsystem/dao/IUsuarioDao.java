package workoutsystem.dao;

import workoutsystem.model.Usuario;

public interface IUsuarioDao {
	/**
	 * Metodo responvavel pela busca do usuario no banco de dados
	 * 
	 * @param u
	 * @return usuario preenchido se encontrado ou senão 
	 * null para indicar que não foi encontrado
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
	 * @return true = usuario pode logar , false = usuario não pode logar
	 */
	public abstract boolean realizarLogin(Usuario u);
}
