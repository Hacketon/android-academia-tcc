package workoutsystem.dao;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.model.Serie;
import workoutsystem.model.Treino;

public interface ITreinoDao {
	
	
	/**
	 * 
	 *  Metodo responsavel por buscar todos
	 *  os exercicios que n�o  est�o ligados a um treino 
	 *  especifico
	 * @param codigoTreino
	 * @return
	 * @throws SQLException
	 */
	public abstract List<Serie> listarSerie(long codigoTreino)
	throws SQLException;

	
	/**
	 * Metodo responsavel pela remo��o de uma determinada serie
	 * @param codigoTreino
	 * @param ordem
	 * @return
	 * @throws SQLException 
	 */
	public abstract boolean excluirSerieCodigo(long codigo) throws SQLException;
	
	public boolean verificarExercicio(long codigoExercicio) throws SQLException;
	
	
	/**
	 * Metodo responsavel pela busca de um treino em um determinada ficha
	 * @param nomeTreino
	 * @param codigoFicha
	 * @return true = se o treino existe caso contrario false
	 * @throws SQLException 
	 */
	public boolean buscarTreino(String nomeTreino, long codigoFicha) throws SQLException;
	
	/**
	 * Metodo responsavel pela altera��o do nome dos treinos
	 * @param nomeTreino
	 * @param codigoFicha
	 * @param codigoTreino 
	 * @return
	 */
	public boolean alterarNomeTreino(String nomeTreino, long codigoFicha, long codigoTreino) throws SQLException;

	/**
	 * Metodo responsavel por inserir um treino e relacionar com uma ficha
	 * @param treino
	 * @return
	 */
	public abstract boolean inserirTreino(Treino treino) throws SQLException;
	
	/**
	 * Metodo responsavel por listar treinos a partir de uma ficha
	 * @param codigo
	 * @return lista de treinos
	 * @throws SQLException
	 */
	public List<Treino> listarTreinos(long codigo) throws SQLException;
	
	/**
	 * Metodo responsavel pela exclus�o de um treino separadamente
	 * @param codigoTreino
	 * @param codigoFicha
	 * @return
	 * @throws SQLException
	 */
	public boolean excluirTreino(long codigoTreino, long codigoFicha) throws SQLException;
	
	
	/**
	 * Metodo responsavel pela busca da quantidade de treinos em uma determinada ficha
	 * @param codigoFicha
	 * @return
	 */
	public int buscarQuantidadeTreino(long codigoFicha) throws SQLException;
	
	/**
	 * Metodo responsavel pela reordena��o das series de uma ficha
	 * @param ordemAntiga
	 * @param ordemNova
	 * @param codigoTreino 
	 * @return 
	 * @throws SQLException 
	 */
	
	/**
	 * Metodo responsavel pela exclus�o da series de todos os exercicios  
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
	 * Metodo responsavel pela reordena��o dos treinos de uma ficha
	 * @param ordem
	 * @param codigoTreino
	 * @return 
	 * @throws SQLException 
	 */

	boolean reordenarTreino(int ordem, long codigoTreino) throws SQLException;


	/**
	 * Metodo responsavel pela busca da quantidade de serie em um determinado treino
	 * @param codigoFicha
	 * @return
	 */
	public abstract int buscarQuantidadeSerie(long codigoTreino) 
	throws SQLException;
	/**
	 * Metodo responsavel pela altera��o de uma Serie
	 * @param Serie
	 * @return true se a opera��o foi realizada ou false caso contrario
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
	 * Metodo responsavel pela busca de um codigo de uma serie atrav�s de sua ordem e codigo do treino!
	 * @param ordem
	 * @return
	 * @throws SQLException
	 */
	public abstract int buscarSerie(int ordem,int codigotreino) throws SQLException;



	boolean reordenarSerie(int novo, int codigo) throws SQLException;

	/**
	 * Metodo responsavel pela busca de todos os treinos validos , ou seja que tenham
	 * series 
	 * @param codigoFicha
	 * @return listaTreino
	 * @throws SQLException 
	 */
	public abstract List<Treino> buscarTreinoValido(long codigoFicha) throws SQLException;
	
}
