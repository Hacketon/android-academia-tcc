package workoutsystem.dao;

import workoutsystem.model.Perfil;
import workoutsystem.model.Usuario;

public interface IPerfilDao {
	/**
	 * Metodo responsavel pela busca de perfil relacionada ao usuario, ,
	 *  o mesmo ira carregar ao inicar a tela
	 * @param u
	 * @return
	 */
	public abstract Perfil buscarPerfil();
	
	/**
	 * metodo responsavel pela criação de um perfil
	 * @param perfil
	 * @return
	 */
	public abstract boolean criarPerfil(Perfil perfil);
	
	
	/**
	 * Metodo responsavel pela exclusao de um perfil  
	 * @param perfil
	 * @return
	 */
	public abstract boolean excluirPerfil(Perfil perfil);
	
}
