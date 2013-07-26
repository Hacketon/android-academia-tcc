package workoutsystem.dao;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.model.Realizacao;
import workoutsystem.model.Serie;

public interface ISerieDao {

	public boolean alterarCarga(double novo,int codigo) throws SQLException;
	/**
	 * Metodo responsavel pela busca da quantidade de serie em um determinado treino
	 * @param codigoFicha
	 * @return
	 */
	public abstract int buscarQuantidadeSerie(long codigoTreino) 
	throws SQLException;
	/**
	 * Metodo responsavel pela alteração de uma Serie
	 * @param Serie
	 * @return true se a operação foi realizada ou false caso contrario
	 * @throws SQLException 
	 */
	public abstract boolean atualizarSerie(Serie serie) throws SQLException;
	/**
	 * Remover series de um determinado exercicio 
	 * @param codigoTreino
	 * @param codigoExercicio
	 * @return true or false;
	 * @throws SQLException 
	 */
	public abstract boolean removerSerie(long codigoTreino, long codigoExercicio) throws SQLException;
	/**
	 * Metodo responsavel pela busca de um codigo de uma serie através de sua ordem e codigo do treino!
	 * @param ordem
	 * @return
	 * @throws SQLException
	 */
	public abstract int buscarSerie(int ordem,int codigotreino) throws SQLException;


	public boolean reordenarSerieRemocao(int posicaoInicial, int posicaoFinal,long codigoTreino) throws SQLException;
	
	boolean reordenarSerie(int novo, int codigo) throws SQLException;
	
	/**
	 * Metodo responsavel pela exclusão da series de todos os exercicios  
	 * em um treino
	 * @param codigoTreino = long 
	 * @return
	 * @throws SQLException
	 */
	public boolean excluirSerieTreino(long codigoTreino) throws SQLException;
	
	/**
	 * Metodo responsavel por adicionar a serie um exercicio em uma ficha
	 * @param serie
	 * @return
	 */
	public abstract boolean inserirSerie(Serie serie)
	throws SQLException;
	
	/**
	 * 
	 *  Metodo responsavel por buscar todos
	 *  os exercicios que não  estão ligados a um treino 
	 *  especifico
	 * @param codigoTreino
	 * @return
	 * @throws SQLException
	 */
	public abstract List<Serie> listarSerie(long codigoTreino)
	throws SQLException;
	
	/**
	 * Metodo responsavel pela remoção de uma determinada serie
	 * @param codigoTreino
	 * @param ordem
	 * @return
	 * @throws SQLException 
	 */
	public abstract boolean excluirSerieCodigo(long codigo) throws SQLException;
	
	/**
	 * Metodo responsavel por inserir series na tabela realizacaoSerie , como uma tabela auxiliar ,
	 *  que ao excluir dados dessa tabela  os mesmos serão inseridos na tabele realizacao
	 * @param codigoFicha
	 * @return listaTreino
	 * @throws SQLException 
	 */
	public abstract boolean inserirRealizacaoSerie(Serie serie) throws SQLException;


	public abstract boolean removerRealizacaoSerie(Serie serie) throws SQLException;


	public abstract List<Serie> listarRealizacaoSerie() throws SQLException;
	
	
	public abstract boolean inserirRealizacao(Serie serie, long codigoFicha) throws SQLException;
	/**
	 * Remove todos os exercicios que tem alguma serie relacionada !
	 * @param codigo [exercicio]
	 * @return true or false
	 */
	public abstract boolean removerSerie(long codigo) throws SQLException;
	
	
	public abstract int buscarTreinoIniciado() throws SQLException;

	public abstract boolean removerTodasRealizacoesSerie() throws SQLException;
	
	
}
