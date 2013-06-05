package workoutsystem.dao;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.model.Especificacao;
import workoutsystem.model.Treino;

public interface ITreinoDao {
	
	
	/**
	 * Metodo responsavel pela remoção de uma determinada especifcacao
	 * @param codigoTreino
	 * @param ordem
	 * @return
	 * @throws SQLException 
	 */
	public abstract boolean excluirEspecificacao(long codigoTreino, long ordem) throws SQLException;
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
	 * Metodo responsavel pela reordenação das especificacoes/series de uma ficha
	 * @param ordemAntiga
	 * @param ordemNova
	 * @param codigoTreino 
	 * @return 
	 * @throws SQLException 
	 */
	public boolean reordenarEspecificacao
			(int ordemAntiga, int ordemNova, long codigoTreino) throws SQLException;
	/**
	 * Metodo responsavel pela exclusão da especificacao todos os exercicios  
	 * em um treino
	 * @param codigoTreino = long 
	 * @param codigoExercicio = long
	 * @return
	 * @throws SQLException
	 */
	public boolean excluirEspecificacao(long codigoTreino) throws SQLException;
	
	/**
	 * Metodo responsavel por adicionar a especificacao um exercicio em uma ficha
	 * @param especificacao
	 * @return
	 */
	public abstract boolean inserirEspecificacao(Especificacao especificacao)
	throws SQLException;
	
	/**
	 * Metodo responsavel pela reordenação dos treinos de uma ficha
	 * @param ordem
	 * @param codigoTreino
	 * @return 
	 * @throws SQLException 
	 */
	public abstract boolean reordenarTreino(int ordem, long codigoTreino) throws SQLException;


	/**
	 * Metodo responsavel pela busca da quantidade de especificacao em um determinado treino
	 * @param codigoFicha
	 * @return
	 */
	public abstract int buscarQuantidadeEspecificacao(long codigoTreino) 
	throws SQLException;
	/**
	 * Metodo responsavel pela alteração de uma especificacao
	 * @param especificacao
	 * @return true se a operação foi realizada ou false caso contrario
	 * @throws SQLException 
	 */
	public abstract boolean atualizarEspecificacao(Especificacao especificacao) throws SQLException;
}
