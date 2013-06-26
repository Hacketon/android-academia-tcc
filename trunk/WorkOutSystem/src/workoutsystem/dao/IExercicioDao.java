package workoutsystem.dao;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.model.Serie;
import workoutsystem.model.Exercicio;


import workoutsystem.model.Passo;


import workoutsystem.model.GrupoMuscular;


public interface IExercicioDao {
	/**
	 *  Busca exercicio pelo nome e por codigo 
	 * @param nome
	 * @param codigo
	 * @return true = exercicio existente com este nome e codigo diferente caso contrario false
	 */
	public abstract boolean buscarExercicio(String nome,long codigo);

	/**
	 * Metodo responsavel pela busca de exercicios
	 * @param e busca pelo codigo do exercicio
	 * @return
	 */
	public abstract boolean buscarExercicio(long codigo);


	/**
	 * Metodo responsavel pela criação de um novo exercicio
	 * @param e
	 * @return
	 * @throws SQLException 
	 */

	public abstract boolean adicionarExercicio(Exercicio e) throws SQLException;

	/**
	 * Metodo responsavel pela alteração de exercicio por codigo
	 * @param codigo
	 * @param e
	 * @return
	 * @throws SQLException 
	 */

	public abstract boolean alterarExercicio(long codigo, Exercicio e) throws SQLException;

	/**
	 * Metodo responsavel pela desativação dos exercicios 
	 * @param l
	 * @return
	 * @throws SQLException 
	 */

	public abstract boolean excluirExercicio(long l) throws SQLException;

	/**
	 * Metodo responsavel pela visualização dos Passos de cada exercicio
	 * @param e
	 * @return
	 * @throws SQLException 
	 */
	public abstract List<Passo> visualizarPassos(Exercicio e) throws SQLException;



	/**
	 * Metodo que fará a busca do código de cada grupo muscular 
	 * @return numero do grupo muscular
	 * @throws SQLException 
	 */
	public abstract int buscarGrupoMuscular(String nome) throws SQLException;
	public abstract String buscarGrupoMuscular(int codigo) throws SQLException;
	/**
	 * Uma lista de exercicio através do nome do exercicio
	 * @param nome = nome do exercicio
	 * @return objeto do tipo exercicio
	 */
	public abstract Exercicio buscarExercicio(String nome);
	/**
	 * Lista de exercicios não personalizados ou personalizados através do grupo muscular e 
	 * booleano indicando se esta ou não personalizado
	 * @param grupo
	 * @return List
	 */
	public abstract List<Exercicio> listarExercicios(int grupo,int personalizado);
	/**
	 * Metodo responsavel pela busca de exercicio por grupo muscular
	 * @param grupo
	 * @return
	 */
	public abstract Exercicio buscarExercicioGrupoMuscular(GrupoMuscular grupo);

	/**
	 * Metodo responsavel por buscar exercicios "desativados" (excluidos)
	 * @param nomeExercicio
	 * @param i
	 * @return
	 * @throws SQLException 
	 */
	public abstract boolean reativarExercicio(String nomeExercicio, int i) throws SQLException;
	/**
	 * Metodo responsavel por listar exercicios que estão relacionados há um treino de uma ficha;
	 * @param codigoFicha
	 * @param codigoTreino
	 * @return
	 */
	public abstract List<Exercicio> listarExercicioTreino(long codigoTreino) throws SQLException;
	/**
	 * Metodo responsavel pela busca dos exercicios 
	 * que não estão relacionados a nenhum treino 
	 * @param codigoGrupo = grupo muscular
	 * @param codigoAtivo = ativo ou não 
	 * @return Lista de Exercicio
	 * @throws SQLException
	 */
	public abstract List<Exercicio> buscarExercicioTreino (int codigoGrupo,int codigoAtivo) throws SQLException;
	/**
	 *  Metodo responsavel por buscar todos
	 *  os exercicios que não  estão ligados a um treino 
	 * @param codigoTreino
	 * @param codigoGrupo
	 * @return list
	 * @throws SQLException
	 */
	public abstract List<Exercicio> listarExercicioFora(long codigoTreino, long codigoGrupo)
	throws SQLException;

	

	/**
	 * 
	 *  Metodo responsavel por buscar todos
	 *  os exercicios que não  estão ligados a um treino
	 * @param codigoTreino
	 * @return
	 * @throws SQLException
	 */
	public List<Exercicio> listarExercicioSemTreino(long codigoGrupo)
	throws SQLException ;

}
