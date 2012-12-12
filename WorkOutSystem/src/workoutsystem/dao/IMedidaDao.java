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
	
	/**
	 * Metodo que trar� o valor da medicao , dependendo do codigo informado
	 * @param codigo
	 * @return
	 */
	public abstract Medicao buscarValorMedicao(int codigo);
}
