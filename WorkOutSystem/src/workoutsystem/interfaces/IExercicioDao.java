package workoutsystem.interfaces;

import java.util.List;

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
	 * Metodo responsavel pela cria��o de um novo exercicio
	 * @param e
	 * @return
	 */

	public abstract boolean adicionarExercicio(Exercicio e);
	
	/**
	 * Metodo responsavel pela altera��o de exercicio por codigo
	 * @param codigo
	 * @param e
	 * @return
	 */
	
	public abstract boolean alterarExercicio(long codigo, Exercicio e);
	
	/**
	 * Metodo responsavel pela exclus�o de exercicios 
	 * @param l
	 * @return
	 */
	
	public abstract boolean excluirExercicio(long l);
	
	/**
	 * Metodo responsavel pela visualiza��o dos Passos de cada exercicio
	 * @param e
	 * @return
	 */
	public abstract List<Passo> visualizarPassos(Exercicio e);
	
	/**
	 * Metodo responsavel pela visualiza��o dos exercicios
	 * @param e
	 * @return exercicio
	 */
	public abstract Exercicio visualizarExercicio(Exercicio e);
	
	/**
	 * Metodo que far� a busca do c�digo de cada grupo muscular 
	 * @return numero do grupo muscular
	 */
	public abstract int buscarGrupoMuscular(String nome);
		/**
	 * Uma lista de exercicio atrav�s do nome do exercicio
	 * @param nome = nome do exercicio
	 * @return objeto do tipo exercicio
	 */
	public abstract Exercicio buscarExercicio(String nome);
	/**
	 * Lista de exercicios n�o personalizados ou personalizados atrav�s do grupo muscular e 
	 * booleano indicando se esta ou n�o personalizado
	 * @param grupo
	 * @return List
	 */
	public abstract List<Exercicio> listarExercicios(String grupo,int personalizado);
	/**
	 * Metodo responsavel pela busca de exercicio por grupo muscular
	 * @param grupo
	 * @return
	 */
	public abstract Exercicio buscarExercicioGrupoMuscular(GrupoMuscular grupo);
}
