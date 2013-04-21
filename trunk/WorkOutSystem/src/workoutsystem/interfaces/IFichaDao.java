package workoutsystem.interfaces;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.model.Especificacao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Ficha;
import workoutsystem.model.Treino;



public interface IFichaDao {

	/**
	 * Metodo responsavel por listar todas as fichas existentes
	 * @return lista de fichas
	 * @throws SQLException
	 */
	public List<Ficha> listarFichas() throws SQLException;
	/**
	 * Metodo responsavel por listar treinos a partir de uma ficha
	 * @param codigoFicha
	 * @return lista de treinos
	 * @throws SQLException
	 */
	public List<Treino> listarTreinos(int codigoFicha) throws SQLException;
	/**
	 * Metodo responsavel por listar as especificacoes de um exercicio 
	 * a partir de um treino
	 * @return lista de especificacao
	 * @throws SQLException
	 */
	public List<Especificacao> listarEspecificacao(int codigoTreino,int codigoExercicio,
			int codigoFicha)
	throws SQLException;
	
	
	/**
	 * Metodo responsavel por inserir um treino e relacionar com uma ficha
	 * @param treino
	 * @return
	 */
	public abstract boolean inserirTreino(Treino treino) throws SQLException;
	/**
	 * Metodo responsavel pela criação de uma ficha
	 * @param ficha
	 * @return
	 */
	public abstract boolean inserirFicha(Ficha ficha) throws SQLException;
	/**
	 * Metodo responsavel por adicionar a especificacao um exercicio em uma ficha
	 * @param especificacao
	 * @return
	 */
	public abstract boolean inserirEspecificacao(Especificacao especificacao) throws SQLException;
	


	
	
}
