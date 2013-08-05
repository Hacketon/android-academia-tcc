package workoutsystem.dao;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.model.Serie;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Ficha;
import workoutsystem.model.Treino;



public interface IFichaDao {
	public boolean atualizarRealizacoes() throws SQLException;

	/**
	 * Metodo responsavel por listar todas as fichas existentes
	 * @return lista de fichas
	 * @throws SQLException
	 */
	public List<Ficha> listarFichas() throws SQLException;
		
	/**
	 * Metodo responsavel pela criação de uma ficha
	 * @param ficha
	 * @return
	 */
	public abstract boolean inserirFicha(Ficha ficha) throws SQLException;
	
	
	/**
	 * Metodo responsavel pela busca de uma ficha através de 
	 * um nome
	 * @param nome 
	 * @return Ficha
	 */
	public Ficha buscarFicha(String nome) throws SQLException;
	/**
	 * Metodo responsavel pela exclusão de uma ficha 
	 * @param codigoFicha
	 * @return
	 */
	public boolean excluirFicha(long codigoFicha) throws SQLException;
	
	
	
	
	/**
	 * Metodo responsavel pela busca da ficha atual do perfil cadastrado
	 * @return Ficha atual do usuario ou nulo , senão tiver ficha.
	 * @throws SQLException
	 */
	public Ficha buscarFichaAtual() throws SQLException;
	/**
	 * Metodo responsavel pela alteração da ficha atual do perfil cadastrado
	 * @return true , alterado com sucesso , false quando não for possivel alterar
	 * @throws SQLException
	 */
	public boolean alterarFichaAtual(long codigoFicha) throws SQLException;
	/**
	 * Metodo responsavel pela busca de uma ficha através do seu codigo .
	 * @param i
	 * @return Ficha
	 * @throws SQLException
	 */
	public Ficha buscarFichaCodigo(long i) throws SQLException;

	/**
	 * Metodo responsavel pela desativação das fichas atuais
	 * @return true , caso consiga 
	 * @throws SQLException 
	 */
	public boolean desativarFichaAtual() throws SQLException;

	/**
	 * Metodo responsavel pela atualização de uma ficha !
	 * @param ficha
	 * @return
	 * @throws SQLException
	 */
	public boolean atualizarFicha(Ficha ficha) throws SQLException;

	/**
	 * Metodo responsavel pela busca do codigo da ultima ficha adicionada !
	 * @return
	 * @throws SQLException
	 */
	public long buscarUltimaFicha() throws SQLException;

	/**
	 * Metodo responsavel pela busca de todas as fichas exceto a 
	 * ficha que esta sendo modificada
	 * @param codigo [codigo da ficha que esta sendo modificada!]
	 * @return listaFicha
	 * @throws SQLException
	 */
	public List<Ficha> buscarFichaDiferente(long codigo) throws SQLException;

	
}
