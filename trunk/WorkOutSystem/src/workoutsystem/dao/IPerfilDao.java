package workoutsystem.dao;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.model.Frequencia;
import workoutsystem.model.Perfil;

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
	public abstract boolean excluirPerfil();
	/**
	 * Metodo responsavel pela atualização do perfil do usuario
	 * @return true = possivel atualizar caso contrario false;
	 */
	public abstract boolean atualizarPerfil(Perfil perfil);
	/**
	 * Metodo responsavel pela atualização da frequencia perfil do usuario
	 * @param perfil
	 * @return true = possivel atualizar caso contrario false; 
	 */
	public abstract boolean frequenciaPerfil(Perfil perfil);
	/**
	 * Busca a Frequencia de um determinado perfil (Implementar) 
	 * @param perfil
	 * @return Lista de Dias da Semana
	 */
	public abstract List<Frequencia> buscarFrequencia(Perfil perfil);
		
	/**
	 * Metodo responsavel pela exclusão das frequencias
	 * @param perfil
	 * @return
	 */
	public abstract boolean excluirFrequencia(Perfil perfil);
	
	/**
	 * Metodos que buscará a quantida de dias selecionados
	 */
	public abstract int quantidadeDias(Perfil perfil);

	public abstract int buscarUltimoPerfil() throws SQLException;

	
}
