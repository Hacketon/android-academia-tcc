package workoutsystem.dao;

import workoutsystem.model.Medicao;

public interface IMedidaDao {
/**
 * Metodo que buscar� o codigo de medi��o
 * @param nome
 * @return
 */
	public abstract int buscarMedida(String nome, String lado);
	
	/**
	 * Metodo responsavel por adicionar medida
	 * @param medicao
	 * @return
	 */
	public abstract boolean adicionarMedicao(Medicao medicao);
}
