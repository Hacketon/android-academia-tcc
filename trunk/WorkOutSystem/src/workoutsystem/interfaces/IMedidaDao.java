package workoutsystem.interfaces;

import java.util.List;

import workoutsystem.model.Medicao;

public interface IMedidaDao {
/**
 * Metodo que buscar� o codigo de medi��o
 * @param nome
 * @return
 */
	public abstract int buscarMedida(String nome, String lado);
	
	
	
	/**
	 * Metodo que trar� o valor da medicao , dependendo do codigo informado
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
	 * metodo que ira adicionar medi��es por uma lista 
	 * @param medicao
	 * @return
	 */
	public abstract boolean adicionarMedicao(List<Medicao> medicao);
/**
 * metodo que ir� alterar as medicoes apartit da data e codigomedida
 * @param medicoes
 * @return
 */
	public abstract boolean alterarMedicao(List<Medicao> medicoes);
	
	/**
	 * metodo que ira aletar as ultimas medicoes de cada grupo muscular
	 * @param m
	 * @return
	 */
	public abstract boolean alterarUltimaMedicao(List<Medicao> m);
}
