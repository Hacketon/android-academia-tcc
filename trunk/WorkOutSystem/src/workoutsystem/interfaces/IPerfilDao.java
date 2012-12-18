package workoutsystem.interfaces;

import java.util.List;

import workoutsystem.model.DiaSemana;
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
	 * metodo responsavel pela cria��o de um perfil
	 * @param perfil
	 * @return
	 */
	public abstract boolean criarPerfil(Perfil perfil);
	
	
	/**
	 * Metodo responsavel pela exclusao de um perfil  
	 * @param perfil
	 * @return
	 */
	public abstract boolean excluirPerfil(int codigoUsuario);
	/**
	 * Metodo responsavel pela atualiza��o do perfil do usuario
	 * @return true = possivel atualizar caso contrario false;
	 */
	public abstract boolean atualizarPerfil(Perfil perfil);
	/**
	 * Metodo responsavel pela atualiza��o da frequencia perfil do usuario
	 * @param perfil
	 * @return true = possivel atualizar caso contrario false; 
	 */
	public abstract boolean frequenciaPerfil(Perfil perfil);
	/**
	 * Busca a Frequencia de um determinado perfil (Implementar) 
	 * @param perfil
	 * @return Lista de Dias da Semana
	 */
	public abstract List<DiaSemana> buscarFrequencia(Perfil perfil);
		
	
}