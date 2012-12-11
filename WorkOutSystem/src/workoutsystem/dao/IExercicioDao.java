package workoutsystem.dao;

import workoutsystem.model.Exercicio;

public interface IExercicioDao {
	
	/**
	 * Metodo responsavel pela busca de exercicios
	 * @param e
	 * @return
	 */
	public abstract Exercicio buscarExercicio(Exercicio e);
	
	/**
	 * Metodo responsavel pela criação de um novo exercicio
	 * @param e
	 * @return
	 */
	
	public abstract boolean adicionarExercicio(Exercicio e);
	
	/**
	 * Metodo responsavel pela alteração de exercicio por codigo
	 * @param codigo
	 * @param e
	 * @return
	 */
	
	public abstract boolean alterarExercicio(int codigo, Exercicio e);
	
	/**
	 * Metodo responsavel pela exclusão de exercicios 
	 * @param codigo
	 * @return
	 */
	
	public abstract boolean excluirExercicio(int codigo);
	
	/**
	 * Metodo responsavel pela visualização dos Passos de cada exercicio
	 * @param e
	 * @return
	 */
	public abstract Exercicio visualizarPassos(Exercicio e);
	
	/**
	 * Metodo responsavel pela visualização dos exercicios
	 * @param e
	 * @return
	 */
	
	public abstract Exercicio visualizarExercicio(Exercicio e);
	
	/**
	 * Metodo que fará a busca do código de cada grupo muscular 
	 * @return
	 */
	public abstract int buscarGrupoMuscular(String nome);

	
}
