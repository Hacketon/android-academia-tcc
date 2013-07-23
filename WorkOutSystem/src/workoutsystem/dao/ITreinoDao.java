package workoutsystem.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import workoutsystem.model.Realizacao;
import workoutsystem.model.Serie;
import workoutsystem.model.Treino;

public interface ITreinoDao {
	
	
	

	
	
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
	 * Metodo responsavel pela alteração do nome dos treinos
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
	 * Metodo responsavel pela exclusão de um treino separadamente
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
	 * Metodo responsavel pela reordenação das series de uma ficha
	 * @param ordemAntiga
	 * @param ordemNova
	 * @param codigoTreino 
	 * @return 
	 * @throws SQLException 
	 */
	
	
	
	/**
	 * Metodo responsavel pela reordenação dos treinos de uma ficha
	 * @param ordem
	 * @param codigoTreino
	 * @return 
	 * @throws SQLException 
	 */

	boolean reordenarTreino(int ordem, long codigoTreino) throws SQLException;


	/**
	 * Metodo responsavel pela busca de todos os treinos validos , ou seja que tenham
	 * series 
	 * @param codigoFicha
	 * @return listaTreino
	 * @throws SQLException 
	 */
	public abstract List<Treino> buscarTreinoValido(long codigoFicha) throws SQLException;



	
	
	public abstract Realizacao buscarUltimoTreinoRealizado() throws SQLException, ParseException;

}
