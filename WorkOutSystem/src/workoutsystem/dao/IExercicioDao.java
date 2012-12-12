package workoutsystem.dao;

import java.util.List;

import workoutsystem.model.Exercicio;
<<<<<<< .mine
import workoutsystem.model.Passo;
=======
import workoutsystem.model.GrupoMuscular;
>>>>>>> .r98

public interface IExercicioDao {
	
	
	/**
	 * Metodo responsavel pela busca de exercicios
	 * @param e
	 * @return
	 */
	public abstract Exercicio buscarExercicio(Exercicio e);
	
	
	/**
	 * Metodo que realizar� a busca de exercicios personalizados
	 * @param e
	 * @return
	 */
	public abstract Exercicio buscarExercicioPersonalizado(Exercicio exercicio);
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
	
	public abstract boolean alterarExercicio(int codigo, Exercicio e);
	
	/**
	 * Metodo responsavel pela exclus�o de exercicios 
	 * @param codigo
	 * @return
	 */
	
	public abstract boolean excluirExercicio(int codigo);
	
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
	 * Uma lista de exercicio (personalizados) atrav�s do grupo muscular 
	 * @param grupo = nome do grupo 
	 * @return Lista de exercicios
	 */
	public abstract List<Exercicio> listarExercicioPersonalizado(String grupo);
	/**
	 * Uma lista de exercicio atrav�s do nome do exercicio
	 * @param nome = nome do exercicio
	 * @return objeto do tipo exercicio
	 */
	public abstract Exercicio buscarExercicio(String nome);
	/**
	 * Lista de exercicios n�o personalizados atrav�s do grupo muscular
	 * @param grupo
	 * @return List
	 */
	public abstract List<Exercicio> listarExercicios(String grupo);
	/**
	 * Metodo responsavel pela busca de exercicio por grupo muscular
	 * @param grupo
	 * @return
	 */
	public abstract Exercicio buscarExercicioGrupoMuscular(GrupoMuscular grupo);
}
