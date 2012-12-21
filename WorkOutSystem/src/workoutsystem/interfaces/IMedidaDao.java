package workoutsystem.interfaces;

import java.util.List;

import workoutsystem.model.Medicao;

public interface IMedidaDao {
/**
 * Metodo que buscará o codigo de medição
 * @param nome
 * @return
 */
	public abstract int buscarMedida(String nome, String lado);
	
	
	
	/**
	 * Metodo que trará o valor da medicao , dependendo do codigo informado
	 * @param codigo
	 * @return
	 */
	public abstract Double buscarValorMedicao(int codigo);
	/**
	 * Metodo que TUDO de medicao
	 * @param codigo
	 * @return
	 */

	public abstract List<Medicao> buscarMedicao(int codigo);

	/**
	 * metodo que ira adicionar medições por uma lista 
	 * @param medicao
	 * @return
	 */
	public abstract boolean adicionarMedicao(List<Medicao> medicao);
}
