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
	public abstract Perfil buscarPerfil(Usuario u);
	
	/**
	 * metodo responsavel pela criação de um perfil
	 * @param perfil
	 * @return
	 */
	public abstract boolean criarPerfil(Perfil perfil,Usuario usuario);
	
	
	/**
	 * Metodo responsavel pela exclusao de um perfil  
	 * @param perfil
	 * @return
	 */
	public abstract boolean excluirPerfil(int codigoUsuario);
	/**
	 * Metodo responsavel pela atualização do perfil do usuario
	 * @return true = possivel atualizar caso contrario false;
	 */
	public abstract boolean atualizarPerfil(Perfil perfil,Usuario usuario);
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
	public abstract List<DiaSemana> buscarFrequencia(Perfil perfil);
		
	/**
	 * Metodo responsavel pela exclusão das frequencias
	 * @param perfil
	 * @return
	 */
	public abstract boolean excluirFrequencia(Perfil perfil);
	/**
	 * Metodo que buscara o codigo do perfil apartir do usuario
	 * @param u
	 * @return
	 */
	public abstract int codigoPerfil(Usuario u);
	/**
	 * Metodos que buscará a quantida de dias selecionados
	 */
	public abstract int quantidadeDias(Perfil perfil);
}
